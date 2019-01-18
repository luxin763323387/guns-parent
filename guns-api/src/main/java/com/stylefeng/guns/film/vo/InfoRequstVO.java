package com.stylefeng.guns.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfoRequstVO implements Serializable {
    private String biography;
    private ActorRequestVO actors;
    private ImgsVO imgsVO;
    private String filmId;
}
