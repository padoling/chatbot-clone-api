package hellobot.api.controller;

import hellobot.api.dto.SessionDto;
import hellobot.api.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity startSession(@RequestBody SessionDto sessionDto) {
        return new ResponseEntity<>(sessionService.saveOrUpdateSession(sessionDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getSessionByUserId(@RequestParam String userId) {
        return new ResponseEntity<>(sessionService.findSessionByUserId(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity finishSession(@PathVariable String sessionId) {
        sessionService.deleteSession(sessionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
