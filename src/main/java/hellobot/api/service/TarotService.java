package hellobot.api.service;

import hellobot.api.dto.TarotDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TarotService {

    public String saveTarot() {
        //TODO
        return null;
    }

    public List<TarotDto> findTarotList() {
        //TODO
        return null;
    }

    public TarotDto findTarotByNumber(String scenarioId, int number) {
        //TODO
        return null;
    }
}
