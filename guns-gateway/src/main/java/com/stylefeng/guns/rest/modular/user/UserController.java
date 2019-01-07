package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import com.stylefeng.guns.user.UserAPI;
import com.stylefeng.guns.user.vo.UserInfoModel;
import com.stylefeng.guns.user.vo.UserModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Reference(interfaceClass = UserAPI.class,check = false)
    private UserAPI userAPI;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseVO register(UserModel userModel) {
        if (userModel.getUsername() == null || userModel.getUsername().trim().length() == 0) {
            return ResponseVO.serviceFaile("用户名不存在");
        }
        if (userModel.getPassword() == null || userModel.getPassword().trim().length() == 0) {
            return ResponseVO.serviceFaile("密码不能为空");
        }
        boolean isSuccess = userAPI.register(userModel);
        if (isSuccess = false) {
            return ResponseVO.serviceFaile("用户注册失败");
        } else {
            return ResponseVO.success("用户注册成功");
        }
    }

    @RequestMapping(value = "check", method = RequestMethod.POST)
    public ResponseVO check(String username) {
        if (username != null && username.trim().length() > 0) {
            boolean noExists = userAPI.checkUsername(username);
            if (noExists) {
                return ResponseVO.success("用户名不存在");
            } else {
                return ResponseVO.serviceFaile("用户已存在");
            }
        } else {
            return ResponseVO.serviceFaile("输入的用户名为空");
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ResponseVO logout() {
          /*
            应用：
                1、前端存储JWT 【七天】 ： JWT的刷新
                2、服务器端会存储活动用户信息【30分钟】M
                3、JWT里的userId为key，查找活跃用户
            退出：
                1、前端删除掉JWT
                2、后端服务器删除活跃用户缓存
            现状：
                1、前端删除掉JWT
         */
        return ResponseVO.success("用户登出成功");
    }

    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    public ResponseVO getUserInfo() {
        //获取当前登陆用户
        String userId = CurrentUser.getCurrentUser();
        if (userId != null && userId.trim().length() > 0) {
            int uuid = Integer.parseInt(userId);
            //将用户Id传入后端
            UserInfoModel userInfoModel = userAPI.getUserInfo(uuid);
            if (userInfoModel != null) {
                return ResponseVO.success(userInfoModel);
            } else {
                return ResponseVO.serviceFaile("用户查询失败");
            }
        }else {
            return ResponseVO.serviceFaile("用户未登陆");
        }
    }

    @RequestMapping(value = "updateUserInfo",method = RequestMethod.POST)
    public ResponseVO updateUserInfo(UserInfoModel userInfoModel){
        //获取当前登陆的用户
        String userId = CurrentUser.getCurrentUser();
        if(userId != null && userId.trim().length()>0){
            //将用户Id传入数据库
            int uuid = Integer.parseInt(userId);
            //将本地的Id和传入的id比较
            if(uuid != userInfoModel.getUuid()){
                return ResponseVO.serviceFaile("业务异常，请修改你自己的用户信息");
            }else {
                UserInfoModel userInfoModel1 = userAPI.updateUserInfo(userInfoModel);
                if(userInfoModel1 != null){
                    return ResponseVO.success(userInfoModel1);
                }else {
                    return ResponseVO.serviceFaile("用户更新失败");
                }
            }
        }else {
            return ResponseVO.serviceFaile("用户未登陆");
        }
    }
}
