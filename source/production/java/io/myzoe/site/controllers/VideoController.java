package io.myzoe.site.controllers;

import io.myzoe.config.annotation.RestEndpoint;
import io.myzoe.site.entities.Video;
import io.myzoe.site.services.VideoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

@RestEndpoint
public class VideoController {


    private static final Logger Log = LogManager.getLogger();

    @Inject
    VideoService videoService;

    //-------------------Retrieve All Videos---------------------------------------------------------

    @RequestMapping(value = "/videos/", method = RequestMethod.GET)
    public ResponseEntity<List<Video>> listAllVideos()
    {
        List<Video> videos = this.videoService.getAllVideos();
        if (videos.isEmpty())
        {
            return new ResponseEntity<List<Video>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Video>>(videos, HttpStatus.OK);
    }

}