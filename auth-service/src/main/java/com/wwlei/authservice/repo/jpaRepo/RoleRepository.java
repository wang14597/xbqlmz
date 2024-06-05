package com.wwlei.authservice.repo.jpaRepo;

import com.wwlei.authservice.repo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
