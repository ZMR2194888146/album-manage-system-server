package site.aboy.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.aboy.album.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByNickName(String nickName);
    User findUserByAliUserId(String aliUserId);
    User findUserBySid(String sid);
    List<User> findUsersByActiveIsTrue();
}
