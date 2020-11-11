package hellobot.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hellobot.api.domain.input.InputType;
import hellobot.api.dto.SessionDto;
import hellobot.api.global.error.ErrorCode;
import hellobot.api.global.error.GlobalException;
import hellobot.api.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity startSession(@RequestBody SessionDto sessionDto) {
        return new ResponseEntity<>(sessionService.saveOrUpdateSession(sessionDto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getSessionByUserId(@PathVariable String userId) {
        return new ResponseEntity<>(sessionService.findSessionByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/message")
    public ResponseEntity getNextMessage(@RequestParam String scenarioId,
                                         @RequestParam String userId,
                                         @RequestParam InputType inputType,
                                         @RequestParam(required = false) String contents,
                                         @RequestParam int nextMessageNum) {
        Map<String, String> contentMap = new HashMap<>();
        if(contents != null) {
            try {
                contentMap = objectMapper.readValue(contents, new TypeReference<Map<String, String>>(){});
            } catch(Exception e) {
                log.error("[SessionController] Json parse failed cause : {}", e.getLocalizedMessage());
                throw new GlobalException(ErrorCode.BAD_REQUEST, "The form of parameter contents is not correct.");
            }
        }
        return new ResponseEntity<>(sessionService.findNextMessage(scenarioId, userId, inputType, contentMap, nextMessageNum), HttpStatus.OK);
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity finishSession(@PathVariable String sessionId) {
        sessionService.deleteSession(sessionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
