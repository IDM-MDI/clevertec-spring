package ru.clevertec.ecl.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.spring.model.GiftCertificateDTO;

@RestController
@RequestMapping("/api/v1/gifts")
public class GiftCertificateController {
    @GetMapping
    public String findGifts() {
        return "This method should return list of gifts";
    }
    @GetMapping("/{id}")
    public String findGift(@PathVariable long id) {
        return "This method should return gift by id";
    }
    @PostMapping
    public String saveGift(@RequestBody GiftCertificateDTO gift) {
        return "This method should save gift and return it";
    }
    @PatchMapping("/{id}")
    public String updateGift(@PathVariable long id,
                             @RequestBody GiftCertificateDTO gift) {
        return "This method should update gift and return it";
    }
}
