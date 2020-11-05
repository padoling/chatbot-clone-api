package hellobot.api.domain.chunk;

import hellobot.api.domain.scenario.Scenario;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Chunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Scenario scenario;

    private Integer order;

    private String message;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private String actionContent;

    private Integer nextChunk;
}
