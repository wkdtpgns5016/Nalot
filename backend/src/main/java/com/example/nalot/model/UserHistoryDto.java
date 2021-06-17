package com.example.nalot.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class UserHistoryDto {
    int id;
    String userId;
    String weatherId;
    String userClothesId;
    Timestamp historyDate;
}
