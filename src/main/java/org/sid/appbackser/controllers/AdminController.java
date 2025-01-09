package org.sid.appbackser.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.sid.appbackser.dto.UserLoggedDTO;
import org.sid.appbackser.entities.Project;
import org.sid.appbackser.entities.Proposition;
import org.sid.appbackser.entities.User;
import org.sid.appbackser.services.ProjectService;
import org.sid.appbackser.services.PropositionService;
import org.sid.appbackser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/admin")
public class AdminController {

	Logger	logger = Logger.getLogger(AdminController.class.getName());

	@Autowired
	private PropositionService propositionService;

	@Autowired
	private UserService	userService;

	@Autowired
	private ProjectService projectService;


	@GetMapping
	public ResponseEntity<String> helloRUser() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("hello");
	}
	
	@GetMapping("/users")
	public ResponseEntity<Page<UserLoggedDTO>> paginateUsers(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	
	) {
		page = Math.max(page, 0); 
		size = Math.max(size, 1);
		PageRequest pageable = PageRequest.of(page, size);
		Page<User> users = userService.findAllUsers(pageable);
		List<UserLoggedDTO> userLoggedDtos = new ArrayList<>();
		users.forEach(user -> {
			user.getAccounts().forEach(account -> {
				userLoggedDtos.add(new UserLoggedDTO(user, account.getId(), account.getEmail(), account.getRole()));
			});
		});
		Page<UserLoggedDTO> userLoggedDtoPage = new PageImpl<>(userLoggedDtos, pageable, users.getTotalElements());
		return ResponseEntity.ok(userLoggedDtoPage);
	}	
	


	//propositions
	@GetMapping("/PendingPropositions")
	public List<Proposition> getPendingPropositions() {
		return propositionService.getPendingPropositions();
	}

	@GetMapping("/AllPropositions")
	public List<Proposition> getAllPropositions() {
		return propositionService.getAllPropositions();
	}

	@PostMapping("/ApproveProposition/{propositionID}")
	public ResponseEntity<Proposition> ApproveProposition(@PathVariable("propositionID") Integer propositionID) {
		logger.info("the proposition is on approving treatement");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(
				propositionService.approveProposition(propositionID));
	}
	
	@PostMapping("/RejectProposition/{propositionID}")
	public ResponseEntity<?> rejectProposition(@PathVariable("propositionID") Integer propositionID) {
		logger.info("the proposition is on rejecting treatement");
		propositionService.rejectProposition(propositionID);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Proposition rejected successfully");
	}

	//projects
	@GetMapping("/projects")
	public ResponseEntity<Page<Project>> paginateProjects(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	
	) {
		page = Math.max(page, 0); 
		size = Math.max(size, 1);
		PageRequest pageable = PageRequest.of(page, size);
		Page<Project>  projects= projectService.findAllProjects(pageable);
		return ResponseEntity.ok(projects);
	}	
	
	
}
