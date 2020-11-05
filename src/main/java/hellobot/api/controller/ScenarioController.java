package hellobot.api.controller;

import hellobot.api.dto.ScenarioPostDto;
import hellobot.api.service.ScenarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController("/scenario")
public class ScenarioController {

    private final ScenarioService scenarioService;

    @PostMapping
    public ResponseEntity saveScenario(@RequestBody ScenarioPostDto scenarioPostDto) {
        Map<String, Long> response = new HashMap<>();
        response.put("scenarioId", scenarioService.saveScenario(scenarioPostDto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
