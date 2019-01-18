
package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.film.FilmServiceAPI;
import com.stylefeng.guns.film.vo.*;
import com.stylefeng.guns.rest.modular.film.vo.FilmConditionVo;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmRequetVo;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @Reference(interfaceClass = FilmServiceAPI.class, check = false)
    private FilmServiceAPI filmServiceAPI;

    /**
     * 1、首页接口
     * @return
     */
    @RequestMapping(value = "getIndex", method = RequestMethod.GET)
    public ResponseVO<FilmIndexVO> getIndex() {
        FilmIndexVO filmIndexVO = new FilmIndexVO();

        //获取banners 滚轮图片
        filmIndexVO.setBanners(filmServiceAPI.getBanners());

        //获取hotFilms  热播电影
        filmIndexVO.setHotFilms(filmServiceAPI.getHotFilms(true, 8, 1, 1, 99, 99, 99));

        //获取soonFilms  将上映电影
        filmIndexVO.setSoonFilms(filmServiceAPI.getSoonFilms(true, 8, 1, 1, 99, 99, 99));

        //获取boxRanking  票房排行榜
        filmIndexVO.setBoxRanking(filmServiceAPI.getboxRanking());

        //获取boxRanking 受欢迎排行榜
        filmIndexVO.setExpectRanking(filmServiceAPI.getExpectRanking());

        //获取top100 前100
        filmIndexVO.setTop100(filmServiceAPI.getTop());

        return ResponseVO.success(IMG_PRE, filmIndexVO);
    }

    /**
     * 影片条件列表查询接口
     * @param catId
     * @param sourceId
     * @param yearId
     * @return
     */
    @RequestMapping(value = "getConditionList", method = RequestMethod.GET)
    public ResponseVO getConditionList(@RequestParam(name = "catId", required = false, defaultValue = "99") String catId,
                                       @RequestParam(name = "sourceId", required = false, defaultValue = "99") String sourceId,
                                       @RequestParam(name = "yearId", required = false, defaultValue = "99") String yearId) {

        FilmConditionVo filmConditionVo = new FilmConditionVo();
        //获取类型列表
        //获取 catId="99"的影片 如果为99 设置为active 为true
        //标识位置
        boolean flag = false;
        List<CatVO> catResult = new ArrayList<>();
        List<CatVO> catsList = filmServiceAPI.getCatsList();
        CatVO cat = null;
        for (CatVO catVO : catsList) {
            if (catVO.getCatId().equals("99")) {    //跳过
                cat = catVO;
                continue;
            }
            if (catVO.getCatId().equals(catId)) {
                flag = true;
                catVO.setActive(true);
            } else {
                catVO.setActive(false);
            }
            catResult.add(catVO);
        }

        //如果没有catId = "99" active 为false
        if (!flag) {
            cat.setActive(true);
            catResult.add(cat);
        } else {
            cat.setActive(false);
            catResult.add(cat);
        }

        //获取片源列表
        flag = false;
        List<SourceVO> sourcesList = filmServiceAPI.getSourcesList();
        List<SourceVO> sourceResult = new ArrayList<>();
        SourceVO source = null;
        for (SourceVO sourceVO : sourcesList) {
            if (sourceVO.getSourceId().equals("99")) {
                source = sourceVO;
                continue;
            }

            if (sourceVO.getSourceId().equals(sourceId)) {
                flag = true;
                sourceVO.setActive(true);
            } else {
                sourceVO.setActive(false);
            }
            sourceResult.add(sourceVO);
        }

        if (!flag) {
            source.setActive(true);
            sourceResult.add(source);
        } else {
            source.setActive(false);
            sourceResult.add(source);
        }

        //获取年代列表
        flag = false;
        List<YearVO> yearResult = new ArrayList<>();
        List<YearVO> yearsList = filmServiceAPI.getYearsList();
        YearVO year = null;
        for (YearVO yearVO : yearsList) {
            if (yearVO.getYearId().equals("99")) {
                year = yearVO;
                continue;
            }

            if (yearVO.getYearId().equals(yearId)) {
                flag = true;
                yearVO.setActive(true);
            }
            yearResult.add(yearVO);
        }

        if (!flag) {
            year.setActive(true);
            yearResult.add(year);
        } else {
            year.setActive(false);
            yearResult.add(year);
        }

        filmConditionVo.setCatInfo(catResult);
        filmConditionVo.setSourceInfo(sourceResult);
        filmConditionVo.setYearInfo(yearResult);

        return ResponseVO.success(filmConditionVo);
    }

    /**
     * 影片查询接口
     * @param filmRequetVo
     * @return
     */
    @RequestMapping(value = "getFilms", method = RequestMethod.GET)
    public ResponseVO getFilms(FilmRequetVo filmRequetVo) {
        String img_pre = "http://img.meetingshop.cn/";
        //根据showType来获取影片
        FilmVO filmVO = null;
        switch (filmRequetVo.getShowType()) {
            case 1:
                filmVO = filmServiceAPI.getHotFilms(false, filmRequetVo.getPageSize(), filmRequetVo.getNowPage(),
                        filmRequetVo.getSortId(), filmRequetVo.getCatId(), filmRequetVo.getSourceId(), filmRequetVo.getYearId()
                );
                break;
            case 2:
                filmVO = filmServiceAPI.getSoonFilms(false, filmRequetVo.getPageSize(), filmRequetVo.getNowPage(),
                        filmRequetVo.getSortId(), filmRequetVo.getCatId(), filmRequetVo.getSourceId(), filmRequetVo.getYearId()
                );
                break;
            case 3:
                filmVO = filmServiceAPI.getClassicFilms(filmRequetVo.getPageSize(), filmRequetVo.getNowPage(),
                        filmRequetVo.getSortId(), filmRequetVo.getCatId(), filmRequetVo.getSourceId(), filmRequetVo.getYearId());
                break;
            default:
                filmVO = filmServiceAPI.getHotFilms(false, filmRequetVo.getPageSize(), filmRequetVo.getNowPage(),
                        filmRequetVo.getSortId(), filmRequetVo.getCatId(), filmRequetVo.getSourceId(), filmRequetVo.getYearId()
                );
                break;
        }
        return ResponseVO.success(filmVO.getNowPage(), filmVO.getTotalPage(), img_pre, filmVO.getFilmInfo());
    }


    //4、影片详情查询接口
    @RequestMapping(value = "films/{searchParam}", method = RequestMethod.GET)
    public ResponseVO films(@PathVariable("searchParam") String searchParam, int searchType ){

        //根据searchType ->1 ,名称查询查询
        FilmDetailVO filmDetail = filmServiceAPI.getFilmDetail(searchType, searchParam);

        String filmId = filmDetail.getFilmId();
        //查询影片的详细信息 -> Dubbo的异步获取
        //获取影片描述信息
        FilmDescVO filmDescVO = filmServiceAPI.getFilmDesc(filmId);
        //获取导演信息
        ActorsVO director = filmServiceAPI.getDirector(filmId);
        //获取演员
        List<ActorsVO> actors = filmServiceAPI.getActors(filmId);
        //获取图片
        ImgsVO imgsVO = filmServiceAPI.getImgs(filmId);

        //组织info对象
        InfoRequstVO infoRequstVO = new InfoRequstVO();
        //组织Actor属性
        ActorRequestVO actorRequestVO = new ActorRequestVO();
        actorRequestVO.setActors(actors);
        actorRequestVO.setDirector(director);

        //组织info对象
        infoRequstVO.setActors(actorRequestVO);
        infoRequstVO.setBiography(filmDescVO.getBiography());
        infoRequstVO.setFilmId(filmId);
        infoRequstVO.setImgsVO(imgsVO);

        filmDetail.setInfo04(infoRequstVO);

        return ResponseVO.success("http://img.meetingshop.cn/",filmDetail);
    }

}

