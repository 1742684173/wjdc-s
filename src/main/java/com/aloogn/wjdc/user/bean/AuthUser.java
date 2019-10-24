package com.aloogn.wjdc.user.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser implements Serializable {
    private Integer userId;
    private String userName;
    private String userNickname;
    private String role;
    private String token;
}
