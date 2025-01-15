package org.sid.appbackser.controllers;

import java.io.File;
import java.nio.file.Paths;

import org.sid.appbackser.entities.Project;
import org.sid.appbackser.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/guest")
public class GuestController {



	@GetMapping
	public ResponseEntity<String> helloAdmin() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("hello");
	}

	
}
