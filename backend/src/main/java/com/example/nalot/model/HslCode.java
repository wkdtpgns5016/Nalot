package com.example.nalot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HslCode {
    int hue;
    double saturation;
    double lightness;

    public RgbCode convertRgb(){
        double c = (1 - Math.abs(2 * lightness - 1)) * saturation;
        double x = c * (1 - Math.abs((double)hue / 60 % 2 - 1));
        double m = lightness - (c / 2);

        double redDelta;
        double greenDelta;
        double blueDelta;

        if(hue >= 0 && hue < 60) {
            redDelta = c;
            greenDelta = x;
            blueDelta = 0;
        }
        else if(hue >= 60 && hue < 120) {
            redDelta = x;
            greenDelta = c;
            blueDelta = 0;
        }
        else if(hue >= 120 && hue < 180) {
            redDelta = 0;
            greenDelta = c;
            blueDelta = x;
        }
        else if(hue >= 180 && hue < 240) {
            redDelta = 0;
            greenDelta = x;
            blueDelta = c;
        }
        else if(hue >= 240 && hue < 300) {
            redDelta = x;
            greenDelta = 0;
            blueDelta = c;
        }
        else {
            redDelta = c;
            greenDelta = 0;
            blueDelta = x;
        }

        int red = (int)((redDelta+m)*255);
        int green = (int)((greenDelta+m)*255);
        int blue = (int)((blueDelta+m)*255);

        return new RgbCode(red, green, blue);
    }
}
