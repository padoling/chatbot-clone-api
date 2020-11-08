package hellobot.api.service;

import hellobot.api.domain.input.InputRepository;
import hellobot.api.domain.message.MessageRepository;
import hellobot.api.domain.scenario.Scenario;
import hellobot.api.domain.scenario.ScenarioRepository;
import hellobot.api.dto.ScenarioPostRequestDto;
import hellobot.api.global.error.ErrorCode;
import hellobot.api.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final MessageRepository messageRepository;
    private final InputRepository inputRepository;

    @Transactional
    public String saveScenario(ScenarioPostRequestDto scenarioPostRequestDto) {
        if(scenarioRepository.findByName(scenarioPostRequestDto.getName()) == null) {
            Scenario scenario = scenarioRepository.save(scenarioPostRequestDto.toEntity());
            scenarioPostRequestDto.getMessageDtoList().stream().forEach(
                    messageDto -> {
                        messageDto.setScenarioId(scenario.getId());
                        messageRepository.save(messageDto.toEntity());
                    }
            );
            scenarioPostRequestDto.getInputDtoList().stream().forEach(
                    inputDto -> {
                        inputDto.setScenarioId(scenario.getId());
                        inputRepository.save(inputDto.toEntity());
                    }
            );
            return scenario.getId();
        } else {
            throw new GlobalException(ErrorCode.CONFLICT, "Requested scenario name already exists.");
        }
    }

    public String getDescMessage(String id) {
        Scenario scenario = scenarioRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
        return scenario.getDescMessage();
    }
}