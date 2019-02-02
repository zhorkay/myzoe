package io.myzoe.site.controllers;

import io.myzoe.config.annotation.RestEndpoint;
import io.myzoe.site.entities.PostSourceType;
import io.myzoe.site.services.PostSourceTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.util.List;

@RestEndpoint
public class PostSourceTypeController
{

    private static final Logger Log = LogManager.getLogger();

    @Inject
    PostSourceTypeService postSourceTypeService;

    //-------------------Retrieve All PostSourceTypes---------------------------------------------------------

    @RequestMapping(value = "/postsourcetypes/", method = RequestMethod.GET)
    public ResponseEntity<List<PostSourceType>> listAllPostSourceTypes()
    {
        List<PostSourceType> postsourcetypes = this.postSourceTypeService.getAllPostSourceTypes();
        if (postsourcetypes.isEmpty())
        {
            return new ResponseEntity<List<PostSourceType>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PostSourceType>>(postsourcetypes, HttpStatus.OK);
    }

    //-------------------Retrieve Single PostSourceType--------------------------------------------------------

    @RequestMapping(value = "/postsourcetype/{cd}", method = RequestMethod.GET)
    public ResponseEntity<PostSourceType> getPostSourceType(@PathVariable("cd") String cd) {
        Log.debug("Fetching PostSourceType with cd " + cd);
        PostSourceType postsourcetype = this.postSourceTypeService.getPostSourceType(cd);
        if (postsourcetype == null) {
            Log.debug("PostSourceType with cd " + cd + " not found");
            return new ResponseEntity<PostSourceType>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PostSourceType>(postsourcetype, HttpStatus.OK);
    }

    //-------------------Create a PostSourceType-----------------------------------------------------------------

    @RequestMapping(value = "/postsourcetype/", method = RequestMethod.POST)
    public ResponseEntity<Void> createPostSourceType(@RequestBody PostSourceType type, UriComponentsBuilder ucBuilder)
    {
        if (this.postSourceTypeService.getPostSourceType(type.getPostSourceTypeCode()) != null) {
            Log.debug("A PostSourceType with name " + type.getPostSourceTypeCode() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        this.postSourceTypeService.create(type);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/postsourcetype/{cd}").buildAndExpand(type.getPostSourceTypeCode()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //-------------------Update a PostSourceType-----------------------------------------------------------------

    @RequestMapping(value = "/postsourcetype/{cd}", method = RequestMethod.PUT)
    public ResponseEntity<PostSourceType> updatePostSourceType(@PathVariable("cd") String cd,
                                             @RequestBody PostSourceType type)
    {
        PostSourceType currentPostSourceType = this.postSourceTypeService.getPostSourceType(cd);

        if (currentPostSourceType == null)
        {
            Log.debug("PostSourceType does not exist: {}.", cd);
            return new ResponseEntity<PostSourceType>(HttpStatus.NOT_FOUND);
        }

        currentPostSourceType.setPostSourceTypeDesc(type.getPostSourceTypeDesc());

        this.postSourceTypeService.update(currentPostSourceType);

        return new ResponseEntity<PostSourceType>(currentPostSourceType, HttpStatus.OK);
    }

    //------------------- Delete a PostSourceType --------------------------------------------------------

    @RequestMapping(value = "/postsourcetype/{cd}", method = RequestMethod.DELETE)
    public ResponseEntity<PostSourceType> deletePostSourceType(@PathVariable("cd") String cd) {
        Log.debug("Fetching & Deleting PostSourceType with cd " + cd);

        PostSourceType postsourcetype = this.postSourceTypeService.getPostSourceType(cd);
        if (postsourcetype == null) {
            Log.debug("Unable to delete. PostSourceType with cd " + cd + " not found");
            return new ResponseEntity<PostSourceType>(HttpStatus.NOT_FOUND);
        }

        this.postSourceTypeService.delete(cd);
        return new ResponseEntity<PostSourceType>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All PostSourceTypes --------------------------------------------------------

    @RequestMapping(value = "/postsourcetype/", method = RequestMethod.DELETE)
    public ResponseEntity<PostSourceType> deleteAllPostSourceTypes()
    {
        Log.debug("Deleting All PostSourceTypes");

        this.postSourceTypeService.deleteAll();
        return new ResponseEntity<PostSourceType>(HttpStatus.NO_CONTENT);
    }
}
