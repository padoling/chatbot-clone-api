package hellobot.api.domain.user;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document("user")
public class User {

    @Id
    private String id;

    private String name;

    private UserRole userRole;
}
