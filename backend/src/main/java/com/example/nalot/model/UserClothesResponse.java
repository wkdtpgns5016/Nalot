package com.example.nalot.model;

import lombok.Data;

@Data
public class UserClothesResponse {
    int id;
    ClothesDto clothes;
    String color;
    String colorMix;

}
