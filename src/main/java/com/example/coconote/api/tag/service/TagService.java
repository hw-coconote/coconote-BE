package com.example.coconote.api.tag.service;

import com.example.coconote.api.channel.entity.Channel;
import com.example.coconote.api.channel.repository.ChannelRepository;
import com.example.coconote.api.tag.dto.request.TagCreateReqDto;
import com.example.coconote.api.tag.dto.response.TagListResDto;
import com.example.coconote.api.tag.entity.Tag;
import com.example.coconote.api.tag.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final ChannelRepository channelRepository;

    public Tag createTag(TagCreateReqDto dto) {
        Channel channel = channelRepository.findById(dto.getChannelId()).orElseThrow(()->new EntityNotFoundException("Channel not found"));
        Tag tag = tagRepository.save(dto.toEntity(channel));
        return tag;
    }

    public List<TagListResDto> tagList(Long channelId) {
        Channel channel = channelRepository.findById(channelId).orElseThrow(()->new EntityNotFoundException("Channel not found"));
        List<Tag> tags = tagRepository.findAllByChannel(channel);
        List<TagListResDto> tagListResDtos = tags.stream().map(tag -> tag.fromEntity()).toList();
        return tagListResDtos;
    }
}
