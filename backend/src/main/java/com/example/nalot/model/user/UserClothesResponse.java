package com.example.nalot.model.user;

import com.example.nalot.model.clothes.ClothesDto;
import lombok.Data;

@Data
public class UserClothesResponse {
    String id;
    ClothesDto clothes;
    String color;
    String colorMix;

}
