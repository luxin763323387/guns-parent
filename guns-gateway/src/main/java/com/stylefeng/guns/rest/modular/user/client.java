package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.user.UserAPI;
import org.springframework.stereotype.Component;

/**
 * @author Steven Lu
 * @date 2018/12/17 -20:38
 */
@Component
public class client {

    @Reference
    private UserAPI userAPI;

    public void run(){
        userAPI.login("admin","password");
    }
}
