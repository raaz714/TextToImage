package com.texttosign.TextToSign.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.texttosign.TextToSign.service.TextToGraphics;

@RestController
public class TextToSignApi {

    TextToGraphics textToGraphics;

    public TextToSignApi() {
        textToGraphics = new TextToGraphics();
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "John") String name) {
        return String.format("Hello World %s!", name);
    }

    @GetMapping("/ttos")
    public String textToSign(@RequestParam(value = "name", defaultValue = "John") String name,
            @RequestParam(value = "style", defaultValue = "1") String option) {
        String base64Image = "";
        try {
            base64Image = textToGraphics.getImageFromText(name, Integer.parseInt(option));
        } catch (NumberFormatException nfe) {
            System.out.println(nfe);
            base64Image = textToGraphics.getImageFromText(name, 0);
        }

        return "data:image/png;base64," + base64Image;
    }
}

