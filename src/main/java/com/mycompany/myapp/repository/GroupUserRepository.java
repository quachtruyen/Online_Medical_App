package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
    GroupUser findByIdGroupUser(Long idGroupUser);
}
