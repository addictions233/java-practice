import com.one.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: UserApp
 * @Description: 带main方法的测试类
 * @Author: one
 * @Date: 2020/12/01
 */
public class IOCApp {
    public static void main(String[] args) {
        //2,加载核心配置文件 applicationContext.xml
//        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //3,获取资源 如果applicationContext.xml文件中没有配置该 bean id属性,就会报NoSuchBeanDefinitionException
//        UserService userService = (UserService) ctx.getBean("userService");
//        //用userService对象调用其方法
//        userService.save();
        //测试2
//        UserService userService = (UserService) ctx.getBean("userService7");
//        System.out.println(userService);

        //测试3
//        BookDao bookDao = (BookDao) ctx.getBean("bookDao");
//        bookDao.save();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookDao bookDao = (BookDao) ctx.getBean("bookDao");
    }
}
