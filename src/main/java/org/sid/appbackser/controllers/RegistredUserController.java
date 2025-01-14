package org.sid.appbackser.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.sid.appbackser.dto.ChatGroupDTO;
import org.sid.appbackser.dto.DashboardRUserDTO;
import org.sid.appbackser.dto.MessageDTO;
import org.sid.appbackser.dto.ProjectDTO;
import org.sid.appbackser.dto.UserLoggedDTO;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.User;
import org.sid.appbackser.entities.Project;
import org.sid.appbackser.entities.Proposition;
import org.sid.appbackser.entities.RessourceFolder.Depot;
import org.sid.appbackser.entities.RessourceFolder.File_;
import org.sid.appbackser.entities.RessourceFolder.Folder;
import org.sid.appbackser.services.AccountDetails;
import org.sid.appbackser.services.AccountService;
import org.sid.appbackser.services.ChatGroupService;
import org.sid.appbackser.services.DashboardService;
import org.sid.appbackser.services.DepotService;
import org.sid.appbackser.services.File_Service;
import org.sid.appbackser.services.FolderService;
import org.sid.appbackser.services.MessageService;
import org.sid.appbackser.services.ProjectService;
import org.sid.appbackser.services.PropositionService;
import org.sid.appbackser.services.UserService;
import org.sid.appbackser.services.implementations.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/registred-user")
public class RegistredUserController {


	private static final Logger logger = LoggerFactory.getLogger(RegistredUserController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private PropositionService propositionService;

	@Autowired
	private ChatGroupService chatGroupService;

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ProjectService	projectService;

	@Autowired
	private DepotService depotService;

	@Autowired
	private FolderService folderService;

	@Autowired
	private File_Service fileService;

	@Autowired
	private UserService	userService;

	@Autowired
	private DashboardService dashboardService;

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

	@GetMapping("/chat/group/messages/{chatGroupId}")
    public ResponseEntity<List<MessageDTO>> getMessagesForChatGroup(@PathVariable String chatGroupId) {
        try {
            List<MessageDTO> messages = messageService.getMessagesForChatGroup(chatGroupId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Return 500 Internal Server Error in case of exceptions
        }
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
			String dobString = (String) requestBody.get("dob"); // dob as String
			String profession = (String) requestBody.get("profession");

			Date dob = null;
			if (dobString != null && !dobString.isEmpty()) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Ensure this matches the frontend format
				java.util.Date utilDate = dateFormat.parse(dobString); // Parse String to Date
				dob = new java.sql.Date(utilDate.getTime()); // Convert java.util.Date to java.sql.Date
			}

			User updatedUser = userService.updateUser(id, firstName, lastName, phone, dob, profession);
			return ResponseEntity.ok(updatedUser);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user: " + e.getMessage());
		}
	}

