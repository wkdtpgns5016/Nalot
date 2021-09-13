package com.example.nalot.controller;

import com.example.nalot.model.color.ColorRequest;
import com.example.nalot.model.color.RgbCode;
import com.example.nalot.service.color.ColorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/colors")
public class ColorController {
    private final ColorService colorService;
    public ColorController(ColorService colorService) { this.colorService = colorService; }

    @PostMapping("/tone-on-tone-mixes")
    public List<RgbCode> getToneOnToneMixes(@RequestBody ColorRequest colorRequest){
        return colorService.getToneOnToneList(colorRequest.getHexCode()); }

    @PostMapping("/tone-in-tone-mixes")
    public List<RgbCode> getToneInToneMixes(@RequestBody ColorRequest colorRequest){
        return colorService.getToneInToneList(colorRequest.getHexCode()); }

}
