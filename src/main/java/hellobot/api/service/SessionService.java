package hellobot.api.service;

import hellobot.api.domain.input.Input;
import hellobot.api.domain.input.InputRepository;
import hellobot.api.domain.message.Message;
import hellobot.api.domain.message.MessageRepository;
import hellobot.api.domain.scenario.Scenario;
import hellobot.api.domain.scenario.ScenarioRepository;
import hellobot.api.domain.session.Session;
import hellobot.api.domain.session.SessionRepository;
import hellobot.api.domain.user.User;
import hellobot.api.domain.user.UserRepository;
import hellobot.api.dto.ImageDto;
import hellobot.api.dto.NextMessageDto;
import hellobot.api.dto.SessionDto;
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
    private final MessageBuilder messageBuilder;

    // sessionId가 같이 오면 update, 아니면 새로 save
    @Transactional
    public NextMessageDto saveOrUpdateSession(SessionDto sessionDto) {
        Scenario scenario = scenarioRepository.findById(sessionDto.getScenarioId())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
        User user = userRepository.findById(sessionDto.getUserId())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
        Map<String, String> variables = new HashMap<>();
        variables.put("userName", user.getName());
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

        String messageContent = messageBuilder.applyVariables(message.getContents(), variables);

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
