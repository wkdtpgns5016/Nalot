package com.example.nalot.service.color;

import com.example.nalot.model.color.RgbCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ColorServiceImplTest {
    @InjectMocks
    ColorServiceImpl colorService;

    @Test
    void getToneInToneList() {
        //given
        String hexCode = "#9752e0";

        //when
        List<RgbCode> result = colorService.getToneInToneList(hexCode);

        //then
        assertThat(result.get(0).toHexCode()).isEqualTo("#e05151");
    }

    @Test
    void getToneOnToneList() {
        //given
        String hexCode = "#9752e0";

        //when
        List<RgbCode> result = colorService.getToneOnToneList(hexCode);

        //then
        assertThat(result.get(0).toHexCode()).isEqualTo("#efe5fa");
    }
}