package hellobot.api.dto;

import hellobot.api.domain.chunk.Chunk;
import lombok.Getter;

import java.util.List;

@Getter
public class ScenarioPostDto {

    private String name;
    private List<String> variables;
    private List<Chunk> chunkList;

}
