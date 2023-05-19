package exercise.repository;

import exercise.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    Course findById(long id);

    @Query(value = "select * from courses where id in (:ids)", nativeQuery = true)
    Collection<Course> findAllByIds(Collection<Long> ids);

}
