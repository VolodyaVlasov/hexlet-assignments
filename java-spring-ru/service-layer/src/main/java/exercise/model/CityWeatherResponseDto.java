package exercise.model;

public record CityWeatherResponseDto(String name,
                                     String temperature,
                                     String cloudy,
                                     String humidity,
                                     String wind) {

}
