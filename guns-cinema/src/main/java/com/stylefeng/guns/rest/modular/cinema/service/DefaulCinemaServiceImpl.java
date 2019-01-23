package com.stylefeng.guns.rest.modular.cinema.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.cinema.CinemaServiceAPI;
import com.stylefeng.guns.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = CinemaServiceAPI.class)
public class DefaulCinemaServiceImpl implements CinemaServiceAPI {

    @Autowired





    @Override
    public Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO) {

        return null;
    }

    @Override
    public List<BrandVO> getBradns(int brandId) {
        return null;
    }

    @Override
    public List<AreaVO> getArea(int areaId) {
        return null;
    }

    @Override
    public List<HallTypeVO> getHallTypes(int hallTypeId) {
        return null;
    }

    @Override
    public CinemaInfoVO getCinemaInfosById(int cinemaId) {
        return null;
    }

    @Override
    public List<FilmInfoVO> getFilmInfosByCinemaId(int cinemaId) {
        return null;
    }

    @Override
    public HallInfoVO getHallInfoById(int filmId) {
        return null;
    }

    @Override
    public FilmInfoVO getFilmInfoByFilmId(int filmId) {
        return null;
    }
}
