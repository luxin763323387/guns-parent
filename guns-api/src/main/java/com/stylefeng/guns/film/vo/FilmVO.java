package com.stylefeng.guns.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmVO implements Serializable {

    private int filmNum;
    private int nowPage; //当前页数
    private int totalPage; //总页数
    private List<FilmInfo> filmInfo;

}
