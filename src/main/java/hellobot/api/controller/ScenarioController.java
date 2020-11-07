package hellobot.api.controller;

import hellobot.api.dto.ScenarioPostRequestDto;
import hellobot.api.service.ScenarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController("/scenario")
public class ScenarioController {

    private final ScenarioService scenarioService;

    @PostMapping
    public ResponseEntity saveScenario(@RequestBody ScenarioPostRequestDto scenarioPostRequestDto) {
        Map<String, String> response = new HashMap<>();
        response.put("scenarioId", scenarioService.saveScenario(scenarioPostRequestDto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/desc")
    public ResponseEntity getDescMessage(@PathVariable String id) {
        Map<String, String> response = new HashMap<>();
        response.put("descMessage", scenarioService.getDescMessage(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
