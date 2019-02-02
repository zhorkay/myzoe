package io.myzoe.site.services;

import io.myzoe.site.entities.Post;
import io.myzoe.site.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService
{
    @Inject PostRepository postRepository;

    @Override
    @Transactional
    public List<Post> getAllPosts()
    {
        Iterable<Post> iterable = this.postRepository.findAll();
        List<Post> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    @Override
    @Transactional
    public Page<Post> getPagesPosts(@NotNull Pageable page)
    {
        Page<Post> posts = this.postRepository.findAll(page);
        return posts;
    }

    @Override
    @Transactional
    public Post getPost(long id) {
        return this.postRepository.findOne(id);
    }

    @Override
    @Transactional
    public void create(Post post) {
        this.postRepository.save(post);
    }

    @Override
    @Transactional
    public void update(Post post) {
        this.postRepository.save(post);
    }

    @Override
    @Transactional
    public void delete(long id)
    {
        this.postRepository.delete(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        this.postRepository.deleteAll();
    }
}
