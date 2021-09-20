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

    public HslCode convertHsl(){
        double redDelta = (double)this.red / 255;
        double greenDelta = (double)this.green / 255;
        double blueDelta = (double)this.blue / 255;

        double cMax = Math.max(Math.max(redDelta, greenDelta), blueDelta);
        double cMin = Math.min(Math.min(redDelta, greenDelta), blueDelta);
        double delta = cMax - cMin;

        int hue;
        if(delta == 0) hue = 0;
        else if(cMax == redDelta) {
            double num = (greenDelta-blueDelta)/delta%6+6;
            if(num < 0) num += 6;
            hue = (int)(60 * num);
        }
        else if(cMax == greenDelta) {
            hue = (int)(60 * (((blueDelta-redDelta)/delta)+2));
        }
        else {
            hue = (int)(60 * (((redDelta-greenDelta)/delta)+4));
        }
        hue = hue % 360;

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
