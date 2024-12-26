package org.sid.appbackser.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ruser")
public class RegistredUserController {

	@GetMapping
	public ResponseEntity<String> hello() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("hello");
	}
	
}
