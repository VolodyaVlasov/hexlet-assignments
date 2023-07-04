package exercise.repository;

import exercise.model.City;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {

    // BEGIN
    Collection<City> findAllByNameContainingIgnoreCase(String name);

    Collection<City> findAll(Sort sort);
    // END
}
