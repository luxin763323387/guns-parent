package com.stylefeng.guns.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.cinema.vo.FilmInfoVO;
import com.stylefeng.guns.rest.common.persistence.model.MoocFieldT;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 放映场次表 Mapper 接口
 * </p>
 *
 * @author stevenlu
 * @since 2019-01-23
 */
public interface MoocFieldTMapper extends BaseMapper<MoocFieldT> {

    List<FilmInfoVO> getFilmInfos(@Param("cinemaId")Integer cinemaId);

}
