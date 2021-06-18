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
        HslCode hslCode = rgbCode.convertHsl();

        for(int i=0; i<12; i++){
            hslCode.setHue(i * 30);
            RgbCode tone = hslCode.convertRgb();
            toneList.add(tone);
        }

        return toneList;
    }

    @Override
    public List<RgbCode> getToneOnToneList(String hexCode) {
        List<RgbCode> toneList = new ArrayList<>();
        RgbCode rgbCode = new RgbCode(hexCode);
        HslCode hslCode = rgbCode.convertHsl();

        for(int i=11; i>=0; i--){
            hslCode.setLightness((i * 7) + 17);
            RgbCode tone = hslCode.convertRgb();
            toneList.add(tone);
        }

        return toneList;
    }
}
