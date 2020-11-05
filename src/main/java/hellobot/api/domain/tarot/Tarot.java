package hellobot.api.domain.tarot;

import hellobot.api.domain.scenario.Scenario;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Tarot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Scenario scenario;

    private Integer tarotNum;

    private String description;
}
