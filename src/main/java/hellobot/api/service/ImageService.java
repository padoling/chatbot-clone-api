package hellobot.api.service;

import hellobot.api.domain.image.Image;
import hellobot.api.domain.image.ImageRepository;
import hellobot.api.dto.ImageDto;
import hellobot.api.global.error.ErrorCode;
import hellobot.api.global.error.GlobalException;
import hellobot.api.global.util.FileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final FileUploader fileUploader;

    public ImageDto saveImage(String name, MultipartFile imageFile) {
        if(imageFile != null) {
            String imageUrl = "";
            try {
                imageUrl = fileUploader.upload(name, imageFile);
            } catch(IOException e) {
                log.error("[ImageService] Image upload failed cause: {}", e.getLocalizedMessage());
                throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR, "Image upload failed.");
            }
            ImageDto imageDto = ImageDto.builder()
                    .name(name)
                    .imageUrl(imageUrl)
                    .build();
            Image image = imageRepository.save(imageDto.toEntity());
            return new ImageDto(image);
        }
        throw new GlobalException(ErrorCode.BAD_REQUEST, "ImageFile has no value.");
    }

    public List<ImageDto> findImageList(Pageable pageable) {
        Page<ImageDto> imageDtoPage = imageRepository.findAll(pageable)
                .map(ImageDto::new);
        return new ArrayList<>(imageDtoPage.getContent());
    }

    public List<ImageDto> findImageListById(List<String> imageList) {
        return imageRepository.findByIdIn(imageList).stream()
                .map(ImageDto::new)
                .collect(Collectors.toList());
    }
}
