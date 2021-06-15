package com.example.nalot.model;

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
        this.red = Integer.decode(hexCode.substring(1,3));
        this.green = Integer.decode(hexCode.substring(2,5));
        this.blue = Integer.decode(hexCode.substring(5));
    }

    public HslCode convertHsl(){
        double redDelta = this.red / 255;
        double greenDelta = this.green / 255;
        double blueDelta = this.blue / 255;

        double cMax = Math.max(Math.max(redDelta, greenDelta), blueDelta);
        double cMin = Math.min(Math.min(redDelta, greenDelta), blueDelta);
        double delta = cMax - cMin;

        int hue;
        if(delta == 0) hue = 0;
        else if(cMax == redDelta) {
            hue = 60 * (int)(((greenDelta-blueDelta)/delta)%6);
        }
        else if(cMax == greenDelta) {
            hue = 60 * (int)(((blueDelta-redDelta)/delta)+2);
        }
        else {
            hue = 60 * (int)(((redDelta-greenDelta)/delta)+4);
        }

        double lightness = (cMax+cMin) / 2;

        double saturation;
        if(delta == 0) saturation = 0;
        else saturation = delta / (1-Math.abs(2*lightness-1));

        return new HslCode(hue,saturation,lightness);
    }

    public String toHexCode() {
        return "#" +
                Integer.toHexString(this.red) +
                Integer.toHexString(this.green) +
                Integer.toHexString(this.blue);
    }
}
