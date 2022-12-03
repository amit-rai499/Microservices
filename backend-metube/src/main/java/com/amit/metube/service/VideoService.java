package com.amit.metube.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amit.metube.dto.CommentDto;
import com.amit.metube.dto.UploadVideoResponse;
import com.amit.metube.dto.VideoDto;
import com.amit.metube.model.Comment;
import com.amit.metube.model.Video;
import com.amit.metube.repo.VideoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoService {
	
	private final S3Service s3Service;
	
	private final VideoRepository videoRepository;
	
	private final UserService userService;
	
	public UploadVideoResponse uploadVideo(MultipartFile file) {
		String videoUrl =  s3Service.uploadFile(file);
		var video = new Video();
		video.setVideoUrl(videoUrl);
		
		var savedVideo =  videoRepository.save(video);
		return new UploadVideoResponse(savedVideo.getId(),savedVideo.getVideoUrl());
	}

	public VideoDto editVideo(VideoDto videoDto) {
		// Find the video by videoId
		var savedVideo = getVideoById(videoDto.getId());
		
		// Map the videoDto fields to video
		savedVideo.setTitle(videoDto.getTitle());
		savedVideo.setDescription(videoDto.getDescription());
		savedVideo.setTags(videoDto.getTags());
		savedVideo.setThumbnailUrl(savedVideo.getThumbnailUrl());
		savedVideo.setVideoStatus(videoDto.getVideoStatus());
		savedVideo.setUserId(videoDto.getUserId());
		// save the video to database
		videoRepository.save(savedVideo);
		return videoDto;
	}

	public String uploadThumbnail(MultipartFile file, String videoId) {
		var savedVideo =  getVideoById(videoId);
		String thumbnailUrl = s3Service.uploadFile(file);
		savedVideo.setThumbnailUrl(thumbnailUrl);
		videoRepository.save(savedVideo);
		System.out.println(savedVideo);
		return thumbnailUrl;
	}
	
	public Video getVideoById(String videoId) {
		return videoRepository.findById(videoId).orElseThrow(() -> new IllegalArgumentException("Cannot find video by id - "+videoId));
	}
	
	public VideoDto getVideoDetails(String videoId) {
		Video savedVideo =  getVideoById(videoId);
		
		increaseViewCount(savedVideo);
		userService.addVideoToHistory(videoId);
		
		return mapToVideoDto(savedVideo);
	}

	private void increaseViewCount(Video savedVideo) {
		savedVideo.incrementViewCount();
		videoRepository.save(savedVideo);
	}

	public VideoDto likeVideo(String videoId) {
		// Get Video By Id
		Video savedVideo =  getVideoById(videoId);
		
		// Increment ikes Count
		
		// If user already liked the video, then decrement like count
		if(userService.isLikedVdeo(videoId)) {
			savedVideo.decrementLikes();
			userService.removeFromLikedVideos(videoId);
		} else if (userService.isDisLikedVdeo(videoId)) {
			savedVideo.decrementDislikes();
			userService.removeFromDislikedVideos(videoId);
			savedVideo.incrementLikes();
			userService.addToLikedVideos(videoId);
		} else {
			savedVideo.incrementLikes();
			userService.addToLikedVideos(videoId);
		}
		
		videoRepository.save(savedVideo);
		
		return mapToVideoDto(savedVideo);
		
	}

	public VideoDto dislikeVideo(String videoId) {
		// Get Video By Id
				Video savedVideo =  getVideoById(videoId);
				
				// Increment ikes Count
				
				// If user already liked the video, then decrement like count
				if(userService.isDisLikedVdeo(videoId)) {
					savedVideo.decrementDislikes();
					userService.removeFromDislikedVideos(videoId);
				} else if (userService.isLikedVdeo(videoId)) {
					savedVideo.decrementLikes();
					userService.removeFromLikedVideos(videoId);
					savedVideo.incrementDislikes();
					userService.addToDislikedVideos(videoId);
				} else {
					savedVideo.incrementDislikes();
					userService.addToDislikedVideos(videoId);
				}
				
				videoRepository.save(savedVideo);
				
				return mapToVideoDto(savedVideo);
	}
	
	public VideoDto mapToVideoDto(Video video) {
		VideoDto videoDto = new VideoDto();
		videoDto.setVideoUrl(video.getVideoUrl());
		videoDto.setThumbnailUrl(video.getThumbnailUrl());
		videoDto.setId(video.getId());
		videoDto.setTitle(video.getTitle());
		videoDto.setDescription(video.getDescription());
		videoDto.setTags(video.getTags());
		videoDto.setVideoStatus(video.getVideoStatus());
		videoDto.setLikeCount(video.getLikes().get());
		videoDto.setDislikeCount(video.getDisLikes().get());
		videoDto.setViewCount(video.getViewCount().get());
		return videoDto;
	}

	public void addComment(String videoId, CommentDto commentDto) {
		Video video = getVideoById(videoId);
		Comment comment = new Comment();
		comment.setText(commentDto.getCommentText());
		comment.setAuthorId(commentDto.getAuthorId());
		video.addComment(comment);
		videoRepository.save(video);
	}

	public List<CommentDto> getAllComments(String videoId) {
		Video video = getVideoById(videoId);
		List<Comment> comments = video.getCommentList();
		return comments.stream().map(this::mapToCommentDto).collect(Collectors.toList());
	}

	private CommentDto mapToCommentDto(Comment comment) {
		CommentDto commentDto = new CommentDto();
		commentDto.setCommentText(comment.getText());
		commentDto.setAuthorId(comment.getAuthorId());
		return commentDto;
	}

	public List<VideoDto> getAllVideos() {
		return videoRepository.findAll().stream().map(this::mapToVideoDto).collect(Collectors.toList());
	}
}
