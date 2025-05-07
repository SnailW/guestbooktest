package com.second.guestbook.controller;

import com.second.guestbook.model.GuestEntry;
import com.second.guestbook.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GuestbookController {

    private final GuestbookService guestbookService;

    @Autowired
    public GuestbookController(GuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    @GetMapping("/entries")
    public List<GuestEntry> getAllEntries() {
        return guestbookService.getAllEntries();
    }

    @GetMapping("/entries/{id}")
    public ResponseEntity<GuestEntry> getEntryById(@PathVariable Long id) {
        GuestEntry entry = guestbookService.getEntryById(id);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entry);
    }

    @PostMapping("/entries")
    public GuestEntry createEntry(@RequestBody GuestEntry entry,
                                  @RequestHeader(value = "X-Forwarded-For", required = false) String forwardedIp,
                                  @RequestHeader(value = "X-Real-IP", required = false) String realIp,
                                  @RequestHeader(value = "Remote-Addr", required = false) String remoteAddr) {
        // IP 결정 로직 (방화벽 테스트용)
        String clientIp = forwardedIp != null ? forwardedIp :
                (realIp != null ? realIp : remoteAddr);

        return guestbookService.addEntry(entry, clientIp);
    }

    @PutMapping("/entries/{id}")
    public ResponseEntity<GuestEntry> updateEntry(@PathVariable Long id, @RequestBody GuestEntry entry) {
        GuestEntry existingEntry = guestbookService.getEntryById(id);
        if (existingEntry == null) {
            return ResponseEntity.notFound().build();
        }

        entry.setId(id);
        GuestEntry updatedEntry = guestbookService.updateEntry(entry);
        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("/entries/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        GuestEntry existingEntry = guestbookService.getEntryById(id);
        if (existingEntry == null) {
            return ResponseEntity.notFound().build();
        }

        guestbookService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/network-info")
    public Map<String, String> getNetworkInfo(@RequestHeader Map<String, String> headers) {
        Map<String, String> networkInfo = new HashMap<>();

        // 클라이언트 IP 결정
        String clientIp = headers.getOrDefault("x-forwarded-for",
                headers.getOrDefault("x-real-ip",
                        headers.getOrDefault("remote-addr", "unknown")));

        networkInfo.put("clientIp", clientIp);
        networkInfo.put("userAgent", headers.getOrDefault("user-agent", "unknown"));

        return networkInfo;
    }
}