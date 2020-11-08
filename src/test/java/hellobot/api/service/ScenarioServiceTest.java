package hellobot.api.service;

import hellobot.api.domain.scenario.Scenario;
import hellobot.api.domain.scenario.ScenarioRepository;
import hellobot.api.dto.InputDto;
import hellobot.api.dto.MessageDto;
import hellobot.api.dto.ScenarioPostRequestDto;
import hellobot.api.global.error.GlobalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ScenarioServiceTest {

    @InjectMocks
    private ScenarioService scenarioService;

    @Mock
    private ScenarioRepository scenarioRepository;

    private Scenario scenario;

    @BeforeEach
    public void setUp() {
        scenario = Scenario.builder()
                .id("id12345678")
                .name("썸 연애운")
                .descMessage("내꺼인 듯 내꺼 아닌 내꺼 같은 그분\n과연 사귀게 될 건지\n사귀면 언제 사귈 건지\n{$imageId1}\n타로로 점 쳐볼까?")
                .build();
    }

    @Test
    public void saveScenario_success() {
        // given
        String name = "썸 연애운";
        ScenarioPostRequestDto dto = ScenarioPostRequestDto.builder()
                .name(name)
                .messageDtoList(new ArrayList<MessageDto>())
                .inputDtoList(new ArrayList<InputDto>())
                .build();

        given(scenarioRepository.findByName(name)).willReturn(null);
        given(scenarioRepository.save(any())).willReturn(scenario);

        // when
        String scenarioId = scenarioService.saveScenario(dto);

        // then
        assertThat(scenarioId).isEqualTo(scenario.getId());
        assertThat(scenario.getName()).isEqualTo(dto.getName());
    }

    @Test
    public void saveScenario_fail() {
        // given
        String name = "썸 연애운";
        ScenarioPostRequestDto dto = ScenarioPostRequestDto.builder()
                .name(name)
                .messageDtoList(new ArrayList<MessageDto>())
                .inputDtoList(new ArrayList<InputDto>())
                .build();

        given(scenarioRepository.findByName(name)).willReturn(scenario);

        // when
        assertThrows(GlobalException.class, () -> {
            scenarioService.saveScenario(dto);
        });
    }

    @Test
    public void getDescMessage_success() {
        // given
        given(scenarioRepository.findById(any())).willReturn(Optional.of(scenario));

        // when
        String descMessage = scenarioService.getDescMessage("id12345678");

        // then
        assertThat(descMessage)
                .isNotNull()
                .isEqualTo(scenario.getDescMessage());
    }
}
