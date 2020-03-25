package com.example.jpa.controller;

import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.model.Post;
import com.example.jpa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PostController {

    @Autowired
    PostRepository postRepository;


    @GetMapping("/posts")
    public Page<Post> getAllPosts(Pageable pageable){
        return postRepository.findAll(pageable);
    }
    @GetMapping("/posts/{postId}")
    public Post getAllPostsById(@PathVariable Long postId){
        return postRepository.findById(postId).get();
    }

    @PostMapping("/posts")
    public  Post  createPost(@RequestBody Post post){
        return postRepository.save(post);
    }

  /*  @PutMapping("/posts/{postId}")
    public  Post updatePost(@PathVariable Long postId,  @RequestBody Post post){
          post.setId(postId);
        return postRepository.save(post);
    }*/
    @PutMapping("/posts/{postId}")
    public  Post updatePost(@PathVariable Long postId,  @RequestBody Post post) {
        return postRepository.findById(postId).map(post1 -> {
            post1.setDesc(post.getDesc());
            post1.setTitle(post.getTitle());
            post1.setContent(post.getContent());
            return postRepository.save(post1);
        }).orElseThrow(() -> new ResourceNotFoundException("Post Id Not Found" + postId));
    }

 /*   @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id){
         postRepository.deleteById(id);
    }*/

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        return postRepository.findById(postId).map(post -> {
            postRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }
}
