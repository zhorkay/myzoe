package io.myzoe.site.services;

import io.myzoe.site.entities.Video;
import io.myzoe.site.repositories.VideoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Inject
    VideoRepository videoRepository;

    @Override
    @Transactional
    public List<Video> getAllVideos()
    {
        Iterable<Video> iterable = this.videoRepository.findAll();
        List<Video> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    @Override
    @Transactional
    public Page<Video> getPagesVideos(@NotNull Pageable page)
    {
        Page<Video> videos = this.videoRepository.findAll(page);
        return videos;
    }

    @Override
    @Transactional
    public Video getVideo(long id) {
        return this.videoRepository.findOne(id);
    }

    @Override
    @Transactional
    public void create(Video video) {
        this.videoRepository.save(video);
    }

    @Override
    @Transactional
    public void update(Video video) {
        this.videoRepository.save(video);
    }

    @Override
    @Transactional
    public void delete(long id)
    {
        this.videoRepository.delete(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        this.videoRepository.deleteAll();
    }
    
}
