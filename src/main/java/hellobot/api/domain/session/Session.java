package hellobot.api.domain.session;

import hellobot.api.domain.scenario.Scenario;
import hellobot.api.domain.user.User;

import javax.persistence.*;

@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Scenario scenario;
}
