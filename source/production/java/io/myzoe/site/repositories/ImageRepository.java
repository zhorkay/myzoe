package io.myzoe.site.repositories;

import io.myzoe.site.entities.Image;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImageRepository extends PagingAndSortingRepository<Image, Long>
{

}
