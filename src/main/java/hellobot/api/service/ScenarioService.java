package hellobot.api.service;

import hellobot.api.domain.input.InputRepository;
import hellobot.api.domain.message.MessageRepository;
import hellobot.api.domain.scenario.Scenario;
import hellobot.api.domain.scenario.ScenarioRepository;
import hellobot.api.dto.ScenarioDto;
import hellobot.api.dto.ScenarioPostRequestDto;
import hellobot.api.global.error.ErrorCode;
import hellobot.api.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
            scenarioPostRequestDto.getMessageDtoList().forEach(
                    messageDto -> {
                        messageDto.setScenarioId(scenario.getId());
                        messageRepository.save(messageDto.toEntity());
                    }
            );
            scenarioPostRequestDto.getInputDtoList().forEach(
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

    public List<ScenarioDto> findScenarioList(Pageable pageable) {
        Page<ScenarioDto> scenarioDtoPage = scenarioRepository.findAll(pageable)
                .map(ScenarioDto::new);
        return new ArrayList<>(scenarioDtoPage.getContent());
    }

}