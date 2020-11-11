package hellobot.api.service;

import hellobot.api.domain.tarot.Tarot;
import hellobot.api.domain.tarot.TarotRepository;
import hellobot.api.dto.TarotDto;
import hellobot.api.global.error.ErrorCode;
import hellobot.api.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TarotService {

    private final TarotRepository tarotRepository;

    public String saveTarot(TarotDto tarotDto) {
        if(tarotRepository.findByScenarioIdAndTarotNum(tarotDto.getScenarioId(), tarotDto.getTarotNum()).isPresent()) {
            throw new GlobalException(ErrorCode.CONFLICT, "Requested tarot number and scenario already exists.");
        }
        Tarot tarot = tarotRepository.save(tarotDto.toEntity());
        return tarot.getId();
    }

    public List<TarotDto> findTarotList(Pageable pageable) {
        Page<TarotDto> tarotDtoPage = tarotRepository.findAll(pageable)
                .map(TarotDto::new);
        return new ArrayList<>(tarotDtoPage.getContent());
    }

    public TarotDto findTarotByNumber(String scenarioId, int number) {
        Tarot tarot = tarotRepository.findByScenarioIdAndTarotNum(scenarioId, number)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
        return new TarotDto(tarot);
    }
}
