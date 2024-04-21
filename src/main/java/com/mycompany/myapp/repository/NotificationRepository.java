package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByIdIn(List<Long> notificationIds);
}
