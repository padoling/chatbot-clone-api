package hellobot.api.service;

import hellobot.api.domain.input.Input;
import hellobot.api.domain.input.InputRepository;
import hellobot.api.domain.message.Message;
import hellobot.api.domain.message.MessageRepository;
import hellobot.api.domain.scenario.Scenario;
import hellobot.api.domain.scenario.ScenarioRepository;
import hellobot.api.domain.session.SessionRepository;
import hellobot.api.dto.ImageDto;
import hellobot.api.dto.NextMessageDto;
import hellobot.api.dto.SessionDto;
import hellobot.api.global.error.ErrorCode;
import hellobot.api.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final ScenarioRepository scenarioRepository;
    private final MessageRepository messageRepository;
    private final InputRepository inputRepository;
    private final ImageService imageService;

    @Transactional
    public NextMessageDto saveSession(SessionDto sessionDto) {
        Scenario scenario = scenarioRepository.findById(sessionDto.getScenarioId())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
        Map<String, String> variables = new HashMap<>();
        if(!scenario.getVariables().isEmpty()) {
            scenario.getVariables().forEach(
                    variable -> variables.put(variable, "")
            );
        }
        sessionDto.setVariables(variables);
        sessionRepository.save(sessionDto.toEntity());

        Message message = messageRepository.findByScenarioIdAndNumber(scenario.getId(), sessionDto.getMessageNumber())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
        Input input = inputRepository.findByScenarioIdAndNumber(scenario.getId(), message.getNextInputNum())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

        List<String> imageUrlList = new ArrayList<>();
        if(!message.getImageList().isEmpty()) {
            imageUrlList = imageService.findImageListById(message.getImageList()).stream()
                    .map(ImageDto::getImageUrl)
                    .collect(Collectors.toList());
        }

        return NextMessageDto.builder()
                .messageContent(message.getContents())
                .imageUrlList(imageUrlList)
                .inputType(input.getInputType())
                .inputContents(input.getContents())
                .nextMessageNums(input.getNextMessageNums())
                .build();
    }

    public String updateSession() {
        // TODO
        return null;
    }

    public String deleteSession() {
        // TODO
        return null;
    }
}
