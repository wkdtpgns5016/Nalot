package com.example.nalot.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class UserDto {
    String id;
    String password;
    String name;
    String gender;
    String zoneCode;
    String addressBasic;
    String addressDetail;
    String addressGroundNumber;
    Timestamp birth;
}
