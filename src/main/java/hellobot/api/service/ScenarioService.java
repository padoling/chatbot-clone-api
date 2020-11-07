package hellobot.api.service;

import hellobot.api.domain.scenario.Scenario;
import hellobot.api.domain.scenario.ScenarioRepository;
import hellobot.api.dto.ScenarioPostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;

    @Transactional
    public String saveScenario(ScenarioPostRequestDto scenarioPostRequestDto) {
        if(scenarioRepository.findByName(scenarioPostRequestDto.getName()) == null) {
            Scenario scenario = scenarioRepository.save(scenarioPostRequestDto.toEntity());
            //tbd
            return scenario.getId();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getDescMessage(String id) {
        Scenario scenario = scenarioRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return scenario.getDescMessage();
    }
}
