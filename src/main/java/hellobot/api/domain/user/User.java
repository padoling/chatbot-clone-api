package hellobot.api.domain.user;

import lombok.Builder;
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

    @Builder
    public User(String id, String name, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.userRole = userRole;
    }
}
