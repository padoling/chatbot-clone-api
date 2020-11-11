package hellobot.api.controller;

import hellobot.api.dto.TarotDto;
import hellobot.api.service.TarotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tarot")
public class TarotController {

    private final TarotService tarotService;

    @PostMapping
    public ResponseEntity postTarot(@RequestBody TarotDto tarotDto) {
        Map<String, String> response = new HashMap<>();
        response.put("tarotId", tarotService.saveTarot(tarotDto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getTarotList(@PageableDefault(sort = {"id"}) Pageable pageable) {
        return new ResponseEntity<>(tarotService.findTarotList(pageable), HttpStatus.OK);
    }
}
