package com.amit.metube.service;

import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.amit.metube.model.User;
import com.amit.metube.model.Video;
import com.amit.metube.repo.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	public User getCurrentUser() {
		String sub  =	((Jwt)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getClaim("sub");
		return userRepository.findBySub(sub).orElseThrow(()-> new IllegalArgumentException("Canot find user with sub - "+sub));
	}

	public void addToLikedVideos(String videoId) {
		User currentUser = getCurrentUser();
		currentUser.addToLikedVideos(videoId);
		userRepository.save(currentUser);
	}
	
	public boolean isLikedVdeo(String videoId) {
		return getCurrentUser().getLikedVideos().stream().anyMatch(likedVideo -> likedVideo.equals(videoId));
	}
	
	public boolean isDisLikedVdeo(String videoId) {
		return getCurrentUser().getDislikedVideos().stream().anyMatch(dislikedVideo -> dislikedVideo.equals(videoId));
	}

	public void removeFromLikedVideos(String videoId) {
		User currentUser = getCurrentUser();
		currentUser.removeFromLikedVideos(videoId);
		userRepository.save(currentUser);
	}

	public void removeFromDislikedVideos(String videoId) {
		User currentUser = getCurrentUser();
		currentUser.removeFromDisikedVideos(videoId);
		userRepository.save(currentUser);
	}

	public void addToDislikedVideos(String videoId) {
		User currentUser = getCurrentUser();
		currentUser.addToDislikedVideos(videoId);
		userRepository.save(currentUser);
	}

	public void addVideoToHistory(String videoId) {
		User currentUser = getCurrentUser();
		currentUser.addToVideoHistory(videoId);
		userRepository.save(currentUser);
	}

	public boolean subscribeUser(String userId) {
		// Retrieve the current user and add the userId to the subscribed to users set
		// Retrive the target user and add the current user to the subscribers list
		User currentUser = getCurrentUser();
		currentUser.addToSubscribedToUsers(userId);
		User user = getUserById(userId);
		
		user.addToSubscribers(currentUser.getId());
		
		userRepository.save(currentUser);
		userRepository.save(user);
		return true;
	}
	
	public boolean unSubscribeUser(String userId) {
		// Retrieve the current user and add the userId to the subscribed to users set
		// Retrive the target user and add the current user to the subscribers list
		User currentUser = getCurrentUser();
		currentUser.removeFromSubscribedToUsers(userId);
		User user = getUserById(userId);
		
		user.removeFromSubscribers(currentUser.getId());
		
		userRepository.save(currentUser);
		userRepository.save(user);
		return true;
	}

	private User getUserById(String userId) {
		User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("Cannot find user with userId : "+userId));
		return user;
	}

	public Set<String> getUserHistory(String userId) {
		User user = getUserById(userId);
		return user.getVideoHistory();
	}
}
