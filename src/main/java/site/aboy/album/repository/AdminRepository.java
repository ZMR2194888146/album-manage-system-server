package site.aboy.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.aboy.album.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findAdminByUsername(String username);
}
