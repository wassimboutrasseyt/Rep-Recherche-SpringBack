package org.sid.appbackser.services.implementations;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.sid.appbackser.dto.UserLoggedDTO;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.GroupAccount;
import org.sid.appbackser.enums.AccountStatus;
import org.sid.appbackser.enums.Roles;
import org.sid.appbackser.repositories.AccountRepository;
import org.sid.appbackser.repositories.ProjectRepository;
import org.sid.appbackser.repositories.UserRepository;
import org.sid.appbackser.services.AccountDetails;
import org.sid.appbackser.services.AccountService;
import org.sid.appbackser.services.GroupAccountService;
import org.sid.appbackser.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.sid.appbackser.repositories.RessourcePersoRepository;
import org.sid.appbackser.services.DepotService;
import org.sid.appbackser.services.FolderService;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    
    @Autowired 
    private UserRepository userRepository;

    @Autowired
    private AccountDetailsService accountDetailService;

    @Autowired
    AuthenticationManager authManager;
    
    @Autowired
    ProjectRepository projectRepository;

    @Autowired 
    JWTService JwtService;

    
	private static final Logger logger =  LoggerFactory.getLogger(UserService.class);

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(10);

    
    
    public AccountServiceImplement(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(Account account) {
    	account.setPassword(encoder.encode(account.getPassword()));
      //  account.setGroups(null);
        account.setRole(Roles.REGISTRED_USER);
        account.setStatus(AccountStatus.ACTIVE);
    	Account account_2= accountRepository.save(account);
    	//this.setRoleForAccount(account_2.getId(),Roles.REGISTRED_USER);

        return account_2;// Return saved account
    }

    @Override
    public String updateAccount(Account account) {
        accountRepository.save(account);
        return "Account updated successfully";
    }

    @Override
    public Account getAccount(Integer accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    @Override
    public String deleteAccount(Integer accountId) {
        accountRepository.deleteById(accountId);
        return "Account deleted successfully";
    }

    @Override
    public List<Account> getAccountsByUserId(Integer userId) {
        return accountRepository.findAll(); 
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll(); 
    }

    // New method to set a role for an account
    public String setRoleForAccount(Integer accountId, Roles roleEnum) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            return "Account not found";
        }

        // Find the role by its enum value
        // Role role = roleRepository.findByRole(roleEnum)
        //                            .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        // Assign the role to the account
        account.setRole(roleEnum);

        // Save the updated account
        accountRepository.save(account);
        return "Role assigned successfully";
    }

	@Override
	public String verify(Account acc) {
		Authentication auth=authManager.authenticate(new UsernamePasswordAuthenticationToken(acc.getEmail(),acc.getPassword()));
		if(auth.isAuthenticated()) return JwtService.generateToken(acc.getEmail(), acc.getId()) ;
		return "not registred";
	}

    @Override
    public UserLoggedDTO loadInfo(Account acc) {
        logger.info("Email: " + acc.getEmail());

        // Fetch the full account details
        Account account = accountRepository.findByEmail(acc.getEmail());

        if (account == null) {
            throw new RuntimeException("Account not found for email: " + acc.getEmail());
        }

        logger.info("Fetched Account's User: " + account.getUser().getFirstName());
        logger.info("Fetched Account Role: " + account.getRole());

        // Prepare the UserLoggedDTO
        UserLoggedDTO dto = new UserLoggedDTO();
        dto.setId(account.getId());
        dto.setRole(account.getRole().toString());
        dto.setEmail(account.getEmail());
        dto.setUser(account.getUser());

        // Populate group details
        List<UserLoggedDTO.GroupData> groupDataList = new ArrayList<>();
        for (GroupAccount groupAccount : account.getGroups()) {
            UserLoggedDTO.GroupData groupData = new UserLoggedDTO.GroupData();
            groupData.setRolePerGroup(groupAccount.getRole().toString());
            groupData.setProjectId(projectRepository.findByAdminGroupOrProjectGroup(groupAccount.getGroup())!=null
                    ? projectRepository.findByAdminGroupOrProjectGroup(groupAccount.getGroup()).getId()
                    : null);
            groupData.setProjectShortName(projectRepository.findByAdminGroupOrProjectGroup(groupAccount.getGroup()) != null
                    ? projectRepository.findByAdminGroupOrProjectGroup(groupAccount.getGroup()).getShortName()
                    : null);
            groupDataList.add(groupData);
        }

        // Set groups in DTO
        dto.setGroups(groupDataList);

        return dto;
    }




    @Override
    @Transactional
    public Account getAccountFromToken(Principal principal) {
        AccountDetails accountDetails = (AccountDetails) accountDetailService.loadUserByUsername(principal.getName());
        return accountDetails.getAccount();
    }

    @Override
    public List<Account> getAdmisAccount() {
        return accountRepository.findByRole(Roles.ADMIN);
      }

    @Override
    public Account getAccountByEmail(String email) {
        
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        return account;
    }

}
