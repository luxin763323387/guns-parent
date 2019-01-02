package com.stylefeng.guns.film;

import com.stylefeng.guns.film.vo.BannerVO;
import com.stylefeng.guns.film.vo.FilmInfo;
import com.stylefeng.guns.film.vo.FilmVO;

import java.util.List;

public interface FilmServiceAPI {

    //获取banners
    List<BannerVO> getBanners();

    //获取热映影片
    FilmVO getHotFilms (boolean isLimit, int nums);

    //获取即将上映影片(受欢迎程做排序)
    FilmVO getSoonFilms(boolean isLimit, int nums);

    //获取票房排行榜
    List<FilmInfo> getboxRanking();

    //获取人气排行榜
    List<FilmInfo> getExpectRanking();

    //获取top100
    List<FilmInfo> getTop();
}
