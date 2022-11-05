package com.choks.redditclone.service;

import com.choks.redditclone.dto.SubredditDto;
import com.choks.redditclone.exceptions.SpringRedditException;
import com.choks.redditclone.mapper.SubredditMapper;
import com.choks.redditclone.model.Subreddit;
import com.choks.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
       return subredditRepository.findAll()
               .stream()
               .map(subredditMapper::mapSubredditToDto)
               .collect(Collectors.toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));

        return  subredditMapper.mapSubredditToDto(subreddit);
    }
}
