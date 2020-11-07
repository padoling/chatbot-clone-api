package hellobot.api.domain.tarot;

import hellobot.api.domain.scenario.Scenario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document("tarot")
public class Tarot {

    @Id
    private String id;
    
    private Scenario scenario;

    private Integer tarotNum;

    private String description;
}
