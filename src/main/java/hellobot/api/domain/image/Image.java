package hellobot.api.domain.image;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document("image")
public class Image {

    @Id
    private String id;

    private String name;

    private String imageUrl;
}
