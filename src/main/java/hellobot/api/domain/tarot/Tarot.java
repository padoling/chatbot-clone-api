package hellobot.api.domain.tarot;

import lombok.Builder;
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
    
    private String scenarioId;

    private Integer tarotNum;

    private String description;

    @Builder
    public Tarot(String id, String scenarioId, Integer tarotNum, String description) {
        this.id = id;
        this.scenarioId = scenarioId;
        this.tarotNum = tarotNum;
        this.description = description;
    }
}
