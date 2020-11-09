package hellobot.api.domain.image;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageRepository extends MongoRepository<Image, String> {

    public List<Image> findByIdIn(List<String> ids);
}
