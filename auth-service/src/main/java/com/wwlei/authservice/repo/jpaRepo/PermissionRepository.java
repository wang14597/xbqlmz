package com.wwlei.authservice.repo.jpaRepo;

import com.wwlei.authservice.repo.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
