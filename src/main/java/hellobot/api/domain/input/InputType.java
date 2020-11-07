package hellobot.api.domain.input;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InputType {

    TEXT("TYPE_TEXT"),
    OPTION("TYPE_OPTION");

    private final String inputType;
}
