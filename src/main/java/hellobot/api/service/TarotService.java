package hellobot.api.service;

import hellobot.api.domain.tarot.TarotRepository;
import hellobot.api.dto.TarotDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TarotService {

    private final TarotRepository tarotRepository;

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
