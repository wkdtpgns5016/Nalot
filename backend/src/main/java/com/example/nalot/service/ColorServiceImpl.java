package com.example.nalot.service;

import com.example.nalot.model.HslCode;
import com.example.nalot.model.RgbCode;

import java.util.ArrayList;
import java.util.List;

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
}
