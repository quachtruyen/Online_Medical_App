package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserNotification;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    Page<UserNotification> findAllByUserIdOrderByCreatedAtDesc(Long userNotificationId, Pageable pageable);

    Integer countAllByUserIdAndStatus(Long userId, String status);

    List<UserNotification> findAllByUserIdAndStatus(Long userId, String status);

    UserNotification findOneByUserIdAndNotificationId(Long userId, Long notificationId);
}
