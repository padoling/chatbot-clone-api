package hellobot.api.domain.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document("message")
public class Message {

    @Id
    private String id;

    private String contents;

    private Integer number;

    private Integer nextInputNum;

    private String scenarioId;
}
