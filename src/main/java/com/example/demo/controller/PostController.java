package com.example.demo.controller;

import com.example.demo.dto.PostCreateRequestDto;
import com.example.demo.dto.PostGetResponseDto;
import com.example.demo.dto.PostListResponseDto;
import com.example.demo.dto.PostUpdateRequestDto;
import com.example.demo.service.ImageService;
import com.example.demo.service.PostService;
import com.example.demo.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/*
	타임리프 관련 ref : https://sidepower.tistory.com/145
	https://victorydntmd.tistory.com/327
 */
@Controller
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	private final ImageService imageService;
	private final VideoService videoService;

	@GetMapping("/post")
	public String getPostList(
		Model model,
		Pageable pageable
	) {
		PostListResponseDto postList = postService.getAllPost(pageable);
		model.addAttribute("data", postList);
		return "postList";
	}

	@GetMapping("/post/{id}")
	public String getPost(@PathVariable Long id, Model model) {
		PostGetResponseDto dto = postService.getPost(id);
		model.addAttribute("post", dto);
		return "post";
	}

	@PutMapping("/post/{id}")
	public String updatePost(@PathVariable Long id,
							 @RequestPart(value = "image", required = false) MultipartFile image,
							 @RequestPart(value = "video", required = false) MultipartFile video,
							 PostUpdateRequestDto data) {
		if (data.isImageChanged()) {
			String imagePath = imageService.saveImage(image);
			data.setImagePath(imagePath);
		}

		if (data.isVideoChanged()) {
			String videoPath = videoService.saveVideo(video);
			data.setVideoPath(videoPath);
		}

		postService.updatePost(id, data);
		return "redirect:/post";
	}

	@DeleteMapping("/post/{id}")
	public String deletePost(@PathVariable Long id) {
		postService.deletePost(id);
		return "redirect:/post";
	}

	@PostMapping("/post")
	public String createPost(
		PostCreateRequestDto data,
		@RequestPart(value = "image", required = false) MultipartFile image,
		@RequestPart(value = "video", required = false) MultipartFile video
	) {
		String imagePath = imageService.saveImage(image);
		data.setImagePath(imagePath);

		String videoPath = videoService.saveVideo(video);
		data.setVideoPath(videoPath);

		postService.createPost(data);
		return "redirect:/post";
	}

	@GetMapping("/post/new")
	public String newPost() {
		return "newPost";
	}

	@GetMapping("/post/update/{id}")
	public String updatePost(@PathVariable Long id, Model model) {
		PostGetResponseDto post = postService.getPost(id);
		model.addAttribute("post", post);
		return "updatePost";
	}
}
