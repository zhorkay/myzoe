package io.myzoe.site.repositories;

import io.myzoe.site.entities.Image;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VideoRepository extends PagingAndSortingRepository<Image, Long> {

}
