package com.stylefeng.guns.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmInfo implements Serializable {
    private  String  filmId;
    private  int  filmType;  //[0-2D,1-3D,2-3DIMAX,4-无]
    private  String  imgAddress;
    private  String  filmName;
    private  String  filmScore;
    private  int expectNum; // 受欢迎的人数
    private  String  showTime;
    private  int boxNum;
    private  String  score;


}
