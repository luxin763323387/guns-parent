package com.stylefeng.guns.cinema;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.cinema.vo.*;

import java.util.List;

public interface CinemaServiceAPI {
    //1、根据CinemaQueryVO，查询影院列表
    Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO);

    //2、根据条件获取品牌列表[除了就99以外，其他的数字为isActive]
    List<BrandVO> getBradns(int brandId);

    //3、获取行政区域列表
    List<AreaVO> getArea(int areaId);

    //4、获取影厅类型列表
    List<HallTypeVO> getHallTypes(int hallTypeId);

    //5、根据影院编号，获取影院信息
    CinemaInfoVO getCinemaInfosById(int cinemaId);

    //6、获取所有电影的信息和对应的放映场次信息，根据影院编号
    List<FilmInfoVO> getFilmInfosByCinemaId(int cinemaId);

    //7、根据放映场次ID获取放映信息
    HallInfoVO getHallInfoById(int filmId);

    //8、根据放映场次查询播放的电影编号，然后根据电影编号获取对应的电影信息
    FilmInfoVO getFilmInfoByFilmId(int filmId);
}
