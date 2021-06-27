package com.tweetapp.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tweetapp.project.entity.UserDetails;
import com.tweetapp.project.repository.UserDetailsRepository;

@Service
public class UserService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;
//	@Autowired
//	private SequenceGeneratorService sequenceGeneratorService;
	
	public static ObjectMapper mapper = new ObjectMapper();
	
	public ObjectNode registerUser(UserDetails registerUser) {
		ObjectNode statusNode = mapper.createObjectNode();
		try {
			registerUser.setId(generateId((long) Math.floor(Math.random() * 2)));
//			registerUser.setId(sequenceGeneratorService.generateSequence(UserDetails.SEQUENCE_NAME));
			if(userDetailsRepository.getUserDetails(registerUser.getLoginid(), registerUser.getPassword()) == null) {
				userDetailsRepository.save(registerUser);
				statusNode.put("status", true);
			} else {
				throw new Exception("User already exist!!");
			}
		}
//		catch(DuplicateKeyException e) {
//			statusNode.put("status", false);
//			sequenceGeneratorService.decrement(UserDetails.SEQUENCE_NAME);
//		}
		catch (Exception e) {
			statusNode.put("status", false);
			statusNode.put("errors", e.getMessage());
//			sequenceGeneratorService.decrement(UserDetails.SEQUENCE_NAME);
		}
		return statusNode;
	}
	
	public ObjectNode loginUser(UserDetails loginUser) {
		ObjectNode statusNode = mapper.createObjectNode();
		ObjectNode userDetails = mapper.createObjectNode();
		try {
			UserDetails user = userDetailsRepository.getUserDetails(loginUser.getLoginid(), loginUser.getPassword());
			if(user == null) {
				throw new Exception("User not found");
			}
			statusNode.put("status", true);
			userDetails.put("id", user.getId());
			userDetails.put("firstName", user.getFirstname());
			userDetails.put("lastName", user.getLastname());
			userDetails.put("email", user.getEmail());
			userDetails.put("loginid", user.getLoginid());
			statusNode.set("userDetails", userDetails);
		} catch (Exception e) {
			statusNode.put("status", false);
			statusNode.put("errors", e.toString());
		}
		return statusNode;
	}
	
	public ObjectNode getUsersList() {
		ObjectNode statusNode = mapper.createObjectNode();
		try {
			List<UserDetails> users = new ArrayList<UserDetails>();
			userDetailsRepository.getUserDetailsList().forEach(users::add);
			statusNode.put("status", true);
			ArrayNode array = mapper.createArrayNode();
			for (UserDetails user : users) {
				ObjectNode userNode = mapper.createObjectNode();
				userNode.put("id", user.getId());
				userNode.put("firstname", user.getFirstname());
				userNode.put("lastname", user.getLastname());
				userNode.put("email", user.getEmail());
				userNode.put("loginid", user.getLoginid());
				array.add(userNode);
			}		
			statusNode.putArray("users").addAll(array);
		} catch (Exception e) {
			statusNode.put("status", false);
			statusNode.put("errors", e.toString());
		}
		return statusNode;
	}
	
	public ObjectNode changePassword(UserDetails user) {
		ObjectNode statusNode = mapper.createObjectNode();
		String newPassword = user.getPassword();
		try {
			UserDetails userDetails = userDetailsRepository.getUserId(user.getLoginid());
			if(userDetails != null) {
			userDetails.setPassword(newPassword);
			userDetailsRepository.updatePassword(userDetails);
			statusNode.put("status", true);
			} else {
				throw new Exception("Couldn't find user.");
			}
		} catch (Exception e) {
			statusNode.put("status", false);
			statusNode.put("errors", e.toString());
		}
		return statusNode;
	}
	
	private long generateId(long randomId) {
		long ts = new Date().getTime();
		long randid = (long) Math.floor(Math.random() * 2);
		ts = ts + randomId;
		long uuid = ts + randid;
		return uuid;
	}
	
}
