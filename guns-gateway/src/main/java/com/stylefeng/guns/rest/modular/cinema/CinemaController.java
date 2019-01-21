package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.cinema.CinemaServiceAPI;
import com.stylefeng.guns.cinema.vo.CinemaQueryVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cinema/")
public class CinemaController {

    @Reference(interfaceClass = CinemaServiceAPI.class,check = false)
    private CinemaServiceAPI cinemaServiceAPI;

    //1、查询影院列表-根据条件查询所有影院
    @RequestMapping(value = "getCinemas",method = RequestMethod.GET)
    public ResponseVO getCinemas(CinemaQueryVO cinemaQueryVO){

        return null;
    }


    //2、获取影院列表查询条件
    @RequestMapping(value = "getCondition",method = RequestMethod.GET)
    public ResponseVO getCondition(CinemaQueryVO cinemaQueryVO){

        return null;
    }

    //3、获取播放场次接口
    @RequestMapping(value = "getFields")
    public ResponseVO getFields(Integer cinemaId){

        return null;
    }

    //4、获取场次详细信息接口
    @RequestMapping(value = "getFieldInfo",method = RequestMethod.POST)
    public ResponseVO getFieldInfo(Integer cinemaId,Integer fieldId){

        return null;
    }
}
