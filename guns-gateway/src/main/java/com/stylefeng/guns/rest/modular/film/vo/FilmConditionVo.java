package com.stylefeng.guns.rest.modular.film.vo;

import com.stylefeng.guns.film.vo.CatVO;
import com.stylefeng.guns.film.vo.SourceVO;
import com.stylefeng.guns.film.vo.YearVO;
import lombok.Data;

import java.util.List;

@Data
//condition->条件
public class FilmConditionVo {
    private List<CatVO>  catInfo;
    private List<SourceVO>  sourceInfo;
    private List<YearVO>  yearInfo;

}
