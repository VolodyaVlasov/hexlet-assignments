package exercise;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

// BEGIN
@Service
// END
public class Meal {
    public String getMealForDaytime(String daytime) {

        switch (daytime) {
            case "morning":
                return "breakfast";
            case "day":
                return "lunch";
            case "evening":
                return "dinner";
            default:
                return "nothing :)";
        }
    }

    // Для самостоятельной работы
    // BEGIN
    @PostConstruct
    public void init(){
        System.out.println("PostConstruct from meal");
    }
    // END
}
