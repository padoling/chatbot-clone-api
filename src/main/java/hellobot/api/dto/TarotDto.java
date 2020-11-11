package hellobot.api.dto;

import hellobot.api.domain.tarot.Tarot;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TarotDto {

    private String id;
    private String scenarioId;
    private int tarotNum;
    private String description;

    @Builder
    public TarotDto(String id, String scenarioId, int tarotNum, String description) {
        this.id = id;
        this.scenarioId = scenarioId;
        this.tarotNum = tarotNum;
        this.description = description;
    }

    public TarotDto(Tarot document) {
        this.id = document.getId();
        this.scenarioId = document.getScenarioId();
        this.tarotNum = document.getTarotNum();
        this.description = document.getDescription();
    }

    public Tarot toEntity() {
        return Tarot.builder()
                .id(id)
                .scenarioId(scenarioId)
                .tarotNum(tarotNum)
                .description(description)
                .build();
    }

}
