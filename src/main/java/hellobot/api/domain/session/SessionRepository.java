package hellobot.api.domain.session;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SessionRepository extends MongoRepository<Session, String> {

    public Optional<Session> findByUserId(String userId);

}
