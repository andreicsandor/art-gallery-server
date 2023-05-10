package org.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/api/translations")
public class TranslationController {

    @GetMapping("/{languageCode}")
    public Map<String, String> getTranslations(@PathVariable String languageCode) {
        Locale locale = new Locale(languageCode);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", locale);
        Map<String, String> translations = new HashMap<>();

        for (String key : resourceBundle.keySet()) {
            translations.put(key, resourceBundle.getString(key));
        }

        return translations;
    }
}

