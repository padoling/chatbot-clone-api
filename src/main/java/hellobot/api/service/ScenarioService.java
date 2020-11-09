package hellobot.api.service;

import hellobot.api.domain.input.Input;
import hellobot.api.domain.input.InputRepository;
import hellobot.api.domain.message.Message;
import hellobot.api.domain.message.MessageRepository;
import hellobot.api.domain.scenario.Scenario;
import hellobot.api.domain.scenario.ScenarioRepository;
import hellobot.api.domain.session.Session;
import hellobot.api.domain.session.SessionRepository;
import hellobot.api.dto.*;
import hellobot.api.global.error.ErrorCode;
import hellobot.api.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final MessageRepository messageRepository;
    private final InputRepository inputRepository;
    private final SessionRepository sessionRepository;
    private final ImageService imageService;

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

    @Transactional
    public NextMessageDto findNextMessage(String scenarioId, String userId, Map<String, String> content, int nextMessageNum) {
        Session session = sessionRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND, "Session not found."));
        if(session.getScenarioId().equals(scenarioId)) {
            Message message = messageRepository.findByScenarioIdAndNumber(scenarioId, nextMessageNum)
                    .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
            Input input = inputRepository.findByScenarioIdAndNumber(scenarioId, message.getNextInputNum())
                    .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
            String messageContent = message.getContents();
            Map<String, String> variables = session.getVariables();

            if(content.get("tarotNum") != null) {
                // get tarot message and make messages
            } else {
                // make variables and messages
            }
            List<String> imageUrlList = imageService.findImageListById(message.getImageList()).stream()
                    .map(ImageDto::getImageUrl)
                    .collect(Collectors.toList());

            // update session
            SessionDto sessionDto = new SessionDto(session);
            sessionDto.setMessageNumber(message.getNumber());
            sessionRepository.save(sessionDto.toEntity());

            return NextMessageDto.builder()
                    .messageContent(messageContent)
                    .imageUrlList(imageUrlList)
                    .inputType(input.getInputType())
                    .inputContents(input.getContents())
                    .nextMessageNums(input.getNextMessageNums())
                    .build();
        }
        return null;
    }

}