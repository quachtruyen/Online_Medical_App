package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.Notification;
import java.time.Instant;
import lombok.Data;

@Data
public class NotificationDTO {

    private Long id;
    private Long userId;

    private Instant createdAt = Instant.now();

    private String title;

    private String content;

    private String status;

    private String userNotificationId;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public NotificationDTO() {}

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.content = notification.getContent();
        this.title = notification.getTitle();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserNotificationId() {
        return userNotificationId;
    }

    public void setUserNotificationId(String userNotificationId) {
        this.userNotificationId = userNotificationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