	//Dashboard infos
	    @GetMapping("/dashboard/infos")
    public ResponseEntity<?> getDashboardRUsersInfos(@AuthenticationPrincipal Account authUser) {
		/*
		 * Response will look like:
		 * 
		 * {
		 *   "projectsHeCreated": 5,
		 *   "projectsMemberOn": 3
		 * }
		 */
        try {
            if (authUser == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED) // HTTP 401 Unauthorized
                        .body("User not authenticated");
            }

            DashboardRUserDTO dashboardRUserInfos = dashboardService.getDashboardRUsersInfos(authUser);
            return ResponseEntity.ok(dashboardRUserInfos); // HTTP 200 OK
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // HTTP 500 Internal Server Error
                    .body("Error fetching user dashboard data: " + ex.getMessage());
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
    public ResponseEntity<List<ProjectDTO>> getProjects(@RequestBody List<Integer> projectIds) {
        List<ProjectDTO> projects = projectService.getProjectsByIds(projectIds);
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

	@PutMapping("/project/{projectId}/newmember/{newMemberEmail}")
	public ResponseEntity<String> addMemberToProject(
			@PathVariable Integer projectId,
			@PathVariable String newMemberEmail, 
			@AuthenticationPrincipal AccountDetails authAcc) {
		try {
			Account auth = authAcc.getAccount();
			projectService.addMemberToProject(projectId, auth.getId(), newMemberEmail);
			return ResponseEntity.ok("Member added successfully to the project.");
		} catch (RuntimeException e) {
			if (e.getMessage().contains("Only project admins can add members")) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
			} else if (e.getMessage().contains("Project not found") || e.getMessage().contains("Account not found")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			} else if (e.getMessage().contains("User is already a member of the project")) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
			}
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/project/{projectId}/remove/{memberEmail}")
	public ResponseEntity<String> removeMemberFromProject(
			@PathVariable Integer projectId,
			@PathVariable String memberEmail, 
			@AuthenticationPrincipal AccountDetails authAcc) {
		try {
			Account auth = authAcc.getAccount();
			projectService.removeMemberFromProject(projectId, auth.getId(), memberEmail);
			return ResponseEntity.ok("Member removed successfully from the project.");
		} catch (RuntimeException e) {
			if (e.getMessage().contains("Only project admins can remove members")) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
			} else if (e.getMessage().contains("Project not found") || e.getMessage().contains("Account not found")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			} else if (e.getMessage().contains("User is not a member of the project")) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
			}
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PutMapping("/project/{projectId}/promote/{memberId}")
	public ResponseEntity<String> promoteMemberToAdmin(
			@PathVariable Integer projectId,
			@PathVariable Integer memberId, @AuthenticationPrincipal AccountDetails authAcc) {
		try {
			Account auth = authAcc.getAccount();
			projectService.promoteMemberToAdmin(projectId, auth.getId(), memberId);
			return ResponseEntity.ok("Member promoted to admin successfully.");
		} catch (RuntimeException e) {
			if (e.getMessage().contains("Only project admins can promote members to admins")) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
			} else if (e.getMessage().contains("Project not found") || e.getMessage().contains("The user is not a member of the project")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			} else if (e.getMessage().contains("The user is already an admin of the project")) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
			}
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/project/{projectId}/demote/{adminId}")
	public ResponseEntity<String> demoteAdminToMember(
			@PathVariable Integer projectId,
			@PathVariable Integer adminId, 
			@AuthenticationPrincipal AccountDetails authAcc) {
		try {
			Account auth = authAcc.getAccount();
			projectService.demoteAdminToMember(projectId, auth.getId(), adminId);
			return ResponseEntity.ok("Admin demoted to member successfully.");
		} catch (RuntimeException e) {
			if (e.getMessage().contains("Only project admins can demote other admins")) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
			} else if (e.getMessage().contains("Project not found") || e.getMessage().contains("The user is not an admin of the project")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			} else if (e.getMessage().contains("The user is not an admin of the project")) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
			}
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/project/{projectId}/groups")
	public ResponseEntity<List<Map<String, Object>>> getProjectGroupsWithMembers(@PathVariable Integer projectId) {
		try {
			List<Map<String, Object>> groupsWithMembers = projectService.getProjectGroupsWithMembers(projectId);
			return ResponseEntity.ok(groupsWithMembers); // Return groups with members
		} catch (RuntimeException e) {
			// Handle case where project was not found or other errors
			if (e.getMessage().contains("Project not found")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if project not found
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Return 500 for other errors
		}
	}

	/*
	 * projects depot section ----------------------------------------------------------------
	 */
	
	 @PostMapping("/project/depot/create-folder")
	 public ResponseEntity<?> createFolder(@RequestBody Map<String, Object> requestBody, @AuthenticationPrincipal AccountDetails authAcc) {
		 /*
		  * Request body example:
		  * {
		  *    "depotId": 1,
		  *    "name": "New Folder",
		  *    "parentFolderId": 2 // if it's a subfolder, if the folder will be created directly on the SRC or WEB, this should be null or absent
		  * }
		  */
	 
		 Integer depotId = (Integer) requestBody.get("depotId");
		 String name = (String) requestBody.get("name");
		 Integer parentFolderId = requestBody.get("parentFolderId") != null ? (Integer) requestBody.get("parentFolderId") : null;
		Account auth = authAcc.getAccount();
	 
		 logger.info("Creating folder with name: {}, depotId: {}, parentFolderId: {}", name, depotId, parentFolderId);
	 
		 try {
			 Folder folder = folderService.createFolder(depotId, name, parentFolderId, auth.getId());
			 return ResponseEntity.status(HttpStatus.CREATED).body(folder);
		 } catch (IllegalArgumentException ex) {
			 // Return a 400 Bad Request with the error message when the folder already exists
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Folder already exists at path: " + ex.getMessage());
		 }
	 }
	 

	@DeleteMapping("/project/{projectId}/folder/delete/{folderId}")
	public ResponseEntity<Void> deleteFolder(@PathVariable Integer folderId,@PathVariable Integer projectId, @AuthenticationPrincipal AccountDetails authAcc) {
		Project project = projectService.getProjectById(projectId);
		folderService.deleteFolder(project ,folderId, authAcc.getAccount().getId());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
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

	@PostMapping("/project/file/create")
	public ResponseEntity<?> createFile(@RequestBody Map<String, Object> requestBody, 
										@AuthenticationPrincipal AccountDetails authAcc) {
		/*
		 * {
		 *	"fileName": "example.py",      // Name of the file including the extension
		 *	"folderId": 1,                  // ID of the folder where the file will be stored
		 *	"fileType": "text/x-python"     // MIME type to indicate this is a Python file
		 * }
		 */
		try {
			// Extract values from the request body
			String fileName = (String) requestBody.get("fileName");
			Integer folderId = (Integer) requestBody.get("folderId");
			String fileType = (String) requestBody.get("fileType");
	
			// Get the ownerId from the authenticated account
			Integer ownerId = authAcc.getAccount().getId();
	
			// Call the service method to create the file
			File_ createdFile = fileService.createFile(fileName, folderId, ownerId, fileType);
	
			// Return the created file metadata in the response with HTTP Status Created (201)
			return ResponseEntity.status(HttpStatus.CREATED).body(createdFile);
		} catch (Exception e) {
			// Handle any errors or exceptions (e.g., file already exists or folder not found)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								.body("Error creating file: " + e.getMessage());
		}
	}


	@PostMapping(value = "/project/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, 
                                        @RequestParam("folderId") Integer folderId, 
                                        @AuthenticationPrincipal AccountDetails authAcc) {
        try {
            // Get the ownerId from the authenticated account
            Integer ownerId = authAcc.getAccount().getId();

            // Extract the file metadata
            String fileName = file.getOriginalFilename();
            String fileType = file.getContentType();  // Identify file type from the uploaded file

            // Call the service method to save the file and return its metadata
            File_ uploadedFile = fileService.uploadFile(fileName, folderId, ownerId, fileType, file);

            // Return the uploaded file metadata in the response with HTTP Status Created (201)
            return ResponseEntity.status(HttpStatus.CREATED).body(uploadedFile);
        } catch (Exception e) {
            // Handle any errors or exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error uploading file: " + e.getMessage());
        }
    }

	@GetMapping("/project/file/download/{fileId}")
    public ResponseEntity<?> downloadFile(@PathVariable Integer fileId) throws IOException {
        try {
            // Fetch the file from the service
            File file = fileService.downloadFile(fileId);

            // Create the headers for file download
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");

            // Read the file as a byte array
            Path filePath = Paths.get(file.getAbsolutePath());
            byte[] fileBytes = Files.readAllBytes(filePath);

            // Return the file as a response
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileBytes.length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileBytes);

		} catch (IOException e) {
            // Handle IOException and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error reading file: " + e.getMessage());
        }catch (RuntimeException e) {
            // Handle exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error downloading file: " + e.getMessage());
        }
    }

	@DeleteMapping("/project/file/delete/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable Integer fileId) {
        try {
            // Call the service to delete the file
            fileService.deleteFile(fileId);

            // Return a success response
            return ResponseEntity.ok("File deleted successfully");
        } catch (RuntimeException e) {
            // Handle errors and return appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting file: " + e.getMessage());
        }
    }

}
