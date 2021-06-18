package com.example.nalot.service.color;

import com.example.nalot.model.color.RgbCode;
import java.util.List;

public interface ColorService {
    public List<RgbCode> getToneInToneList(String hexCode);
    public List<RgbCode> getToneOnToneList(String hexCode);
}
