package com.stylefeng.guns.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmVo implements Serializable {

    private int filmNum;
    private List<FilmInfo> filmInfos;

}
