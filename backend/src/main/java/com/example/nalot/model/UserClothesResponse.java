package com.example.nalot.model;

import lombok.Data;

@Data
public class UserClothesResponse {
    int id;
    Clothes clothes;
    String color;
    String colorMix;

    @Data
    public static class Clothes {
        ClothesDto clothesDto;
    }
}