package exercise.repository;

import exercise.model.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    // BEGIN
    Iterable<Comment> findAllByPostId(Long postId);

    @Query(value = "SELECT * FROM comments WHERE post_id = :postId AND id = :commentId", nativeQuery = true)
    Optional<Comment> findByPostIdAndId(@Param("postId") Long postId,
                               @Param("commentId") Long commentId);
    // END
}
