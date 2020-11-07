package hellobot.api.domain.session;

import hellobot.api.domain.scenario.Scenario;
import hellobot.api.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document("session")
public class Session {

    @Id
    private String id;

    private User user;

    private Scenario scenario;
}
