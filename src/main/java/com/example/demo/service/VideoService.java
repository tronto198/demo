package com.example.demo.service;

import com.example.demo.Config;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Service
public class VideoService {
	/***
	 * 비디오 파일 저장, UUID를 활용하여 중복 최소화
	 * @param video 비디오 파일
	 * @return 저장된 위치
	 */
	public String saveVideo(
		MultipartFile video
	) {
		if(video != null && !video.isEmpty()){
			try {
				String videoPath = UUID.randomUUID().toString();
				File file = new File(Config.videoRootPath + File.separator + videoPath);
				if(file.getParentFile() != null)
					file.getParentFile().mkdirs();
				FileCopyUtils.copy(video.getBytes(), file);
				return videoPath;
			} catch (IOException e){
				// ignore
				System.out.println("error - "+e.toString());
			}
		}
		return null;
	}

	/**
	 * 비디오 파일은 한번에 읽기에 부담이 되므로 일정 부분씩 끊어서 읽음
	 * 그 과정에서 서버가 응답하는 부분이 구현된 함수
	 * @param path 비디오 파일 경로
	 * @param range 클라이언트가 요청한 부분
	 * @param res 해당 요청에 대한 Response 객체
	 */
	public void readVideo(String path, String range, HttpServletResponse res) {
		String separator = "-";
		try {
			File file = new File(Config.videoRootPath + File.separator + path);
			RandomAccessFile rFile = new RandomAccessFile(file, "r");

			long rangeStart = 0;
			long rangeEnd = 0;
			boolean isPart = false;

			long videoSize = rFile.length();
			if(range != null){
				if(range.endsWith(separator)){
					range = range + (videoSize - 1);
				}
				int idx = range.trim().indexOf(separator);
				rangeStart = Long.parseLong(range.substring(6, idx));
				rangeEnd = Long.parseLong(range.substring(idx + 1));
				if(rangeStart > 0)
					isPart = true;
			}
			else {
				rangeStart = 0;
				rangeEnd = videoSize - 1;
			}

			long partSize = rangeEnd - rangeStart + 1;
			res.reset();
			res.setStatus(isPart ? 206 : 200);
			res.setContentType("video/mp4");
			res.setHeader("Content-Range", "bytes " + rangeStart + separator + rangeEnd + "/" + videoSize);
			res.setHeader("Accept-Ranges", "bytes");
			res.setHeader("Content-Length", partSize + "");
			OutputStream os = res.getOutputStream();
			rFile.seek(rangeStart);

			int bufferSize = 2048;
			byte[] data = new byte[bufferSize];
			while (partSize > 0){
				int chunk = partSize > bufferSize ? bufferSize : (int) partSize;
				int read = rFile.read(data, 0, chunk);
				os.write(data, 0, read);
				partSize -= chunk;
			}
		} catch (IOException ioE) {
			// ignore
			System.out.println(ioE.toString());
		}
	}
}
