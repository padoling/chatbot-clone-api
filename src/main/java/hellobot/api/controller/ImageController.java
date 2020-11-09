package hellobot.api.controller;

import hellobot.api.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity postImage(@RequestPart String name,
                                    @RequestPart MultipartFile imageFile) {
        return new ResponseEntity<>(imageService.saveImage(name, imageFile), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getImageList(@PageableDefault(sort = {"id"}) Pageable pageable) {
        return new ResponseEntity<>(imageService.findImageList(pageable), HttpStatus.OK);
    }

}
