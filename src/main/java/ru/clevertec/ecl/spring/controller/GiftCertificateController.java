package ru.clevertec.ecl.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.spring.model.GiftCertificateDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gifts")
public class GiftCertificateController {
    @GetMapping
    public List<GiftCertificateDTO> findGifts() {
        return null;
    }
    @GetMapping("/{id}")
    public GiftCertificateDTO findGift(@PathVariable long id) {
        return null;
    }
    @PostMapping
    public GiftCertificateDTO saveGift(@RequestBody GiftCertificateDTO gift) {
        return null;
    }
    @PatchMapping("/{id}")
    public GiftCertificateDTO updateGift(@PathVariable long id,
                             @RequestBody GiftCertificateDTO gift) {
        return null;
    }
    @GetMapping("/search")
    public List<GiftCertificateDTO> findGifts(GiftCertificateDTO gift, String tag) {
        return null;
    }
}
