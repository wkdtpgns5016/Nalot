package com.example.nalot.model;

import com.example.nalot.NalotApplication;
import com.example.nalot.model.color.HslCode;
import com.example.nalot.model.color.RgbCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NalotApplication.class)
public class RgbCodeTest {
    @Test
    public  void convertHslTest() {
        //given
        String hex = "#d77229";
        RgbCode rgbCode = new RgbCode(hex);

        //when
        HslCode hslCode = rgbCode.convertHsl();

        //then
        System.out.println(hslCode.getHue());
        assertThat(hslCode.getHue()).isNotNull();

    }
}
