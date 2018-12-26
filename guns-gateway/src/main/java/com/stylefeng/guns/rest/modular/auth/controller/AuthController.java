package com.stylefeng.guns.rest.modular.auth.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import com.stylefeng.guns.user.UserAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Reference
    private UserAPI userAPI;

   /* @Resource(name = "simpleValidator")
    private IReqValidator reqValidator;*/

    @RequestMapping(value = "${jwt.auth-path}")
    public ResponseVO createAuthenticationToken(AuthRequest authRequest) {

        boolean validate = true;
        //去掉guns自身的用户名验证机制,使用自己的
        //int userId =  3;//userAPI.login(authRequest.getUserName(),authRequest.getPassword()); //测试模式
        int userId = userAPI.login(authRequest.getUserName(), authRequest.getPassword());
        if(userId == 0){
            validate = false;
        }

       // boolean validate = reqValidator.validate(authRequest);

        if (validate) {
            //randomKey 和 token审查完毕
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(""+userId, randomKey);
            //返回
            return ResponseVO.success(new AuthResponse(token, randomKey));
        //    return ResponseEntity.ok(new AuthResponse(token, randomKey));
        } else {
            return ResponseVO.serviceFaile("用户名或密码错误");
           // throw new GunsException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        }
    }
}
