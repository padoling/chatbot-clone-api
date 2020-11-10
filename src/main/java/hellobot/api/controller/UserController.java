package hellobot.api.controller;

import hellobot.api.dto.UserDto;
import hellobot.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity postTestUser(@RequestBody UserDto userDto) {
        Map<String, String> response = new HashMap<>();
        response.put("userId", userService.saveTestUser(userDto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
