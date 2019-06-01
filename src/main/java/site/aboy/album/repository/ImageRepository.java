package site.aboy.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.aboy.album.entity.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findTopByAid(Long aid);
    List<Image> findByUid(Long uid);
    List<Image> findByAid(Long aid);
    List<Image> findAllByUidAndSharedIsTrue(Long uid);
}
