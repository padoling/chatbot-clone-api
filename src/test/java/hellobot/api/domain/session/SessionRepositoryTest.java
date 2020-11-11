package hellobot.api.domain.session;

import hellobot.api.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionRepositoryTest extends RepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    public void updateSession_success() {
        // given
        String scenarioId = "scenarioId";
        String updatedScenarioId = "updatedId";
        Session session = Session.builder()
                .userId("userId")
                .scenarioId(scenarioId)
                .messageNumber(1)
                .build();

        // when
        session = sessionRepository.save(session);
        assertThat(session.getScenarioId()).isEqualTo(scenarioId);
        String previousSessionId = session.getId();

        session.setScenarioId(updatedScenarioId);
        session = sessionRepository.save(session);

        // then
        assertThat(session.getId()).isEqualTo(previousSessionId);
        assertThat(session.getScenarioId()).isEqualTo(updatedScenarioId);
    }

}
