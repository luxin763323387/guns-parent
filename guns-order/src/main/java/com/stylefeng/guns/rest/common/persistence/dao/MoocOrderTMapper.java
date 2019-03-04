package com.stylefeng.guns.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.order.vo.OrderVO;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrderT;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author stevenlu
 * @since 2019-03-04
 */
public interface MoocOrderTMapper extends BaseMapper<MoocOrderT> {

    String getSeatByFieldId(@Param("fieldId") String fieldId);

    OrderVO getOrderInfoById(@Param("orderId") String orderId);

    List<OrderVO> getOrdersByUserId(@Param("userId") Integer userId);

    String getSoldSeatsByFieldId(@Param("fieldId") Integer fieldId);

}
