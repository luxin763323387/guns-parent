package com.stylefeng.guns.rest.modular.film.vo;

import com.stylefeng.guns.film.vo.BannersVO;
import com.stylefeng.guns.film.vo.FilmInfo;
import com.stylefeng.guns.film.vo.FilmVo;

import java.util.List;

public class FilmIndexVO {

    private List<BannersVO> banners;
    private FilmVo hotFilms;
    private FilmVo soonFilms ;
    private List<FilmInfo> boxRanking;
    private List<FilmInfo> expectRanking;
    private List<FilmInfo> top100;

}
