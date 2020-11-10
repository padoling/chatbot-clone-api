package hellobot.api.service;

import hellobot.api.domain.user.User;
import hellobot.api.domain.user.UserRepository;
import hellobot.api.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public String saveTestUser(UserDto userDto) {
        User user = userRepository.save(userDto.toEntity());
        return user.getId();
    }
}
