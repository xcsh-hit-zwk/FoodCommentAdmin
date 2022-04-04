package com.example.foodcommentadmin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;
    private String userId;
    private String password;
    private String nickname;
    private String createTime;
    private String modTime;
    private Boolean hasDelete;
}
