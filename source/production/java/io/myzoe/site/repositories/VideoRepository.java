package io.myzoe.site.repositories;

import io.myzoe.site.entities.Video;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VideoRepository extends PagingAndSortingRepository<Video, Long> {

}
