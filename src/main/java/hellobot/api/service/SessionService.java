package hellobot.api.service;

import hellobot.api.domain.input.Input;
import hellobot.api.domain.input.InputRepository;
import hellobot.api.domain.input.InputType;
import hellobot.api.domain.message.Message;
import hellobot.api.domain.message.MessageRepository;
import hellobot.api.domain.scenario.Scenario;
import hellobot.api.domain.scenario.ScenarioRepository;
import hellobot.api.domain.session.Session;
import hellobot.api.domain.session.SessionRepository;
import hellobot.api.domain.user.User;
import hellobot.api.domain.user.UserRepository;
import hellobot.api.dto.*;
import hellobot.api.global.error.ErrorCode;
import hellobot.api.global.error.GlobalException;
import hellobot.api.global.util.MessageBuilder;
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
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final TarotService tarotService;
    private final MessageBuilder messageBuilder;

    @Transactional
    public NextMessageDto saveOrUpdateSession(SessionDto sessionDto) {
        Session session = sessionRepository.findByUserId(sessionDto.getUserId())
                .orElse(Session.builder().build());
        Scenario scenario = scenarioRepository.findById(sessionDto.getScenarioId())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
        User user = userRepository.findById(sessionDto.getUserId())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

        Map<String, String> variables = new HashMap<>();
        if(!scenario.getVariables().isEmpty()) {
            scenario.getVariables().forEach(variable -> variables.put(variable, ""));
        }
        variables.put("userName", user.getName());

        session.setUserId(sessionDto.getUserId());
        session.setScenarioId(sessionDto.getScenarioId());
        session.setVariables(variables);
        session.setMessageNumber(sessionDto.getMessageNumber());
        session = sessionRepository.save(session);

        Message message = messageRepository.findByScenarioIdAndNumber(scenario.getId(), sessionDto.getMessageNumber())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
        Input input = inputRepository.findByScenarioIdAndNumber(scenario.getId(), message.getNextInputNum())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

        String messageContent = messageBuilder.applyVariables(message.getContents(), variables);
        return buildNextMessage(messageContent, message, session, input);
    }

    @Transactional
    public NextMessageDto findNextMessage(String scenarioId, String userId, InputType inputType, Map<String, String> contentMap, int nextMessageNum) {
        Session session = sessionRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND, "Session not found."));
        Message message = messageRepository.findByScenarioIdAndNumber(scenarioId, nextMessageNum)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
        Input input = inputRepository.findByScenarioIdAndNumber(scenarioId, message.getNextInputNum())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

        String messageContent = message.getContents();
        Map<String, String> variables = session.getVariables();
        if(inputType.equals(InputType.TAROT)) {
            TarotDto tarotDto = tarotService.findTarotByNumber(scenarioId, Integer.parseInt(contentMap.get("tarot")));
            messageContent = messageContent.replaceAll("\\$\\{tarot}", tarotDto.getDescription());
        } else if(inputType.equals(InputType.TEXT)) {
            variables.putAll(contentMap);
        }

        messageContent = messageBuilder.applyVariables(messageContent, variables);

        session.setMessageNumber(message.getNumber());
        session.setVariables(variables);
        session = sessionRepository.save(session);

        return buildNextMessage(messageContent, message, session, input);
    }

    private NextMessageDto buildNextMessage(String messageContent, Message message, Session session, Input input) {
        List<String> imageUrlList = new ArrayList<>();
        if(message.getImageList() != null) {
            imageUrlList = imageService.findImageListById(message.getImageList()).stream()
                    .map(ImageDto::getImageUrl)
                    .collect(Collectors.toList());
        }

        if(message.getNextInputNum() == 0) {
            sessionRepository.deleteById(session.getId());
            return NextMessageDto.builder()
                    .messageContent(messageContent)
                    .imageUrlList(imageUrlList)
                    .inputType(InputType.END)
                    .build();
        }

        return NextMessageDto.builder()
                .messageContent(messageContent)
                .imageUrlList(imageUrlList)
                .inputType(input.getInputType())
                .inputContents(input.getContents())
                .nextMessageNums(input.getNextMessageNums())
                .build();
    }

    public SessionDto findSessionByUserId(String userId) {
        Session session = sessionRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND, "Session not found."));
        return new SessionDto(session);
    }

    public void deleteSession(String sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
