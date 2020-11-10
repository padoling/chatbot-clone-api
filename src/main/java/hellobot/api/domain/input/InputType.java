package hellobot.api.domain.input;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InputType {

    TEXT("TYPE_TEXT"),
    OPTION("TYPE_OPTION"),
    TAROT("TYPE_TAROT"),
    END("TYPE_END");

    private final String inputType;
}
