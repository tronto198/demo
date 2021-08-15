package com.example.demo.controller;

import com.example.demo.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class VideoController {
	private final VideoService videoService;

	@CrossOrigin
	@GetMapping(value = "video/{path}")
	public void getVideo(
		@PathVariable String path,
		HttpServletRequest req,
		HttpServletResponse res
	) {
		String range = req.getHeader("range");
		videoService.readVideo(path, range, res);
	}
}
