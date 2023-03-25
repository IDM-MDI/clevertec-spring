package ru.clevertec.ecl.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.spring.model.TagDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    @GetMapping
    public List<TagDTO> findTags() {
        return null;
    }
    @GetMapping("/{id}")
    public TagDTO findTag(@PathVariable long id) {
        return null;
    }
    @PostMapping
    public TagDTO saveTag(@RequestBody TagDTO tag) {
        return null;
    }
    @PatchMapping("/{id}")
    public TagDTO updateTag(@PathVariable long id,
                            @RequestBody TagDTO tag) {
        return null;
    }
    @GetMapping("/search")
    public List<TagDTO> findTags(TagDTO tag) {
        return null;
    }
}
