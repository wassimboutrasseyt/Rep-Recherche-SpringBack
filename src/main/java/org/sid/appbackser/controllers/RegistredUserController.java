package org.sid.appbackser.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.sid.appbackser.dto.ChatGroupDTO;
import org.sid.appbackser.dto.UserLoggedDTO;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Project;
import org.sid.appbackser.entities.Proposition;
import org.sid.appbackser.entities.RessourceFolder.Depot;
import org.sid.appbackser.entities.RessourceFolder.Folder;
import org.sid.appbackser.services.AccountDetails;
import org.sid.appbackser.services.AccountService;
import org.sid.appbackser.services.ChatGroupService;
import org.sid.appbackser.services.DepotService;
import org.sid.appbackser.services.FolderService;
import org.sid.appbackser.services.PropositionService;
import org.sid.appbackser.services.implementations.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
	private FolderService folderService;

	
	// @Autowired
    // public RegistredUserController(JwtService jwtService) {
    //     this.jwtService = jwtService;
    // }

	@GetMapping("/hello")
	public ResponseEntity<String> hello(@AuthenticationPrincipal AccountDetails authAcc) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("hello " + authAcc.getAccount().getUser().getLastName().toUpperCase());
	}

	// chat section
	@GetMapping("/chat-groups")
	public ResponseEntity<List<ChatGroupDTO>> getMethodName(@AuthenticationPrincipal AccountDetails authAcc) {
		List<ChatGroupDTO> chatGroupDTOs = chatGroupService.getChatGroupsForAccount(authAcc.getAccount().getId());
		return ResponseEntity.ok(chatGroupDTOs);
	}



	//projects depot section
	@PostMapping("/project/depot/create-folder")
	public ResponseEntity<Folder> createFolder(@RequestBody Map<String, Object> requestBody) {

		/*
		 * Request body example:
		 * {
		 *    "depotId": 1,
		 *    "name": "New Folder",
		 *    "parentFolderId": 2 // if it's a subfolde, if the folder will be created directly on the SRC or WEB, this should be null or absent
		 * }
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

	@GetMapping("/me")
    public ResponseEntity<UserLoggedDTO> getUserInfo(@AuthenticationPrincipal AccountDetails acc) {
			// UserDetails user = UserDetailsService.loadUserByUsername(principal.getName());
			// if(authorizationHeader==null) {
			// 	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No token provided");
			// }
			// String Token=authorizationHeader.substring(7);
			// String email=jwtService.extractEmail(Token);
			// UserLoggedDTO user=accountService.loadInfo(account.getEmail());
			// if(user==null){
			// 	logger.error("User not found");
			// 	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
			// }

		Account account=acc.getAccount();
		UserLoggedDTO dto=new UserLoggedDTO();
        dto.setRole(account.getRole());
        dto.setEmail(account.getEmail());
        dto.setUser(account.getUser());

		
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);

    }


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

}
