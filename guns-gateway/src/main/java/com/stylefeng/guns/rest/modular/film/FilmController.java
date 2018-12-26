package com.stylefeng.guns.rest.modular.film;

import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "getIndex",method = RequestMethod.GET)
    public ResponseVO getIndex(){
        //获取banners 滚轮图片

        //获取hotFilms  热播电影

        //获取soonFilms  将上映电影

        //获取boxRanking  票房排行榜

        //获取boxRanking 受欢迎排行榜

        //获取top100 前100
        return null;
    }
}
