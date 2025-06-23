package com.one.dao;

import com.one.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @InterfaceName: UserDao
 * @Description:
 * @Author: one
 * @Date: 2020/12/09
 */
public interface UserDao {
     /**
      * @description 通过id查询用户
      * @param
      * @return
      */
      User get(Integer uuid);

       /**
        * @description 查询所有的User
        * @param
        * @return
        */
       List<User> getAll();

        /**
         * @description 增
         * @param
         * @return
         */
        Boolean save(User user);


         /**
          * @description 删
          * @param
          * @return
          */
         Boolean delete(Integer uuid);

          /**
           * @description 改
           * @param
           * @return
           */
          Boolean update(User user);

           /**
            * @description 通过用户名和密码查询用户
            * @param
            * @return
            */
           User getByUserNameAndPassword(@Param("uerName") String userName,@Param("password") String password);

}
