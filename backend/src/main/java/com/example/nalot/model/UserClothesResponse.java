package com.example.nalot.model;

import lombok.Data;

@Data
public class UserClothesResponse {
    String id;
    ClothesDto clothes;
    String color;
    String colorMix;

}
