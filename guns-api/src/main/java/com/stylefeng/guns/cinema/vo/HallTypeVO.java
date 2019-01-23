package com.stylefeng.guns.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class HallTypeVO implements Serializable {
    private String hallTypeId;
    private String halltypeName;
    private boolean isActive;
}
