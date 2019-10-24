package com.aloogn.wjdc.user.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class User implements Serializable {
    private Integer id;

    private String name;

    private String nikename;

    private Boolean sex;

    private Date birth;

    private String tel;

    private String email;

    private Byte degree;

    private String password;

    private String position;

    private Byte status;

    private Date createTime;

    private Date updateTime;

}