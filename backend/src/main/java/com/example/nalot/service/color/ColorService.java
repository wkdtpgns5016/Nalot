package com.example.nalot.service.color;

import com.example.nalot.model.color.HslCode;
import com.example.nalot.model.color.RgbCode;
import java.util.List;

public interface ColorService {
    public List<RgbCode> getToneInToneList(String hexCode);
    public List<RgbCode> getToneOnToneList(String hexCode);
    public RgbCode convertRgb(HslCode hslCode);
    public HslCode convertHsl(RgbCode rgbCode);
}
