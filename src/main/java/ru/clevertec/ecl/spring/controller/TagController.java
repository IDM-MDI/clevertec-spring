package ru.clevertec.ecl.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.spring.model.TagDTO;

@RestController
@RequestMapping("/tags")
public class TagController {
    @GetMapping
    public String findTags() {
        return "This method should return list of tags";
    }
    @GetMapping("/{id}")
    public String findTag(@PathVariable long id) {
        return "This method should return tag by id";
    }
    @PostMapping
    public String saveTag(@RequestBody TagDTO tag) {
        return "This method should save tag and return it";
    }
    @PatchMapping("/{id}")
    public String updateTag(@PathVariable long id,
                            @RequestBody TagDTO tag) {
        return "This method should update tag and return it";
    }
}
