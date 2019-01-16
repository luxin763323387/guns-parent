package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.film.FilmServiceAPI;
import com.stylefeng.guns.film.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = FilmServiceAPI.class)
public class DefaultFilmServiceImpl implements FilmServiceAPI {

    @Autowired
    private MoocBannerTMapper moocBannerTMapper;
    @Autowired
    private MoocFilmTMapper moocFilmTMapper;
    @Autowired
    private MoocCatDictTMapper moocCatDictTMapper;
    @Autowired
    private MoocYearDictTMapper moocYearDictTMapper;
    @Autowired
    private MoocSourceDictTMapper moocSourceDictTMapper;

    /**
     * banner图
     *
     * @return
     */
    @Override
    public List<BannerVO> getBanners() {
        List<BannerVO> result = new ArrayList<>();
        List<MoocBannerT> moocBanners = moocBannerTMapper.selectList(null);
        for (MoocBannerT moocBannerT : moocBanners) {
            BannerVO bannerVO = new BannerVO();
            bannerVO.setBannerId(moocBannerT.getUuid() + "");
            bannerVO.setBannerAddress(moocBannerT.getBannerAddress());
            bannerVO.setBannerUrl(moocBannerT.getBannerUrl());
            result.add(bannerVO);
        }
        return result;
    }

