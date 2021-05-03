package com.tweetapp.project;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tweetapp.project.entity.UserDetails;
import com.tweetapp.project.repository.UserDetailsRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ApplicationController {

	private UserDetailsRepository userDetailsRepository;

	@PostMapping("/api/v1.0/tweets/register")
	public ObjectNode registerUser(@RequestBody UserDetails requestObject) {
//		userDetailsRepository.save(requestObject);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode statusNode = mapper.createObjectNode();
		ObjectNode responseNode = mapper.createObjectNode();
		statusNode.put("status", "true");
		responseNode.put("serviceResponse", responseNode);
	    return responseNode;
	}
	
}