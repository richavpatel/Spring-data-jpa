package com.example.jpa;


import com.example.jpa.model.Comment;
import com.example.jpa.model.Post;
import com.example.jpa.repository.CommentRepository;
import com.example.jpa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
@EnableJpaAuditing
public class JpaApplication implements CommandLineRunner {

	@Autowired
	PostRepository postRepository;

	@Autowired
    CommentRepository commentRepository;

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Post post1 = new Post("dff","dd","dd");
		Post savedPost = postRepository.save(post1);


		//Comment comment1 = new Comment("text1");

		//Comment comments = new ArrayList<>();
		List<Comment> comments = Arrays.asList(
				new Comment("tex1"),
				new Comment("text11")
		);
		for(Comment c:comments){
			c.setPost(post1);
		}
		//comments.setPost(savedPost);
		commentRepository.saveAll(comments);


	/*	List<Comment> comments = new ArrayList<>();
		comments.add(comment1);
		post1.setComments(comments);*/

		//System.out.println(posts);





	}
}
