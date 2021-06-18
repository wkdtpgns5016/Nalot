package com.example.nalot.model.user;

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
