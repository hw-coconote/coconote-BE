package com.example.coconote.api.tag.repository;

import com.example.coconote.api.channel.entity.Channel;
import com.example.coconote.api.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByChannel(Channel channel);
}