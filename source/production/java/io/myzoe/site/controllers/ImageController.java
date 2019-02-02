package io.myzoe.site.controllers;

import io.myzoe.config.annotation.RestEndpoint;
import io.myzoe.site.entities.Image;
import io.myzoe.site.services.ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.util.List;

@RestEndpoint
public class ImageController
{
    private static final Logger Log = LogManager.getLogger();

    @Inject
    ImageService imageService;

    //-------------------Retrieve All Images---------------------------------------------------------

    @RequestMapping(value = "/images/", method = RequestMethod.GET)
    public ResponseEntity<List<Image>> listAllImages()
    {
        List<Image> images = this.imageService.getAllImages();
        if (images.isEmpty())
        {
            return new ResponseEntity<List<Image>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Image>>(images, HttpStatus.OK);
    }

    //-------------------Retrieve All Images per Pages-----------------------------------------------

    @RequestMapping(value = "/image/", method = RequestMethod.GET)
    public ResponseEntity<Page<Image>> pagingImages(@RequestParam int pageindex,
                                                  @RequestParam int pagesize)
    {
        Pageable page = new PageRequest(pageindex, pagesize);
        Page<Image> images = this.imageService.getPagesImages(page);
        if (images.getSize() == 0)
        {
            Log.debug("Image Not Found");
            return new ResponseEntity<Page<Image>>(HttpStatus.NO_CONTENT);
        }
        Log.debug("Image found.");
        return new ResponseEntity<Page<Image>>(images, HttpStatus.OK);
    }

    //-------------------Retrieve Single Image--------------------------------------------------------

    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
    public ResponseEntity<Image> getImage(@PathVariable("id") long id) {
        Log.debug("Fetching Image with id " + id);
        Image image = this.imageService.getImage(id);
        if (image == null) {
            Log.debug("Image with id " + id + " not found");
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Image>(image, HttpStatus.OK);
    }

    //-------------------Create a Image-----------------------------------------------------------------

    @RequestMapping(value = "/image/", method = RequestMethod.POST)
    public ResponseEntity<Void> createImage(@RequestBody Image image, UriComponentsBuilder ucBuilder)
    {
        if (this.imageService.getImage(image.getId()) != null) {
            Log.debug("A Image with name " + image.getPostName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        this.imageService.create(image);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/image/{id}").buildAndExpand(image.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //-------------------Update a Image-----------------------------------------------------------------

    @RequestMapping(value = "/image/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Image> updateImage(@PathVariable("id") long id,
                                           @RequestBody Image image)
    {
        Image currentImage = this.imageService.getImage(id);

        if (currentImage == null)
        {
            Log.debug("[MYZOE]: Image does not exist: {}.", id);
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND);
        }

        currentImage.setUserId(image.getUserId());
        currentImage.setPostName(image.getPostName());
        currentImage.setPostDesc(image.getPostDesc());
        currentImage.setPostSourceText(image.getPostSourceText());
        currentImage.setPostSourceType(image.getPostSourceType());
        currentImage.setPostEndDate(image.getPostEndDate());
        currentImage.setPostStartDate(image.getPostStartDate());
        currentImage.setPostRankValue(image.getPostRankValue());
        currentImage.setImageWidthValue(image.getImageWidthValue());
        currentImage.setImageHeightValue(image.getImageHeightValue());
        currentImage.setImageThumbSourceText(image.getImageThumbSourceText());

        this.imageService.update(currentImage);
        Log.debug("[MYZOE]: Image updated: {}.", id);
        return new ResponseEntity<Image>(currentImage, HttpStatus.OK);
    }

    //------------------- Delete a Image --------------------------------------------------------

    @RequestMapping(value = "/image/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Image> deleteImage(@PathVariable("id") long id) {
        Log.debug("Fetching & Deleting Image with id " + id);

        Image image = this.imageService.getImage(id);
        if (image == null) {
            Log.debug("Unable to delete. Image with id " + id + " not found");
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND);
        }

        this.imageService.delete(id);
        return new ResponseEntity<Image>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Images --------------------------------------------------------

    @RequestMapping(value = "/image/", method = RequestMethod.DELETE)
    public ResponseEntity<Image> deleteAllImages()
    {
        Log.debug("Deleting All Images");

        this.imageService.deleteAll();
        return new ResponseEntity<Image>(HttpStatus.NO_CONTENT);
    }
}
