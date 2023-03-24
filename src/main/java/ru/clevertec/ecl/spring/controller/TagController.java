package ru.clevertec.ecl.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class TagController {
    @GetMapping
    public String findTags() {
        return "This method should return list of tags";
    }
}
