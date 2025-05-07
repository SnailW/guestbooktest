package com.second.guestbook.service;

import com.second.guestbook.model.GuestEntry;
import java.util.List;

public interface GuestbookService {
    List<GuestEntry> getAllEntries();
    GuestEntry getEntryById(Long id);
    GuestEntry addEntry(GuestEntry entry, String clientIp);
    GuestEntry updateEntry(GuestEntry entry);
    void deleteEntry(Long id);
}