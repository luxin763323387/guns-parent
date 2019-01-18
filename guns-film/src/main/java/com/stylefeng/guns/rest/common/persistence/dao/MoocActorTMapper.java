package com.stylefeng.guns.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.film.vo.ActorsVO;
import com.stylefeng.guns.rest.common.persistence.model.MoocActorT;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 演员表 Mapper 接口
 * </p>
 *
 * @author stevenlu
 * @since 2019-01-07
 */
public interface MoocActorTMapper extends BaseMapper<MoocActorT> {

    List<ActorsVO> getActors (@Param("filmId") String filmId);

}
