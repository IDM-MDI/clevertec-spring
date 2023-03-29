package ru.clevertec.ecl.spring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.model.TagDTO;
import ru.clevertec.ecl.spring.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
@Validated
public class TagController {
    private final TagService service;
    @GetMapping
    public List<TagDTO> findTags(@Valid PageFilter page) {
        return service.findTags(page);
    }
    @GetMapping("/{id}")
    public TagDTO findTag(@PathVariable @Min(1) long id) {
        return service.findTag(id);
    }
    @PostMapping
    public TagDTO saveTag(@RequestBody @Valid TagDTO tag) {
        return service.save(tag);
    }
    @PatchMapping("/{id}")
    public TagDTO updateTag(@PathVariable long id,
                            @RequestBody TagDTO tag) {
        return service.update(tag, id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable @Min(1) long id) {
        service.delete(id);
        return ResponseEntity.ok("Tag successfully deleted");
    }

    @GetMapping("/search")
    public List<TagDTO> findTags(TagDTO tag) {
        return service.findTags(tag);
    }
}
