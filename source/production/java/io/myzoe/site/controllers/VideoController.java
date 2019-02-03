package io.myzoe.site.controllers;

import io.myzoe.config.annotation.RestEndpoint;
import io.myzoe.site.entities.Video;
import io.myzoe.site.services.VideoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    //-------------------Retrieve All Videos per Pages-----------------------------------------------

    @RequestMapping(value = "/video/", method = RequestMethod.GET)
    public ResponseEntity<Page<Video>> pagingVideos(@RequestParam int pageindex,
                                                    @RequestParam int pagesize)
    {
        Pageable page = new PageRequest(pageindex, pagesize);
        Page<Video> videos = this.videoService.getPagesVideos(page);
        if (videos.getSize() == 0)
        {
            Log.debug("Video Not Found");
            return new ResponseEntity<Page<Video>>(HttpStatus.NO_CONTENT);
        }
        Log.debug("Video found.");
        return new ResponseEntity<Page<Video>>(videos, HttpStatus.OK);
    }

}