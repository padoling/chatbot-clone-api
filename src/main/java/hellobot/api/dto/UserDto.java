package hellobot.api.dto;

import hellobot.api.domain.user.User;
import hellobot.api.domain.user.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

    private String id;
    private String name;
    private UserRole userRole;

    @Builder
    public UserDto(String id, String name, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.userRole = userRole;
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .name(name)
                .userRole(userRole)
                .build();
    }
}
