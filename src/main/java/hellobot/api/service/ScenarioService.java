package hellobot.api.service;

import hellobot.api.domain.scenario.Scenario;
import hellobot.api.domain.scenario.ScenarioRepository;
import hellobot.api.dto.ScenarioPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;

    @Transactional
    public Long saveScenario(ScenarioPostDto scenarioPostDto) {
        if(scenarioRepository.findByName(scenarioPostDto.getName()) != null) {
            // logic
        } else {
            // throw exception
        }
        return 1L;
    }
}
