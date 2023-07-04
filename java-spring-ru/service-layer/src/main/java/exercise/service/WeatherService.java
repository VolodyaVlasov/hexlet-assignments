package exercise.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.CityNotFoundException;
import exercise.HttpClient;
import exercise.model.City;
import exercise.model.CityWeatherResponseDto;
import exercise.model.NameTemperatureDto;
import exercise.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;


@Service
public class WeatherService {

    private static final String HTTP = "http://weather/api/v2/cities/";

    @Autowired
    CityRepository cityRepository;

    @Autowired
    ObjectMapper objectMapper;

    // Клиент
    HttpClient client;

    // При создании класса сервиса клиент передаётся снаружи
    // В теории это позволит заменить клиент без изменения самого сервиса
    WeatherService(HttpClient client) {
        this.client = client;
    }

    public CityWeatherResponseDto getWeatherForCityById(Long id) throws JsonProcessingException {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("city isnt exist"));

        String name = city.getName();
        String response = client.get(HTTP + name);
        return objectMapper.readValue(response, CityWeatherResponseDto.class);

    }

    public Collection<NameTemperatureDto> getWithFilter(String name) {
        Collection<City> cityDtos;

        if(isNull(name)){
            cityDtos = cityRepository.findAll(Sort.by("name"));
        } else {
            cityDtos = cityRepository.findAllByNameContainingIgnoreCase(name);
        }

        return cityDtos.stream().map(city -> {
            try {
                return nameTemperatureDto(city.getName());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private NameTemperatureDto nameTemperatureDto(String name) throws JsonProcessingException {
        String response = client.get(HTTP + name);
        return objectMapper.readValue(response, NameTemperatureDto.class);
    }

    // BEGIN

    // END
}
