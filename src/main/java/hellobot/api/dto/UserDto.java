package hellobot.api.dto;

import hellobot.api.domain.user.User;
import hellobot.api.domain.user.UserRole;
import lombok.Getter;

@Getter
public class UserDto {

    private String id;
    private String name;
    private UserRole userRole;

    public User toEntity() {
        return User.builder()
                .id(id)
                .name(name)
                .userRole(userRole)
                .build();
    }
}
