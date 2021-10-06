package com.example.nalot.service.color;

import com.example.nalot.model.color.HslCode;
import com.example.nalot.model.color.RgbCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {

    @Override
    public List<RgbCode> getToneInToneList(String hexCode) {
        List<RgbCode> toneList = new ArrayList<>();
        RgbCode rgbCode = new RgbCode(hexCode);
        HslCode hslCode = convertHsl(rgbCode);

        for(int i=0; i<12; i++){
            hslCode.setHue(i * 30);
            RgbCode tone = convertRgb(hslCode);
            toneList.add(tone);
        }

        return toneList;
    }

    @Override
    public List<RgbCode> getToneOnToneList(String hexCode) {
        List<RgbCode> toneList = new ArrayList<>();
        RgbCode rgbCode = new RgbCode(hexCode);
        HslCode hslCode = convertHsl(rgbCode);

        for(int i=11; i>=0; i--){
            double lightness = ((i * 7) + 17) * 0.01;
            hslCode.setLightness(lightness);
            RgbCode tone = convertRgb(hslCode);
            toneList.add(tone);
        }

        return toneList;
    }

    @Override
    public RgbCode convertRgb(HslCode hslCode) {
        int hue = hslCode.getHue();
        double lightness = hslCode.getLightness();
        double saturation = hslCode.getSaturation();

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

    @Override
    public HslCode convertHsl(RgbCode rgbCode) {
        int red = rgbCode.getRed();
        int green = rgbCode.getGreen();
        int blue = rgbCode.getBlue();

        double redDelta = (double)red / 255;
        double greenDelta = (double)green / 255;
        double blueDelta = (double)blue / 255;

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
}
