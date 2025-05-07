package com.second.guestbook.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GuestEntry {
    private Long id;
    private String name;
    private String message;
    private String ipAddress;
    private LocalDateTime createdAt;
}