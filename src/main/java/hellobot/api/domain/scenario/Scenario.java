package hellobot.api.domain.scenario;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document("scenario")
public class Scenario {

    @Id
    private String id;

    private String name;

    private String variables;

    private String descMessage;

    @Builder
    public Scenario(String id, String name, String variables, String descMessage) {
        this.id = id;
        this.name = name;
        this.variables = variables;
        this.descMessage = descMessage;
    }
}
