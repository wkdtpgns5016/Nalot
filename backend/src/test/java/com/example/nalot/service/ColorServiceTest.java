package com.example.nalot.service;

import com.example.nalot.NalotApplication;
import com.example.nalot.model.RgbCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NalotApplication.class)
public class ColorServiceTest {
    @Autowired
    ColorService colorService;

    @Test
    public void getToneInToneListTest(){
        //given
        String hexCode = "#9752e0";

        //when
        List<RgbCode> toneList = colorService.getToneInToneList(hexCode);

        //then
        assertThat(toneList.size()).isEqualTo(12);

    }

    @Test
    public void getToneOnToneListTest(){
        //given
        String hexCode = "#9752e0";

        //when
        List<RgbCode> toneList = colorService.getToneOnToneList(hexCode);

        //then
        assertThat(toneList.size()).isEqualTo(12);

    }
}
