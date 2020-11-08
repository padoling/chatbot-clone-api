package hellobot.api.controller;

import hellobot.api.dto.ScenarioPostRequestDto;
import hellobot.api.service.ScenarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/scenario")
public class ScenarioController {

    private final ScenarioService scenarioService;

    @ApiOperation(value = "post scenario", notes = "새로운 시나리오 및 시나리오를 구성하는 메시지와 인풋을 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 409, message = "Conflicts")
    })
    @PostMapping
    public ResponseEntity postScenario(@RequestBody ScenarioPostRequestDto scenarioPostRequestDto) {
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
