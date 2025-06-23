package com.one.test;

import com.one.bean.Country;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @ClassName: CountryMapperTest
 * @Description: 测试类: 利用mybatis实现sql查询和结果映射其实并不需要CountryMapper接口,
 * 只需要核心配置文件mybatis-config.xml和映射配置文件CountryMapper.xml就能完成sql查询
 * @Author: one
 * @Date: 2022/03/20
 */
public class CountryMapperTest {
    public static void main(String[] args) {
        InputStream inputStream = null;
        SqlSession sqlSession = null;
        try {
            // 1,加载核心配置文件
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            // 2,建造者模式构建sqlSession对象
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            // 3,sqlSession执行selectList()方法,查询多个, 参数传statement标签的namespace + id属性值
//            List<Country> countries = sqlSession.selectList("selectAll"); // 如果id属性不重复,可以不加namespace
            List<Country> countries = sqlSession.selectList("com.one.mapper.CountryMapper.selectAll");
            countries.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
