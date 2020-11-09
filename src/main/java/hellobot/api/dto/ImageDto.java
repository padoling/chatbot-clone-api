package hellobot.api.dto;

import hellobot.api.domain.image.Image;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageDto {

    private String id;
    private String name;
    private String imageUrl;

    @Builder
    public ImageDto(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public ImageDto(Image document) {
        this.id = document.getId();
        this.name = document.getName();
        this.imageUrl = document.getImageUrl();
    }

    public Image toEntity() {
        return Image.builder()
                .id(id)
                .name(name)
                .imageUrl(imageUrl)
                .build();
    }
}
