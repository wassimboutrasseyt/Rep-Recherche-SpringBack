package org.sid.appbackser.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@GetMapping
	public ResponseEntity<String> helloRUser() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("hello");
	}

	@GetMapping("/sayhello")
	public ResponseEntity<String> sayhello() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("hello");
	}
}
