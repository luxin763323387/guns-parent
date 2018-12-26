package com.stylefeng.guns.rest.modular.example;

import com.stylefeng.guns.rest.common.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 常规控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@Controller
@RequestMapping("/hello")
public class ExampleController {

    @RequestMapping("")
    public ResponseEntity hello() {
        //System.out.println(simpleObject.getUser());
        System.out.println(CurrentUser.getCurrentUser());
        // 一般情况下  userId -> key -> redis[userInfo] -> 30min时效
        // 我们这次是  userId -> 条件，去数据库查询
        // 如果在threadLocal里存放的userInfo，则没有上面的过程，但是在后面业务增多后会出现爆内存。
        return ResponseEntity.ok("请求成功!");
    }
}
