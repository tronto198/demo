package com.example.demo.service;

import com.example.demo.dto.PostCreateRequestDto;
import com.example.demo.dto.PostGetResponseDto;
import com.example.demo.dto.PostListResponseDto;
import com.example.demo.dto.PostUpdateRequestDto;
import com.example.demo.model.Post;
import com.example.demo.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostRepository repository;

	/*
		get
		get list
		create
		update
		delete
	 */

	public Long createPost(PostCreateRequestDto dto) {
		return repository.save(dto.toEntity()).getId();
	}

	public PostListResponseDto getAllPost(Pageable pageable) {
		Page<Post> posts = repository.findAll(pageable);
		List<PostGetResponseDto> results = new ArrayList<>();
		for (Post post : posts) {
			results.add(new PostGetResponseDto(post));
		}

		return new PostListResponseDto(results, posts.getNumber(), posts.getSize(), posts.hasPrevious(), posts.hasNext());
	}

	public PostGetResponseDto getPost(Long id) {
		Post post = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시물이 없습니다."));

		return new PostGetResponseDto(post);
	}

	@Transactional
	public Long updatePost(Long id, PostUpdateRequestDto dto) {
		Post post = repository.findById(id).orElseThrow(() ->
			new IllegalArgumentException(("해당 아이디의 게시물이 없습니다."))
		);

		post.update(dto.getTitle(), dto.getContents(), dto.getImagePath(), dto.isImageChanged(), dto.getVideoPath(), dto.isVideoChanged());

		return post.getId();
	}

	public void deletePost(Long id) {
		repository.deleteById(id);
	}

}
