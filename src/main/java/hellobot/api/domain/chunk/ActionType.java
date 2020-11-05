package hellobot.api.domain.chunk;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionType {

    TEXT("TYPE_TEXT"),
    OPTION("TYPE_OPTION"),
    EXIT("TYPE_EXIT");

    private final String actionType;
}
