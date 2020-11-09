package hellobot.api.service;

import hellobot.api.domain.image.Image;
import hellobot.api.domain.image.ImageRepository;
import hellobot.api.dto.ImageDto;
import hellobot.api.global.error.ErrorCode;
import hellobot.api.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageService {

    @Value("${aws.s3.path}")
    private String path;

    private final ImageRepository imageRepository;

    public ImageDto saveImage(String name, MultipartFile imageFile) {
        if(!imageFile.isEmpty()) {
            String originalFilename = imageFile.getOriginalFilename();
            String extension = null;
            if (originalFilename != null) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String imageUrl = path + name + "_" + System.currentTimeMillis() + extension;
            // s3 버킷 저장 -> 이동욱님 책 보고 하기

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
