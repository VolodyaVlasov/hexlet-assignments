package exercise;

import exercise.daytimes.Daytime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
@RequestMapping("/daytime")
@RequiredArgsConstructor
public class WelcomeController {

    private final Daytime daytime;

    private final Meal meal;

    @GetMapping
    public String getBonAppetite() {
        String mealForDaytime = meal.getMealForDaytime(daytime.getName());
        System.out.println(mealForDaytime);
        return daytime.getName();
    }
}
// END
