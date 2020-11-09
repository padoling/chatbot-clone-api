package hellobot.api.domain.tarot;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TarotRepository extends MongoRepository<Tarot, String> {
}
