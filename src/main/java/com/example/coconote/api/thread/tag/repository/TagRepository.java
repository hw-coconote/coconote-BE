package com.example.coconote.api.thread.tag.repository;

import com.example.coconote.api.channel.channel.entity.Channel;
import com.example.coconote.api.thread.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByChannel(Channel channel);
}