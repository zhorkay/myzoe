package io.myzoe.site.repositories;

import io.myzoe.site.dto.PostDto;
import io.myzoe.site.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long>
{
    @Query( "select p from PostDto p" )
    Page<PostDto> findAllCustom( Pageable pageable );
}
