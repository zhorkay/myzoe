package io.myzoe.site.controllers;

import io.myzoe.config.annotation.RestEndpoint;
import io.myzoe.site.entities.Post;
import io.myzoe.site.services.PostService;
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
public class PostController
{
    private static final Logger Log = LogManager.getLogger();

    @Inject PostService postService;

    //-------------------Retrieve All Posts---------------------------------------------------------

    @RequestMapping(value = "posts", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> listAllPosts()
    {
        List<Post> posts = this.postService.getAllPosts();
        if (posts.isEmpty())
        {
            return new ResponseEntity<List<Post>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    //-------------------Retrieve All Posts per Pages-----------------------------------------------

    @RequestMapping(value = "post", method = RequestMethod.GET)
    public ResponseEntity<Page<Post>> pagingPosts(@RequestParam int pageindex,
                                                  @RequestParam int pagesize)
    {
        Pageable page = new PageRequest(pageindex, pagesize);
        Page<Post> posts = this.postService.getPagesPosts(page);
        if (posts.getSize() == 0)
        {
            Log.debug("Post Not Found");
            return new ResponseEntity<Page<Post>>(HttpStatus.NO_CONTENT);
        }
        Log.debug("Post found.");
        return new ResponseEntity<Page<Post>>(posts, HttpStatus.OK);
    }

    //-------------------Retrieve Single Post--------------------------------------------------------

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public ResponseEntity<Post> getPost(@PathVariable("id") long id) {
        Log.debug("Fetching Post with id " + id);
        Post post = this.postService.getPost(id);
        if (post == null) {
            Log.debug("Post with id " + id + " not found");
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    //-------------------Create a Post-----------------------------------------------------------------

    @RequestMapping(value = "post", method = RequestMethod.POST)
    public ResponseEntity<Void> createPost(@RequestBody Post post, UriComponentsBuilder ucBuilder)
    {
        if (this.postService.getPost(post.getId()) != null) {
            Log.debug("A Post with name " + post.getPostName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        this.postService.create(post);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/post/{id}").buildAndExpand(post.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //-------------------Update a Post-----------------------------------------------------------------

    @RequestMapping(value = "/post/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Post> updatePost(@PathVariable("id") long id,
                                           @RequestBody Post post)
    {
        Post currentPost = this.postService.getPost(id);

        if (currentPost == null)
        {
            Log.debug("Post does not exist: {}.", id);
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }

        currentPost.setUserId(post.getUserId());
        currentPost.setPostName(post.getPostName());
        currentPost.setPostDesc(post.getPostDesc());
        currentPost.setPostSourceText(post.getPostSourceText());
        currentPost.setPostSourceType(post.getPostSourceType());
        currentPost.setPostEndDate(post.getPostEndDate());
        currentPost.setPostStartDate(post.getPostStartDate());
        currentPost.setPostRankValue(post.getPostRankValue());

        this.postService.update(currentPost);

        return new ResponseEntity<Post>(currentPost, HttpStatus.OK);
    }

    //------------------- Delete a Post --------------------------------------------------------

    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Post> deletePost(@PathVariable("id") long id) {
        Log.debug("Fetching & Deleting Post with id " + id);

        Post post = this.postService.getPost(id);
        if (post == null) {
            Log.debug("Unable to delete. Post with id " + id + " not found");
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }

        this.postService.delete(id);
        return new ResponseEntity<Post>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Posts --------------------------------------------------------

    @RequestMapping(value = "post", method = RequestMethod.DELETE)
    public ResponseEntity<Post> deleteAllPosts()
    {
        Log.debug("Deleting All Posts");

        this.postService.deleteAll();
        return new ResponseEntity<Post>(HttpStatus.NO_CONTENT);
    }
}
