package hellobot.api.domain.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@NoArgsConstructor
@Document("input")
public class Input {

    @Id
    private String id;

    private InputType inputType;

    private List<String> contents;

    private Integer number;

    private List<Integer> nextMessageNums;

    private String scenarioId;
}
