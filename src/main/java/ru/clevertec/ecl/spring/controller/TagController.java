package ru.clevertec.ecl.spring.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;
import ru.clevertec.ecl.spring.model.TagDTO;
import ru.clevertec.ecl.spring.service.TagService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService service;
    @GetMapping
    public List<TagDTO> findTags(@RequestParam(defaultValue = "0") @Min(0) int page,
                                 @RequestParam(defaultValue = "10") @Min(1) int size,
                                 @RequestParam(defaultValue = "id") @NotBlank String filter,
                                 @RequestParam(defaultValue = "asc") @NotBlank String direction) throws SQLException {
        return service.findTags(page, size, filter, direction);
    }
    @GetMapping("/{id}")
    public TagDTO findTag(@PathVariable long id) throws SQLException {
        return service.findTag(id);
    }
    @PostMapping
    public TagDTO saveTag(@RequestBody TagDTO tag) throws SQLException {
        return service.save(tag);
    }
    @PatchMapping("/{id}")
    public TagDTO updateTag(@PathVariable long id,
                            @RequestBody TagDTO tag) throws SQLException {
        return service.update(tag, id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable long id) throws SQLException {
        service.delete(id);
        return ResponseEntity.ok("Tag successfully deleted");
    }

    @GetMapping("/search")
    public List<TagDTO> findTags(TagDTO tag) throws SQLException {
        return service.findTags(tag);
    }
}
