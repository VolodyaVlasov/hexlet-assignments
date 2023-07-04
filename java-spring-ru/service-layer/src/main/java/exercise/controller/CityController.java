package exercise.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import exercise.model.City;
import exercise.model.CityWeatherResponseDto;
import exercise.model.NameTemperatureDto;
import exercise.repository.CityRepository;
import exercise.service.WeatherService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityRepository cityRepository;

    private final WeatherService weatherService;

    // BEGIN
    @GetMapping(path = "/cities/{id}")
    public CityWeatherResponseDto getWeatherForCityById(@PathVariable Long id) throws JsonProcessingException {
        return weatherService.getWeatherForCityById(id);
    }

    @GetMapping(path = "/search")
    public Collection<NameTemperatureDto> getWithFilter(@RequestParam(required = false) String  name){
        return weatherService.getWithFilter(name);
    }
    // END
}

