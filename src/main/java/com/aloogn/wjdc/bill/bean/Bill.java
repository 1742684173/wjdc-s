package com.aloogn.wjdc.bill.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill implements Serializable {
    private Integer id;

    private Date dates;

    private BigDecimal sums;

    private Integer labelId;

    private Integer userId;

    private String descs;

    private Integer sortId;

    private Byte type;

    private Date createTime;

    private Date updateTime;
}