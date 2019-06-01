package site.aboy.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.aboy.album.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByPid(Long pid);
    List<Long> findPidByHaveReadIsFalse();
}
