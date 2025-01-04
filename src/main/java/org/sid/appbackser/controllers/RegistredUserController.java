package org.sid.appbackser.controllers;

import java.util.List;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.sid.appbackser.dto.ChatGroupDTO;
import org.sid.appbackser.dto.UserLoggedDTO;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.services.AccountDetails;
import org.sid.appbackser.services.AccountService;
import org.sid.appbackser.services.ChatGroupService;
import org.sid.appbackser.services.implementations.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/registred-user")
public class RegistredUserController {


	private static final Logger logger = LoggerFactory.getLogger(RegistredUserController.class);


	@Autowired
	private JWTService jwtService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ChatGroupService chatGroupService;
	
	// @Autowired
    // public RegistredUserController(JwtService jwtService) {
    //     this.jwtService = jwtService;
    // }

	@GetMapping
	public ResponseEntity<String> hello() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("hello " + "world");
	}

	// chat section
	@GetMapping("/chat-groups")
	public ResponseEntity<List<ChatGroupDTO>> getMethodName(@AuthenticationPrincipal AccountDetails authAcc) {
		List<ChatGroupDTO> chatGroupDTOs = chatGroupService.getChatGroupsForAccount(authAcc.getAccount().getId());
		return ResponseEntity.ok(chatGroupDTOs);
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
	  
	
}
