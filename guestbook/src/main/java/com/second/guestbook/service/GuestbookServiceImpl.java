package com.second.guestbook.service;

import com.second.guestbook.mapper.GuestbookMapper;
import com.second.guestbook.model.GuestEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookMapper guestbookMapper;

    @Autowired
    public GuestbookServiceImpl(GuestbookMapper guestbookMapper) {
        this.guestbookMapper = guestbookMapper;
    }

    @Override
    public List<GuestEntry> getAllEntries() {
        return guestbookMapper.findAll();
    }

    @Override
    public GuestEntry getEntryById(Long id) {
        return guestbookMapper.findById(id);
    }

    @Override
    public GuestEntry addEntry(GuestEntry entry, String clientIp) {
        entry.setIpAddress(clientIp);
        guestbookMapper.insert(entry);
        return entry;
    }

    @Override
    public GuestEntry updateEntry(GuestEntry entry) {
        guestbookMapper.update(entry);
        return entry;
    }

    @Override
    public void deleteEntry(Long id) {
        guestbookMapper.delete(id);
    }
}