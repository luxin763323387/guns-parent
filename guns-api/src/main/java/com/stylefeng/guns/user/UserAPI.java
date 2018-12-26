package com.stylefeng.guns.user;

import com.stylefeng.guns.user.vo.UserInfoModel;
import com.stylefeng.guns.user.vo.UserModel;

/**
 * @author Steven Lu
 * @date 2018/12/17 -20:16
 */
public interface UserAPI {

    //注册
    boolean register(UserModel userModel);

    //产线用户名是否可用
    boolean checkUsername(String username);

    //登陆
    int login(String username, String password);

    //查询个人信息
    UserInfoModel getUserInfo(int uuid);

    //更新个人信息
    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);
}
