package com.example.nalot.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserHistoryRequest {
    String userId;
    float temperature;
    int clothesId;
    String color;
    String colorMix;
}
