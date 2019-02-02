package io.myzoe.site.services;

import io.myzoe.site.entities.Image;
import io.myzoe.site.repositories.ImageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService
{
    @Inject
    ImageRepository imageRepository;

    @Override
    @Transactional
    public List<Image> getAllImages()
    {
        Iterable<Image> iterable = this.imageRepository.findAll();
        List<Image> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    @Override
    @Transactional
    public Page<Image> getPagesImages(@NotNull Pageable page)
    {
        Page<Image> images = this.imageRepository.findAll(page);
        return images;
    }

    @Override
    @Transactional
    public Image getImage(long id) {
        return this.imageRepository.findOne(id);
    }

    @Override
    @Transactional
    public void create(Image image) {
        this.imageRepository.save(image);
    }

    @Override
    @Transactional
    public void update(Image image) {
        this.imageRepository.save(image);
    }

    @Override
    @Transactional
    public void delete(long id)
    {
        this.imageRepository.delete(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        this.imageRepository.deleteAll();
    }
}
