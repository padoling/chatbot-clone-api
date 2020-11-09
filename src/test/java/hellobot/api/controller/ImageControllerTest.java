package hellobot.api.controller;

import hellobot.api.IntegrationTest;
import hellobot.api.domain.image.Image;
import hellobot.api.domain.image.ImageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ImageControllerTest extends IntegrationTest {

    @Autowired
    private ImageRepository imageRepository;

    @BeforeEach
    public void setUp() {
        imageRepository.save(Image.builder()
                .name("lamama")
                .imageUrl("image/1604940118574_lamama.png")
                .build()
        );
    }

    @AfterEach
    public void cleanUp() {
        imageRepository.deleteAll();
    }

    @Test
    public void postImage_success() throws Exception {
        // given

    }

    @Test
    public void getImageList_success() throws Exception {
        // given
        
    }
}
