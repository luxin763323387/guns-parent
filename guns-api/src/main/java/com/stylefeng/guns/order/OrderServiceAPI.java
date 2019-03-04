package com.stylefeng.guns.order;

import com.stylefeng.guns.order.vo.OrderVO;

import java.util.List;

public interface OrderServiceAPI {

    //验证售出的票是否为真
    boolean isTrueSeats(String fieldId,String seats);

    //已经销售的座位，有没有这些座位(有被卖了的 false   没有被卖的true)
    boolean isNotSoldSeats(String fieldId,String seats);

    //创建订单信息
    OrderVO saveOrderInfo(Integer fieldId,String soldSeats,String seatsName,Integer userId);

    //使用当前登录人获取已购买的订单
    List<OrderVO> getOrderByUserId (Integer userId);

    //根据FieldId获取所有已经销售的座位
    String getSoldSeatByFieldId(Integer fieldId);
}