    private List<FilmInfo> getFilmInfos(List<MoocFilmT> moocFilms) {
        List<FilmInfo> filmInfos = new ArrayList<>();
        for (MoocFilmT moocFilmT : moocFilms) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setScore(moocFilmT.getFilmScore());
            filmInfo.setImgAddress(moocFilmT.getImgAddress());
            filmInfo.setFilmType(moocFilmT.getFilmType());
            filmInfo.setFilmScore(moocFilmT.getFilmScore());
            filmInfo.setFilmName(moocFilmT.getFilmName());
            filmInfo.setFilmId(moocFilmT.getUuid() + "");
            filmInfo.setExpectNum(moocFilmT.getFilmPresalenum());
            filmInfo.setBoxNum(moocFilmT.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(moocFilmT.getFilmTime()));

            filmInfos.add(filmInfo);
        }
        return filmInfos;
    }

    /**
     * 热映影片
     * @param isLimit
     * @param nums
     * @param nowPage
     * @param sortId
     * @param catId
     * @param sourceId
     * @param yearId
     * @return
     */
    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums, int nowPage, int sortId, int catId, int sourceId, int yearId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        //热映影片限制
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        //判断是否是首页需要的内容
        if (isLimit) {
            //如果是，则限制条数。限制为热映影片
            Page<MoocFilmT> page = new Page<>(1, nums);
            List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);

            //组织filminfos
            filmInfos = getFilmInfos(moocFilms);
            filmVO.setFilmNum(moocFilms.size());
            filmVO.setFilmInfo(filmInfos);

        } else {
            //如果不是，则是列表页，同样需要限制内容为热映影片

            Page<MoocFilmT> page = null;
            //排序方式，1-按热门搜索，2-按时间搜索，3-按评价搜索
            switch (sortId) {
                case 1:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
                case 2:
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage, nums, "film_score");
                    break;
                default:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
            }
            if (sourceId != 99) {
                entityWrapper.eq("film_source", sourceId);
            }
            if (yearId != 99) {
                entityWrapper.eq("film_dat", yearId);
            }
            if (catId != 99) {
                String catStr = "%#" + catId + "#%";
                entityWrapper.like("film_cats", catStr);
            }
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
            filmInfos = getFilmInfos(moocFilmTS);
            filmVO.setFilmNum(moocFilmTS.size());

            //分页  totalCounts/nums
            // 每页10容量，数据库有6条    6/10+1
            int totalPages = 0;
            int totalCounts = moocFilmTMapper.selectCount(entityWrapper);
            totalPages = (totalCounts / nums) + 1;

            filmVO.setFilmInfo(filmInfos);
            filmVO.setNowPage(nowPage); //页数
            filmVO.setTotalPage(totalPages); //总行数

        }
        return filmVO;
    }

    /**
     * 即将上映影片
     * @param isLimit
     * @param nums
     * @param nowPage
     * @param sortId
     * @param catId
     * @param sourceId
     * @param yearId
     * @return
     */
    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums, int nowPage, int sortId, int catId, int sourceId, int yearId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        //热映影片限制
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");

        //判断是否是首页需要的内容
        if (isLimit) {
            //如果是，则限制条数。限制为热映影片
            Page<MoocFilmT> page = new Page<>(1, nums);
            List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);

            //组织filminfos
            filmInfos = getFilmInfos(moocFilms);
            filmVO.setFilmNum(moocFilms.size());
            filmVO.setFilmInfo(filmInfos);

        } else {
            //如果不是，则是列表页，同样需要限制内容为即将上映映影片
            Page<MoocFilmT> page = null;
            //排序方式，1-按热门搜索，2-按时间搜索，3-按评价搜索
            switch (sortId) {
                case 1:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                case 2:
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                default:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
            }
            if (sourceId != 99) {
                entityWrapper.eq("film_source", sourceId);
            }
            if (yearId != 99) {
                entityWrapper.eq("film_date", yearId);
            }

            if (catId != 99) {
                String catIdStr = "%#" + catId + "#%";
                entityWrapper.like("film_cats", catIdStr);
            }
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
            filmInfos = getFilmInfos(moocFilmTS);
            filmVO.setFilmNum(moocFilmTS.size());

            int totalPage = 0;
            int totalCounts = moocFilmTMapper.selectCount(entityWrapper);
            totalPage = (totalCounts / nums) + 1;

            filmVO.setTotalPage(totalPage);
            filmVO.setFilmNum(nowPage);
            filmVO.setFilmInfo(filmInfos);

        }
        return filmVO;
    }

    /**
     * 经典影片
     * @param nums
     * @param nowPage
     * @param sortId
     * @param catId
     * @param sourceId
     * @param yearId
     * @return
     */
    @Override
    public FilmVO getClassicFilms( int nums, int nowPage, int sortId, int catId, int sourceId, int yearId) {

        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", 3);
        Page<MoocFilmT> page = null;
        //排序方式，1-按热门搜索，2-按时间搜索，3-按评价搜索
        switch (sortId) {
            case 1:
                page = new Page<>(nowPage, nums, "film_box_office");
                break;
            case 2:
                page = new Page<>(nowPage, nums, "film_time");
                break;
            case 3:
                page = new Page<>(nowPage, nums, "film_score");
                break;
            default:
                page = new Page<>(nowPage, nums, "film_box_office");
                break;
        }
        if (sourceId != 99) {
            entityWrapper.eq("film_source", sourceId);
        }
        if (yearId != 99) {
            entityWrapper.eq("film_yearld", yearId);
        }
        if (catId != 99) {
            String catIdStr = "%#" + catId + "%#";
            entityWrapper.like("film_cat", catIdStr);
        }
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
        filmInfos = getFilmInfos(moocFilmTS);
        filmVO.setFilmNum(moocFilmTS.size());

        int totalPage = 0;
        int totalCounts = moocFilmTMapper.selectCount(entityWrapper);
        totalPage = (totalCounts / nums) + 1;

        filmVO.setNowPage(nowPage);
        filmVO.setTotalPage(totalPage);
        filmVO.setFilmInfo(filmInfos);

        return filmVO;
    }

    /**
     * 获取票房排行榜(以上映)
     * @return
     */
    @Override
    public List<FilmInfo> getboxRanking() {
        List<FilmInfo> filmInfos = new ArrayList<>();

        //条件 -> 正在上映， 票房前100
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<MoocFilmT> page = new Page<>(1, 10, "film_box_office");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);

        //组织filminfos
        filmInfos = getFilmInfos(moocFilms);

        return filmInfos;
    }

    /**
     * 获取人气排行榜(没上映)
     * @return
     */
    @Override
    public List<FilmInfo> getExpectRanking() {
        List<FilmInfo> filmInfos = new ArrayList<>();

        //条件 -> 没上映， 预售前10
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");

        Page<MoocFilmT> page = new Page<>(1, 10, "film_preSaleNum");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);

        //组织filminfos
        filmInfos = getFilmInfos(moocFilms);

        return filmInfos;
    }

    /**
     *  获取top100(评分的前100)
     * @return
     */
    @Override
    public List<FilmInfo> getTop() {
        List<FilmInfo> filmInfos = new ArrayList<>();

        //条件 -> 正在上映， 评分前100
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<MoocFilmT> page = new Page<>(1, 100, "film_score");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);

        //组织filminfos
        filmInfos = getFilmInfos(moocFilms);

        return filmInfos;
    }

    /**
     * 通过分类别
     * @return
     */
    @Override
    public List<CatVO> getCatsList() {
        List<CatVO> cats = new ArrayList<>();
        //获取catVO对象
        List<MoocCatDictT> moocCatDictTS = moocCatDictTMapper.selectList(null);
        //对catVO赋值并返回
        for (MoocCatDictT moocCatDictTs : moocCatDictTS) {
            CatVO catVO = new CatVO();
            catVO.setCatId(moocCatDictTs.getUuid() + "");
            catVO.setCatName(moocCatDictTs.getShowName());
            cats.add(catVO);
        }
        return cats;
    }

    /**
     *  通过分地区的大片
     * @return
     */
    @Override
    public List<SourceVO> getSourcesList() {
        List<SourceVO> sources = new ArrayList<>();
        List<MoocSourceDictT> moocSourceDictTS = moocSourceDictTMapper.selectList(null);
        for (MoocSourceDictT moocSourceDictTs : moocSourceDictTS) {
            SourceVO sourceVO = new SourceVO();
            sourceVO.setSourceId(moocSourceDictTs.getUuid() + "");
            sourceVO.setSourceName(moocSourceDictTs.getShowName());
            sources.add(sourceVO);
        }
        return sources;
    }

    /**
     * 通过分年代的电影
     * @return
     */
    @Override
    public List<YearVO> getYearsList() {
        List<YearVO> years = new ArrayList<>();
        List<MoocYearDictT> moocYearDictTS = moocYearDictTMapper.selectList(null);
        for (MoocYearDictT moocYearDictTs : moocYearDictTS) {
            YearVO yearVO = new YearVO();
            yearVO.setYearId(moocYearDictTs.getUuid() + "");
            yearVO.setYearName(moocYearDictTs.getShowName());
            years.add(yearVO);
        }
        return years;
    }

    /**
     * 影片详情查询  0->按编号查询 1->按年代查询
     * @param searchType
     * @param searchParam
     * @return
     */
    @Override
    public FilmDetailVO getFilmDetail(int searchType, String searchParam) {
        FilmDetailVO filmDetailVO = null;
        if(searchType == 1){
            filmDetailVO = moocFilmTMapper.getFilmDetailByName(searchParam);
        }else {
            filmDetailVO = moocFilmTMapper.getFilmDetailById(searchParam);
        }
        return filmDetailVO;
    }

}
