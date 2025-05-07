package com.second.guestbook.mapper;

import com.second.guestbook.model.GuestEntry;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface GuestbookMapper {
    List<GuestEntry> findAll();
    GuestEntry findById(Long id);
    void insert(GuestEntry entry);
    void update(GuestEntry entry);
    void delete(Long id);
}