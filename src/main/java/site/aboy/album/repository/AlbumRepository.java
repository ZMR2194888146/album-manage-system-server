package site.aboy.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.aboy.album.entity.Album;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findAlbumsByUid(Long uid);
}
