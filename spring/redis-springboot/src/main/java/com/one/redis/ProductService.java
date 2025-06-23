package com.one.redis;

import com.alibaba.fastjson.JSON;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 分析多级缓存架构下: 缓存击穿, 缓存穿透, 缓存雪崩, 热点key缓存重建等问题解决方案 tuling demo
 * @author one
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private RedisService<Object> redisService;

    @Autowired
    private RedissonClient redisson;

    /**
     * 产品基础信息缓存前缀
     */
    public static final String PRODUCT_CACHE = "product:cache:";

    public static final Long PRODUCT_CACHE_TIMEOUT = 60 * 60 * 24L;
    public static final String EMPTY_CACHE = "{}";
    public static final String LOCK_PRODUCT_HOT_CACHE_PREFIX = "lock:product:hot_cache:";
    public static final String LOCK_PRODUCT_UPDATE_PREFIX = "lock:product:update:";

    /**
     * 对于热点中的热点数据, 直接使用本地缓存,  并发量支持的大小: web服务本地缓存 > Redis缓存 >  数据库
     */
    public static Map<String, Product> productMap = new ConcurrentHashMap<>();

    @Transactional
    public Product create(Product product) {
        Product productResult = productDao.create(product);
        redisService.setCacheObject(PRODUCT_CACHE + productResult.getId(), JSON.toJSONString(productResult),
                genProductCacheTimeout(), TimeUnit.SECONDS);
        return productResult;
    }

    @Transactional
    public Product update(Product product) {
        Product productResult = null;
        //RLock updateProductLock = redisson.getLock(LOCK_PRODUCT_UPDATE_PREFIX + product.getId());
        RReadWriteLock readWriteLock = redisson.getReadWriteLock(LOCK_PRODUCT_UPDATE_PREFIX + product.getId());
        RLock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            productResult = productDao.update(product);
            redisService.setCacheObject(PRODUCT_CACHE + productResult.getId(), JSON.toJSONString(productResult),
                    genProductCacheTimeout(), TimeUnit.SECONDS);
            productMap.put(PRODUCT_CACHE + productResult.getId(), product);
        } finally {
            writeLock.unlock();
        }
        return productResult;
    }

    public Product get(Long productId) throws InterruptedException {
        Product product = null;
        String productCacheKey = PRODUCT_CACHE + productId;

        product = getProductFromCache(productCacheKey);
        if (product != null) {
            return product;
        }
        // 对于热点key的缓存重建, 加锁, 防止同一时间对于同一热点key的查询打到数据库上
        // DCL 双重锁
        RLock hotCacheLock = redisson.getLock(LOCK_PRODUCT_HOT_CACHE_PREFIX + productId);
        hotCacheLock.lock();
        //boolean result = hotCacheLock.tryLock(3, TimeUnit.SECONDS);
        try {
            product = getProductFromCache(productCacheKey);
            if (product != null) {
                return product;
            }

            //RLock updateProductLock = redisson.getLock(LOCK_PRODUCT_UPDATE_PREFIX + productId);
            // 使用读写锁分离, 解决双写导致的缓存不一致问题, 兼顾读多写少的场景下的效率问题
            RReadWriteLock readWriteLock = redisson.getReadWriteLock(LOCK_PRODUCT_UPDATE_PREFIX + productId);
            RLock rLock = readWriteLock.readLock();
            rLock.lock();
            try {
                product = productDao.get(productId);
                if (product != null) {
                    redisService.setCacheObject(productCacheKey, JSON.toJSONString(product),
                            genProductCacheTimeout(), TimeUnit.SECONDS);
                    productMap.put(productCacheKey, product);
                } else {
                    redisService.setCacheObject(productCacheKey, EMPTY_CACHE, genEmptyCacheTimeout(), TimeUnit.SECONDS);
                }
            } finally {
                rLock.unlock();
            }
        } finally {
            hotCacheLock.unlock();
        }
        return product;
    }


    /**
     * 使用随机数生成key的过期时间, 这样防止key大范围同一时间过期, 造成缓存击穿
     * @return
     */
    private Long genProductCacheTimeout() {
        return PRODUCT_CACHE_TIMEOUT + new Random().nextInt(5) * 60 * 60;
    }

    private Long genEmptyCacheTimeout() {
        return 60L + new Random().nextInt(30);
    }

    private Product getProductFromCache(String productCacheKey) {
        Product product = productMap.get(productCacheKey);
        if (product != null) {
            return product;
        }

        String productStr = redisService.get(productCacheKey);
        if (!StringUtils.isEmpty(productStr)) {
            // 对于数据库本来就没有的key的数据, 可以在redis中缓存一个empty对象, 防止缓存穿透, 也可以使用布隆过滤器提前过滤一遍
            if (EMPTY_CACHE.equals(productStr)) {
                redisService.expire(productCacheKey, genEmptyCacheTimeout(), TimeUnit.SECONDS);
                return new Product();
            }
            product = JSON.parseObject(productStr, Product.class);
            //读延期
            redisService.expire(productCacheKey, genProductCacheTimeout(), TimeUnit.SECONDS);
        }
        return product;
    }


    /**  使用Redis中的zSet数据结构来做产品的实时销量排行榜  **/

    public static final String HOT_PRODUCTS_KEY = "hot_products";

    public static final String PREFIX = "product_";

    public void testSortSet() {
        for (int i = 0; i < 1000; i++) {
            String productId = PREFIX + i;
            Double score =  Math.random() * 10000;
            redisService.zSetAdd(HOT_PRODUCTS_KEY, productId, score);
        }
        Set<Object> hotProducts = redisService.reverseRange(HOT_PRODUCTS_KEY, 10);
        for (Object hotProduct : hotProducts) {
            System.out.println(hotProduct.toString());
        }
    }

}