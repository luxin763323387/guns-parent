package com.stylefeng.guns.rest.modular.order;

import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order/")
public class OrderController {

    //1、用户下单购票接口
    @RequestMapping(value = "buyTickets", method = RequestMethod.POST)
    public ResponseVO buyTickets(Integer fieldId,String soldSeats,String seatsName){

        //验证售出的票是否为真

        //已经销售的座位，有没有这些座位

        //创建订单信息,注意获取登入人

        return null;
    }

    //2、获取用户订单信息接口
    @RequestMapping(value = "getOrderInfo",method = RequestMethod.POST)
    public ResponseVO getOrderInfo(
            @RequestParam(name ="nowPage",required = false,defaultValue = "1")Integer nowPage,
            @RequestParam(name="pageSize",required = false,defaultValue = "5")Integer pageSize
    ){

        //获取当前登入人的信息

        //使用当前登录人获取已购买的订单



        return null;
    }
}


