package pl.edu.utp.newTranslator2.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.utp.newTranslator2.translator.TextTranslator;

@RestController
@RequestMapping("/api")
public class TranslateController {
    @RequestMapping("/translate/{sourceLanguage}-{targetLanguage}/{text}")
    public TextTranslator translate(@PathVariable("sourceLanguage") String sourceLanguage, @PathVariable("targetLanguage") String targetLanguage, @PathVariable("text") String text) {
        return new TextTranslator(sourceLanguage, targetLanguage, text);
    }
}
