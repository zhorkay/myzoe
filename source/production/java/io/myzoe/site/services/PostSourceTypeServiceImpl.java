package io.myzoe.site.services;

import io.myzoe.site.entities.PostSourceType;
import io.myzoe.site.repositories.PostSourceTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostSourceTypeServiceImpl implements PostSourceTypeService
{
    @Inject
    PostSourceTypeRepository postSourceTypeRepository;

    @Override
    @Transactional
    public List<PostSourceType> getAllPostSourceTypes()
    {
        Iterable<PostSourceType> iterable = this.postSourceTypeRepository.findAll();
        List<PostSourceType> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    @Override
    @Transactional
    public PostSourceType getPostSourceType(String cd) {
        return this.postSourceTypeRepository.findOne(cd);
    }

    @Override
    @Transactional
    public void create(PostSourceType type) {
        this.postSourceTypeRepository.save(type);
    }

    @Override
    @Transactional
    public void update(PostSourceType type) {
        this.postSourceTypeRepository.save(type);
    }

    @Override
    @Transactional
    public void delete(String cd)
    {
        this.postSourceTypeRepository.delete(cd);
    }

    @Override
    @Transactional
    public void deleteAll() {
        this.postSourceTypeRepository.deleteAll();
    }
}
