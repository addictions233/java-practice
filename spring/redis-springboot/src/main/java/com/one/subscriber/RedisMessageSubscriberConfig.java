package com.one.subscriber;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @description: 配置redis message的订阅者
 * @author: wanjunjie
 * @date: 2024/10/10
 */
@Configuration
public class RedisMessageSubscriberConfig {


    /**
     * 配置订阅
     * 基于MessageListenerAdapter和RedisMessageListenerContainer
     * @param redisMessageSubscriber
     * @return
     */
    @Bean
    public MessageListenerAdapter messageListenerAdapter(RedisMessageSubscriber redisMessageSubscriber) {
        return new MessageListenerAdapter(redisMessageSubscriber, "handleMessage");
    }

    /**
     * 基于messageListenerAdapter的配置
     * @param connectionFactory
     * @param messageListenerAdapter 将RedisMessageSubscriber包装成messageListenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter messageListenerAdapter
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 添加消息监听器和监听的频道
        // 基于频道的发布/订阅：
        container.addMessageListener(messageListenerAdapter, new ChannelTopic("your-channel"));

        // 基于模式的发布/订阅：
        container.addMessageListener(messageListenerAdapter, new PatternTopic("your-pattern-*"));
        return container;
    }

//    /**
//     * 基于MessageListener的配置
//     * 直接使用RedisMessageSubscriber
//     * @param connectionFactory
//     * @param redisMessageSubscriber 直接使用RedisMessageSubscriber
//     * @return
//     */
//    @Bean
//    public RedisMessageListenerContainer redisMessageListenerContainer(
//            RedisConnectionFactory connectionFactory,  RedisMessageSubscriber redisMessageSubscriber) {
//
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//
//        // 在这里设置你的动态频道名称，可以从配置文件或其他地方获取
//        String dynamicChannel = "your-channel";
//
//        // 基于频道的发布/订阅：
//        ChannelTopic channelTopic = new ChannelTopic(dynamicChannel);
//
//        // 基于模式的发布/订阅：
//        // container.addMessageListener(redisMessageSubscriber, new PatternTopic("your-pattern-*"));
//
//        // 添加消息监听器和监听的动态频道
//        container.addMessageListener(redisMessageSubscriber, channelTopic);
//
//        return container;
//    }


//    /**
//     * 多频道示例
//     * @param connectionFactory
//     * @param redisMessageSubscriber
//     * @return
//     */
//    @Bean
//    public RedisMessageListenerContainer redisMessageListenerContainerMoreTopic(
//            RedisConnectionFactory connectionFactory,
//            RedisMessageSubscriber redisMessageSubscriber
//    ) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//
//        // 在这里设置你的动态频道名称列表，可以从配置文件或其他地方获取
//        List<String> dynamicChannels = Arrays.asList("channel1", "channel2", "channel3");
//
//        // 创建包含所有频道的ChannelTopic列表
//        List<ChannelTopic> channelTopics = createChannelTopics(dynamicChannels);
//
//        // 添加消息监听器和监听的多个频道
//        for (ChannelTopic channelTopic : channelTopics) {
//            container.addMessageListener(redisMessageSubscriber, channelTopic);
//        }
//
//        return container;
//    }
//
//    private List<ChannelTopic> createChannelTopics(List<String> channelNames) {
//        // 使用动态频道名称创建ChannelTopic列表
//        List<ChannelTopic> channelTopics = new ArrayList<>();
//        for (String channelName : channelNames) {
//            channelTopics.add(new ChannelTopic(channelName));
//        }
//        return channelTopics;
//    }
}
