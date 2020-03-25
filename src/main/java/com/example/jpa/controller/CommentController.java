package com.example.jpa.controller;

import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.model.Comment;
import com.example.jpa.model.Post;
import com.example.jpa.repository.CommentRepository;
import com.example.jpa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

   /* @GetMapping("/comments")
    public List<Comment> getAllComment(){
        return commentRepository.findAll();
    }*/

    @GetMapping("/posts/{postId}/comments")
    public Page<Comment>  getAllCommentByPostId(@PathVariable(value = "postId") Long postId, Pageable pageable){
        return commentRepository.findByPostId(postId, pageable);
    }

    @PostMapping("/posts/{postId}/comments")
    public Comment addComment(@PathVariable (value = "postId") Long postId, @RequestBody Comment comment){
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("PostId" + postId + " not found"));
        comment.setPost(post);
        return commentRepository.save(comment);
    }

/*
    @PostMapping
    public Comment createComment(@PathVariable Long postId, @RequestBody Comment comment){
     return postRepository.findById(postId).map(post -> {
         comment.setPost(post);
         return commentRepository.save(comment); })
             .orElseThrow(() -> new ResourceNotFoundException("PostId" + postId + " not found"));
    }
*/



    @PutMapping("/posts/{postId}/comments/{commentId}")
    public Comment updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody Comment comment){

     if(!postRepository.existsById(postId)){
         throw new ResourceNotFoundException("postId"+postId+"Not Found!");
     }
     return commentRepository.findById(commentId).map(comment1 -> {
                 comment1.setText(comment.getText());
                 return commentRepository.save(comment1);} )
             .orElseThrow(()-> new ResourceNotFoundException("Not Found" +commentId));

    }

    @DeleteMapping("comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentRepository.deleteById(commentId);
    }




}
