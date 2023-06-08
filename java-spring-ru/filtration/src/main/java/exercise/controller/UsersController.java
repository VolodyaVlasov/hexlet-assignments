package exercise.controller;

import exercise.model.User;
import exercise.model.UserParams;
import exercise.repository.UserRepository;
import exercise.service.SearchCriteria;
import exercise.service.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

// Зависимости для самостоятельной работы
// import org.springframework.data.querydsl.binding.QuerydslPredicate;
// import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserRepository userRepository;

    // BEGIN
    @GetMapping
    public Collection<User> getByFilter(UserParams userParams) {
        List<Specification<User>> specifications = new ArrayList<>();

        if (nonNull(userParams.getFirstName()))
            specifications.add(new UserSpecification(new SearchCriteria<>("firstName", userParams.getFirstName())));

        if (nonNull(userParams.getLastName()))
            specifications.add(new UserSpecification(new SearchCriteria<>("lastName", userParams.getLastName())));

        Specification<User> specification = specifications.stream()
                .reduce(null, (result, spec) -> {
                    if (isNull(result)) return spec;
                    return result.and(spec);
                });

        if (isNull(specification)) {
            return userRepository.findAll();
        }

        return userRepository.findAll(specification);
    }
    // END
}

