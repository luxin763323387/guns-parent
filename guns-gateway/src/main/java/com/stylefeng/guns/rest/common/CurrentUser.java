package com.stylefeng.guns.rest.common;

/**
 * @author Steven Lu
 * @date 2018/12/18 -13:36
 */
public class CurrentUser {

  /*  //线程绑定存储空间
    //泛型写法
    private static final ThreadLocal<UserInfoModel> threadLocal = new ThreadLocal<>();

    //将信息存储到空间
    public static void saveUserInfo(UserInfoModel userInfoModel){
        threadLocal.set(userInfoModel);
    }

    //将用户信息取出
    public static UserInfoModel getCurrentUser(){
        return threadLocal.get();
    }
*/

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    //将userId存入
    public static void  saveUserId(String userId){
        threadLocal.set(userId);
    }

    //将用户信息去除
    public static String getCurrentUser(){
        return threadLocal.get();
    }
}
