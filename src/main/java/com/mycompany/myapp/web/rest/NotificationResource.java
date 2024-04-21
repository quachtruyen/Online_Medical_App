package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.NotificationService;
import com.mycompany.myapp.service.dto.NotificationDTO;
import com.mycompany.myapp.service.dto.UserNotificationDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
public class NotificationResource {

    private final NotificationService notificationService;

    public NotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("")
    public ResponseEntity<List<NotificationDTO>> getNotificationForCurrentUser(
        Pageable pageable,
        @RequestParam String status,
        @RequestParam String search
    ) {
        List<NotificationDTO> list = notificationService.getListNotification(pageable, search, status);
        int total = notificationService.countAllNotification(status, search);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Total-Count", Integer.toString(total));
        return new ResponseEntity<>(list, headers, HttpStatus.OK);
    }

    @GetMapping("/totalUnread")
    public ResponseEntity<Integer> getTotalUnread() {
        Integer result = notificationService.countTotalUnreadByCurrentUser();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/readAll")
    public ResponseEntity<Void> readAll() {
        notificationService.readAllNotifications();
        return ResponseEntity.ok(null);
    }

    @PutMapping("/readOne")
    public ResponseEntity<Void> readOne(@RequestParam Long id) {
        notificationService.readOne(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/test")
    public void createNoti() {
        UserNotificationDTO userNotification = new UserNotificationDTO();
        userNotification.setNotificationId(3L);
        userNotification.setUserId(9L);
    }
}
