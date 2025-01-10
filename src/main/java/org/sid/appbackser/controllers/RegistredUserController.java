package org.sid.appbackser.controllers;

import java.security.Principal;
import java.sql.Date;
import java.util.List;
import java.util.Map;


import org.sid.appbackser.dto.ChatGroupDTO;
import org.sid.appbackser.dto.UserLoggedDTO;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.User;
import org.sid.appbackser.entities.Project;
import org.sid.appbackser.entities.Proposition;
import org.sid.appbackser.entities.RessourceFolder.Depot;
import org.sid.appbackser.entities.RessourceFolder.Folder;
import org.sid.appbackser.services.AccountDetails;
import org.sid.appbackser.services.AccountService;
import org.sid.appbackser.services.ChatGroupService;
import org.sid.appbackser.services.DepotService;
import org.sid.appbackser.services.FolderService;
import org.sid.appbackser.services.ProjectService;
import org.sid.appbackser.services.PropositionService;
import org.sid.appbackser.services.UserService;
import org.sid.appbackser.services.implementations.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/registred-user")
public class RegistredUserController {


	private static final Logger logger = LoggerFactory.getLogger(RegistredUserController.class);


	@Autowired
	private JWTService jwtService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PropositionService propositionService;

	@Autowired
	private ChatGroupService chatGroupService;
	
	@Autowired
	private ProjectService	projectService;

	@Autowired
	private DepotService depotService;

	@Autowired
	private FolderService folderService;

	@Autowired
	private UserService	userService;
	// @Autowired
    // public RegistredUserController(JwtService jwtService) {
    //     this.jwtService = jwtService;
    // }

	@GetMapping("/hello")
	public ResponseEntity<String> hello(@AuthenticationPrincipal AccountDetails authAcc) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("hello " + authAcc.getAccount().getUser().getLastName().toUpperCase());
	}

	/*
	 *  chat section ----------------------------------------------------------------
	 */

	@GetMapping("/chat-groups")
	public ResponseEntity<List<ChatGroupDTO>> getMethodName(@AuthenticationPrincipal AccountDetails authAcc) {
		List<ChatGroupDTO> chatGroupDTOs = chatGroupService.getChatGroupsForAccount(authAcc.getAccount().getId());
		return ResponseEntity.ok(chatGroupDTOs);
	}

	/*
	 * user section ----------------------------------------------------------------
	 */

	@GetMapping("/me")
    public ResponseEntity<UserLoggedDTO> getUserInfo(@AuthenticationPrincipal AccountDetails acc) {
		Account account=acc.getAccount();
		UserLoggedDTO dto=accountService.loadInfo(account);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
    }

	@PutMapping("/profile/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody Map<String, Object> requestBody) {
		try {
			String firstName = (String) requestBody.get("firstName");
			String lastName = (String) requestBody.get("lastName");
			String phone = (String) requestBody.get("phone");
			Date dob = (Date) requestBody.get("dob");
			String profession = (String) requestBody.get("profession");
			
			User updatedUser =  userService.updateUser(id, firstName, lastName, phone, dob, profession);
			return ResponseEntity.ok(updatedUser);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user: " + e.getMessage());
		}
	}


	/*
	 * PROJECT SECTION ----------------------------------------------------------------
	 */

	@PostMapping("/ProjectProposition")
	public ResponseEntity<String> requestPrjtProposition(Principal principal, @RequestBody Proposition proposition) {
		try{

			proposition.setAccount(accountService.getAccountFromToken(principal));
			propositionService.createProposition(proposition);
			return ResponseEntity.ok("La proposition de projet a été créée avec succès.");
		} catch (Exception e) {
			// Gérer les erreurs et retourner une réponse appropriée
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Une erreur s'est produite lors de la création de la proposition."+e.getMessage());
		}
	}


	
	@GetMapping("/AllPropositionByMe")
	public ResponseEntity<Iterable<Proposition>> getProjectPropositions(Principal principal) {
		try {
			Iterable<Proposition> propositions = propositionService.getPropositionsByAccount(accountService.getAccountFromToken(principal));
			return ResponseEntity.ok(propositions);
		} catch (Exception e) {
			// Gérer les erreurs et retourner une réponse appropriée
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}

	@PostMapping("/projects/by-ids")
    public ResponseEntity<List<Project>> getProjects(@RequestBody List<Integer> projectIds) {
        List<Project> projects = projectService.getProjectsByIds(projectIds);
        return ResponseEntity.ok(projects);
    }

	@GetMapping("/project")
	public ResponseEntity<?> getProjectByShortName(@RequestParam String shortName) {
		try {
			Project project = projectService.getProjectByShortName(shortName);
			if (project == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found: No project exists with the given short name.");
			}
			return ResponseEntity.ok(project);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred. Please try again.");
		}
	}



	
	/*
	 * projects depot section ----------------------------------------------------------------
	 */
	
	@PostMapping("/project/depot/create-folder")
	public ResponseEntity<Folder> createFolder(@RequestBody Map<String, Object> requestBody) {

		/*
		* Request body example:
		* {
		*    "depotId": 1,
		*    "name": "New Folder",
		*    "parentFolderId": 2 // if it's a subfolde, if the folder will be created directly on the SRC or WEB, this should be null or absent
		* }
		*
		*/

		Integer depotId = (Integer) requestBody.get("depotId");
		String name = (String) requestBody.get("name");
		Integer parentFolderId = requestBody.get("parentFolderId") != null ? (Integer) requestBody.get("parentFolderId") : null;
	
		Folder folder = folderService.createFolder(depotId, name, parentFolderId);
		return ResponseEntity.ok(folder);
	}
	

	@DeleteMapping("/delete/{folderId}")
	public ResponseEntity<Void> deleteFolder(@PathVariable Integer folderId) {
		folderService.deleteFolder(folderId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/project/depot/{id}")
	public ResponseEntity<?> getProjectByShortName(@PathVariable Integer depotId) {
		try {
			Depot depot = depotService.getDepotById(depotId);
			if (depot == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Depot not found: No project exists with the given Id.");
			}
			return ResponseEntity.ok(depot);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred. Please try again.");
		}
	}


}
