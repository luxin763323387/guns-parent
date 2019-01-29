package com.stylefeng.guns.rest.modular.cinema.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.cinema.CinemaServiceAPI;
import com.stylefeng.guns.cinema.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.MoocAreaDictT;
import com.stylefeng.guns.rest.common.persistence.model.MoocBrandDictT;
import com.stylefeng.guns.rest.common.persistence.model.MoocCinemaT;
import com.stylefeng.guns.rest.common.persistence.model.MoocHallDictT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = CinemaServiceAPI.class)
public class DefaulCinemaServiceImpl implements CinemaServiceAPI {

    @Autowired
    private MoocAreaDictTMapper moocAreaDictTMapper;
    @Autowired
    private MoocBrandDictTMapper moocBrandDictTMapper;
    @Autowired
    private MoocCinemaTMapper moocCinemaTMapper;
    @Autowired
    private MoocFieldTMapper moocFieldTMapper;
    @Autowired
    private MoocHallDictTMapper moocHallDictTMapper;
    @Autowired
    private MoocHallFilmInfoTMapper moocHallFilmInfoTMapper;

    /**
     * 1、根据条件查询所有影院
     * @param cinemaQueryVO
     * @return
     */
    @Override
    public Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO) {

        List<CinemaVO> cinemas = new ArrayList<>();
        Page<MoocCinemaT> page = new Page<>(cinemaQueryVO.getNowSize(),cinemaQueryVO.getPageSize());

        //判断传入条件  brandId hallType districtId ->如果是 否 则变成99 全部
        EntityWrapper<MoocCinemaT> entityWrapper = new EntityWrapper<>();
        if(cinemaQueryVO.getBrandId() !=99) {
            entityWrapper.eq("brand_id", cinemaQueryVO.getBrandId());
        }

        if(cinemaQueryVO.getDistrictId() !=99) {
            entityWrapper.eq("area_id", cinemaQueryVO.getDistrictId());
        }

        if(cinemaQueryVO.getHallType() !=99) {
            entityWrapper.like("hall_ids", "%#" + cinemaQueryVO.getHallType() + "#%");
        }

        //根据业务实体返回数据实体
        List<MoocCinemaT> moocCinemaTS = moocCinemaTMapper.selectPage(page, entityWrapper);
        for(MoocCinemaT cinemaT : moocCinemaTS ){
            CinemaVO cinemaVO = new CinemaVO();
            cinemaVO.setUuid(""+cinemaT.getUuid());
            cinemaVO.setCinemaName(cinemaT.getCinemaName());
            cinemaVO.setAddress(""+cinemaT.getAreaId());
            cinemaVO.setMinimumPrice(""+cinemaT.getMinimumPrice());   //最低票价
            cinemas.add(cinemaVO);
        }

        //根据条件，判断列表总数
        long counts = moocCinemaTMapper.selectCount(entityWrapper);

        //组织返回对象
        Page<CinemaVO> result = new Page<>();
        result.setRecords(cinemas); //所有纪录
        result.setSize(cinemaQueryVO.getPageSize()); //每页条数
        result.setTotal(counts);//列表总数
        return result;
    }

    /**
     * 获取影院列表查询条件  brandId
     * @param brandId
     * @return
     */
    @Override
    public List<BrandVO> getBradns(int brandId) {
        boolean flag = false;
        List<BrandVO> brandVOS = new ArrayList<>();
        //判断是brandId是否存在
        MoocBrandDictT moocBrandDictT = moocBrandDictTMapper.selectById(brandId);
        if(brandId == 99 || moocBrandDictT == null || moocBrandDictT.getUuid() == null){
            flag = true;
        }

        //查询list,  判断传入条件  brandId ->如果是 否 则变成99   查询全部
        List<MoocBrandDictT> moocBrandDictTS = moocBrandDictTMapper.selectList(null);
        for(MoocBrandDictT brand : moocBrandDictTS ){
            BrandVO brandVO = null;
            brandVO.setBrandId(brand.getUuid()+"");
            brandVO.setBrandName(brand.getShowName());
            if(flag){
                if(brand.getUuid() == 99) {
                    brandVO.setActive(true);
                }
            }else {
                if(brand.getUuid() == brandId){
                    brandVO.setActive(true);
                }
            }
            brandVOS.add(brandVO);
        }
        return brandVOS;
    }

    /**
     * 获取影院列表查询条件 areaId
     * @param areaId
     * @return
     */
    @Override
    public List<AreaVO> getArea(int areaId) {
        boolean flag = false;
        List<AreaVO> areaVOS = new ArrayList<>();
        //判断areaId是否存在
        MoocAreaDictT moocAreaDictT = moocAreaDictTMapper.selectById(areaId);
        if(areaId == 99 || moocAreaDictT == null || moocAreaDictT.getUuid() == null){
            flag = true;
        }
        //查询所有List，判断传入条件  brandId ->如果是 否 则变成99   查询全部
        List<MoocAreaDictT> moocAreaDictTS = moocAreaDictTMapper.selectList(null);
        for(MoocAreaDictT area : moocAreaDictTS){
            AreaVO areaVO = null;
            areaVO.setAreaId(area.getUuid()+"");
            areaVO.setAreaName(area.getShowName());
            if(flag) {
                if(area.getUuid() == 99){
                    areaVO.setActive(true);
                }
                if(area.getUuid() == areaId){
                    areaVO.setActive(true);
                }
            }
            areaVOS.add(areaVO);
        }
        return areaVOS;
    }


    @Override
    public List<HallTypeVO> getHallTypes(int hallTypeId) {
        boolean flag = false;
        List<HallTypeVO> hallTypeVOS = new ArrayList<>();
        //判断 hallTypeId是否存在
        MoocHallDictT moocHallDictT = moocHallDictTMapper.selectById(hallTypeId);
        if(hallTypeId ==99 || moocHallDictT == null || moocHallDictT.getUuid() == null ) {
            flag = true;
        }
        //查询所有List 如果hallTypeId == 99  -> 则显示全部
        List<MoocHallDictT> moocHallDictTS = moocHallDictTMapper.selectList(null);
        for(MoocHallDictT hallType : moocHallDictTS){
            HallTypeVO hallTypeVO = null;
            hallTypeVO.setHallTypeId(hallType.getUuid()+"");
            hallTypeVO.setHalltypeName(hallType.getShowName());
            if(flag){
                if(hallType.getUuid() == 99){
                    hallTypeVO.setActive(true);
                }
                if(hallType.getUuid() == hallTypeId){
                    hallTypeVO.setActive(true);
                }
            }
            hallTypeVOS.add(hallTypeVO);
        }
        return hallTypeVOS;
    }

    @Override
    public CinemaInfoVO getCinemaInfosById(int cinemaId) {
        //获取数据实体
        MoocCinemaT cinemaT = moocCinemaTMapper.selectById(cinemaId);
        //将数据实体放入CinemaInfoVO返回
        CinemaInfoVO cinemaInfoVO = new CinemaInfoVO();
        cinemaInfoVO.setCinemaAdress(cinemaT.getCinemaAddress());
        cinemaInfoVO.setCinemaId(cinemaT.getUuid()+"");
        cinemaInfoVO.setCinemaName(cinemaInfoVO.getCinemaName());
        cinemaInfoVO.setCinemaPhone(cinemaT.getCinemaPhone());
        cinemaInfoVO.setImgUrl(cinemaT.getImgAddress());

        return cinemaInfoVO;
    }

    @Override
    public List<FilmInfoVO> getFilmInfosByCinemaId(int cinemaId) {
        //获取数据实体
        List<FilmInfoVO> filmInfos = moocFieldTMapper.getFilmInfos(cinemaId);
        //将数据实体放入FilmInfoVO返回
        return filmInfos;
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
