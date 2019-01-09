
package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.film.FilmServiceAPI;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/film/")
public class FilmController {



/*
    * API网关
    *   1、功能聚合(API聚合)
    *   好处： 6个接口，一次请求 （同一时刻节省5此HTTP请求）
    *               同一个接口对外暴露，降低了前后端分离开发的难度和复杂度
    *
    *   坏处: 一次获取数据过多，如意出现问题
    * */
    private static final String IMG_PRE = "http://img.meetingshop.cn/";

    @Reference(interfaceClass = FilmServiceAPI.class,check = false)
        private FilmServiceAPI filmServiceAPI;

    @RequestMapping(value = "getIndex",method = RequestMethod.GET)
    public ResponseVO<FilmIndexVO> getIndex(){
        FilmIndexVO filmIndexVO = new FilmIndexVO();

        //获取banners 滚轮图片
        filmIndexVO.setBanners(filmServiceAPI.getBanners());

        //获取hotFilms  热播电影
        filmIndexVO.setHotFilms(filmServiceAPI.getHotFilms(true,8));

        //获取soonFilms  将上映电影
        filmIndexVO.setSoonFilms(filmServiceAPI.getSoonFilms(true,8));

        //获取boxRanking  票房排行榜
        filmIndexVO.setBoxRanking(filmServiceAPI.getboxRanking());

        //获取boxRanking 受欢迎排行榜
        filmIndexVO.setExpectRanking(filmIndexVO.getExpectRanking());

        //获取top100 前100
        filmIndexVO.setTop10(filmServiceAPI.getTop());


        return ResponseVO.success(IMG_PRE,filmIndexVO);
    }

    @RequestMapping(value = "getConditionList", method = RequestMethod.GET)
    public ResponseVO getConditionList(@RequestParam(name =  "catId", required = false,defaultValue = "99")String catId,
                                                                 @RequestParam(name =  "sourceId", required = false,defaultValue = "99")String sourceId,
                                                                 @RequestParam(name =  "yearId", required = false,defaultValue = "99")String yearId){
        //获取类型列表

        //获取片源列表

        //获取年代列表

        return null;
    }
}

