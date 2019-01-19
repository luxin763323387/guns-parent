package com.stylefeng.guns.film;

import com.stylefeng.guns.film.vo.ActorsVO;
import com.stylefeng.guns.film.vo.FilmDescVO;
import com.stylefeng.guns.film.vo.ImgsVO;

import java.util.List;

public interface FilmServiceAsyncAPI {

    //影片介绍 id查询
    FilmDescVO getFilmDesc(String filmId);

    //影片导演信息查询
    ActorsVO getDirector(String filmId);

    //影片演员信息查询
    List<ActorsVO> getActors(String filmId);

    //影片图片查询
    ImgsVO getImgs(String filmId);
}
