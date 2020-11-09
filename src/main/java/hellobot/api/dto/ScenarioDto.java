package hellobot.api.dto;

import hellobot.api.domain.scenario.Scenario;
import lombok.Getter;

import java.util.List;

@Getter
public class ScenarioDto {

    private String id;
    private String name;
    private List<String> variables;
    private String description;

    public ScenarioDto(Scenario document) {
        this.id = document.getId();
        this.name = document.getName();
        this.variables = document.getVariables();
        this.description = document.getDescription();
    }
}
