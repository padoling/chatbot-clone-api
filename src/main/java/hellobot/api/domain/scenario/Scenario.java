package hellobot.api.domain.scenario;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@NoArgsConstructor
@Document("scenario")
public class Scenario {

    @Id
    private String id;

    private String name;

    private List<String> variables;

    private String description;

    @Builder
    public Scenario(String id, String name, List<String> variables, String description) {
        this.id = id;
        this.name = name;
        this.variables = variables;
        this.description = description;
    }
}
