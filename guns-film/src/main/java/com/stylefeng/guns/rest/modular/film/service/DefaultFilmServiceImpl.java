package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.film.FilmServiceAPI;
import com.stylefeng.guns.film.vo.BannerVO;
import com.stylefeng.guns.film.vo.FilmInfo;
import com.stylefeng.guns.film.vo.FilmVO;
import com.stylefeng.guns.rest.common.persistence.dao.MoocBannerTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocBannerT;
import com.stylefeng.guns.rest.common.persistence.model.MoocFilmT;
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

    /**
     * banner图
     * @return
     */
    @Override
    public List<BannerVO> getBanners() {
        List<BannerVO> result = new ArrayList<>();
        List<MoocBannerT> moocBanners= moocBannerTMapper.selectList(null);
        for(MoocBannerT moocBannerT : moocBanners){
            BannerVO bannerVO = new BannerVO();
            bannerVO.setBannerId(moocBannerT.getUuid()+"");
            bannerVO.setBannerAddress(moocBannerT.getBannerAddress());
            bannerVO.setBannerUrl(moocBannerT.getBannerUrl());
            result.add(bannerVO);
        }
        return result;
    }


    private List<FilmInfo> getFilmInfos (List<MoocFilmT> moocFilms){
        List<FilmInfo> filmInfos = new ArrayList<>();
        for(MoocFilmT moocFilmT : moocFilms){
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setScore(moocFilmT.getFilmScore());
            filmInfo.setImgAddress(moocFilmT.getImgAddress());
            filmInfo.setFilmType(moocFilmT.getFilmType());
            filmInfo.setFilmScore(moocFilmT.getFilmScore());
            filmInfo.setFilmName(moocFilmT.getFilmName());
            filmInfo.setFilmId(moocFilmT.getUuid()+"");
            filmInfo.setExpectNum(moocFilmT.getFilmPresalenum());
            filmInfo.setBoxNum(moocFilmT.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(moocFilmT.getFilmTime()));

            filmInfos.add(filmInfo);
      }
        return filmInfos;
    }


    /**
     *  热映影片
     * @param isLimit
     * @param nums
     * @return
     */
    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        //热映影片限制
        EntityWrapper<MoocFilmT>entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","1");

        //判断是否是首页需要的内容
        if(isLimit) {
            //如果是，则限制条数。限制为热映影片
           Page<MoocFilmT> page = new Page<>(1,nums);
            List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);

            //组织filminfos
            filmInfos = getFilmInfos(moocFilms);
            filmVO.setFilmNum(moocFilms.size());
            filmVO.setFilmInfos(filmInfos);

        }else {
            //如果不是，则是列表页，同样需要限制内容为热映影片

        }
        return filmVO;
    }

    /**
     *  即将上映影片
     * @param isLimit
     * @param nums
     * @return
     */
    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        //热映影片限制
        EntityWrapper<MoocFilmT>entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","2");

        //判断是否是首页需要的内容
        if(isLimit) {
            //如果是，则限制条数。限制为热映影片
            Page<MoocFilmT> page = new Page<>(1,nums);
            List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);

            //组织filminfos
            filmInfos = getFilmInfos(moocFilms);
            filmVO.setFilmNum(moocFilms.size());
            filmVO.setFilmInfos(filmInfos);

        }else {
            //如果不是，则是列表页，同样需要限制内容为热映影片

        }
        return filmVO;
    }

    //获取票房排行榜(以上映)
    @Override
    public List<FilmInfo> getboxRanking() {
        List<FilmInfo> filmInfos = new ArrayList<>();

        //条件 -> 正在上映， 票房前10
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","1");

        Page<MoocFilmT> page = new Page<>(1,10,"film_box_office");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);

        //组织filminfos
        filmInfos = getFilmInfos(moocFilms);

        return filmInfos;
    }

    //获取人气排行榜(没上映)
    @Override
    public List<FilmInfo> getExpectRanking() {
        List<FilmInfo> filmInfos = new ArrayList<>();

        //条件 -> 没上映， 预售前10
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","2");

        Page<MoocFilmT> page = new Page<>(1,10,"film_preSaleNum");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);

        //组织filminfos
        filmInfos = getFilmInfos(moocFilms);

        return filmInfos;
    }


    //获取top10(评分的前10)
    @Override
    public List<FilmInfo> getTop() {
        List<FilmInfo> filmInfos = new ArrayList<>();

        //条件 -> 正在上映， 评分前十
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","1");

        Page<MoocFilmT> page = new Page<>(1,10,"film_score");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);

        //组织filminfos
        filmInfos = getFilmInfos(moocFilms);

        return filmInfos;
    }

}
