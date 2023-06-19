package exercise.controller;

import exercise.ResourceNotFoundException;
import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    // BEGIN
    @GetMapping("/{postId}/comments")
    public Iterable<Comment> getAllByPostId(@PathVariable Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public Comment getByPostIdAndId(@PathVariable Long postId, @PathVariable Long commentId) {
        return commentRepository.findByPostIdAndId(postId, commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not fount"));
    }

    @PostMapping("/{postId}/comments")
    public Comment create(@PathVariable Long postId, @RequestBody Comment comment) {
        return postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("Post inst exist"));
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public Comment update(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody Comment comment) {
        return commentRepository.findByPostIdAndId(postId, commentId).map(commentFromDb -> {
            commentFromDb.setContent(comment.getContent());
            return commentRepository.save(commentFromDb);
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not fount"));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public void delete(@PathVariable Long postId, @PathVariable Long commentId) {
        commentRepository.findByPostIdAndId(postId, commentId)
                .map(comment -> {
                    commentRepository.deleteById(commentId);
                    return comment;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Comment not fount"));
    }


    // END
}
