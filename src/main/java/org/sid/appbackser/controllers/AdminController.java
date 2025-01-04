package org.sid.appbackser.controllers;

import java.util.List;

import org.sid.appbackser.entities.Proposition;
import org.sid.appbackser.services.PropositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private PropositionService propositionService;

	@GetMapping
	public ResponseEntity<String> helloRUser() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("hello");
	}

	@GetMapping("/sayhello")
	public ResponseEntity<String> sayhello() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("hello");
	}
	@GetMapping("/PendingPropositions")
	public List<Proposition> getPendingPropositions() {
		return propositionService.getPendingPropositions();
	}

	@GetMapping("/AllPropositions")
	public List<Proposition> getAllPropositions() {
		return propositionService.getAllPropositions();
	}

	@PostMapping("/ApproveProposition")
	public ResponseEntity<Proposition> ApproveProposition(@RequestBody Integer propositionID) {
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(
				propositionService.approveProposition(propositionID));
	}
	

	
}
