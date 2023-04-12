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
import ru.clevertec.ecl.spring.model.GiftCertificateDTO;
import ru.clevertec.ecl.spring.service.GiftCertificateService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gifts")
@RequiredArgsConstructor
@Validated
public class GiftCertificateController {
    private final GiftCertificateService service;
    @GetMapping
    public List<GiftCertificateDTO> findGifts(@Valid PageFilter page) {
        return service.findGifts(page);
    }
    @GetMapping("/{id}")
    public GiftCertificateDTO findGift(@PathVariable @Min(1) long id) {
        return service.findGift(id);
    }
    @PostMapping
    public GiftCertificateDTO saveGift(@RequestBody @Valid GiftCertificateDTO gift) {
        return service.save(gift);
    }
    @PatchMapping("/{id}")
    public GiftCertificateDTO updateGift(@PathVariable @Min(1) long id,
                             @RequestBody GiftCertificateDTO gift) {
        return service.update(gift, id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGift(@PathVariable @Min(1) long id) {
        service.delete(id);
        return ResponseEntity.ok("Gift certificate successfully deleted");
    }
    @GetMapping("/search")
    public List<GiftCertificateDTO> findGifts(GiftCertificateDTO gift, String tag) {
        return service.findGifts(gift, tag);
    }
}
