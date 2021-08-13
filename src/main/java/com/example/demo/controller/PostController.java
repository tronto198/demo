package com.example.demo.controller;

import com.example.demo.dto.PostCreateRequestDto;
import com.example.demo.dto.PostGetResponseDto;
import com.example.demo.dto.PostListResponseDto;
import com.example.demo.dto.PostUpdateRequestDto;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
	타임리프 관련 ref : https://sidepower.tistory.com/145
	https://victorydntmd.tistory.com/327
 */
@Controller
@RequiredArgsConstructor
public class PostController {
	private final PostService service;

	@GetMapping("/post")
	public String getPostList(
		Model model,
		Pageable pageable
	) {
		PostListResponseDto postList = service.getAllPost(pageable);
		model.addAttribute("data", postList);
		return "postList";
	}

	@GetMapping("/post/{id}")
	public String getPost(@PathVariable Long id, Model model) {
		PostGetResponseDto dto = service.getPost(id);
		model.addAttribute("post", dto);
		return "post";
	}

	@PutMapping("/post/{id}")
	public String updatePost(@PathVariable Long id, PostUpdateRequestDto data) {
		service.updatePost(id, data);
		return "redirect:/post";
	}

	@DeleteMapping("/post/{id}")
	public String deletePost(@PathVariable Long id) {
		service.deletePost(id);
		return "redirect:/post";
	}

	@PostMapping("/post")
	public String createPost(PostCreateRequestDto data) {
		service.createPost(data);
		return "redirect:/post";
	}

	@GetMapping("/post/new")
	public String newPost() {
		return "newPost";
	}

	@GetMapping("/post/update/{id}")
	public String updatePost(@PathVariable Long id, Model model) {
		PostGetResponseDto post = service.getPost(id);
		model.addAttribute("post", post);
		return "updatePost";
	}
}
