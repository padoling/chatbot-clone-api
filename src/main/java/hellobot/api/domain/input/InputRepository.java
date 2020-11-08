package hellobot.api.domain.input;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface InputRepository extends MongoRepository<Input, String> {
}
