package com.example.nalot.model.color;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RgbCode {
    int red;
    int green;
    int blue;

    public RgbCode(String hexCode){
        this.red = Integer.parseInt(hexCode.substring(1,3),16);
        this.green = Integer.parseInt(hexCode.substring(3,5),16);
        this.blue = Integer.parseInt(hexCode.substring(5),16);
    }

    public String toHexCode() {
        return "#" +
                Integer.toHexString(this.red) +
                Integer.toHexString(this.green) +
                Integer.toHexString(this.blue);
    }
}
