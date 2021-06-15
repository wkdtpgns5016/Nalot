package com.example.nalot.service;

import com.example.nalot.model.RgbCode;
import java.util.List;

public interface ColorService {
    public List<RgbCode> getToneInToneList(String hexCode);
    public List<RgbCode> getToneOnToneList(String hexCode);
}
