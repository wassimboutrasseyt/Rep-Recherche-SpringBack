package org.sid.appbackser.services.implementations;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.sid.appbackser.dto.AuthentificationDTO;
import org.sid.appbackser.dto.UserLoggedDTO;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.AccountDetails;
import org.sid.appbackser.entities.Group;
import org.sid.appbackser.entities.Role;
import org.sid.appbackser.entities.User;
import org.sid.appbackser.enums.Roles;
import org.sid.appbackser.repositories.AccountRepository;
import org.sid.appbackser.repositories.RoleRepository;
import org.sid.appbackser.repositories.UserRepository;
import org.sid.appbackser.services.AccountService;
import org.sid.appbackser.services.UserService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired 
    private UserRepository userRepository;

    @Autowired
    private AccountDetailsService accountDetailService;

    @Autowired
    AuthenticationManager authManager;
    
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
    	Account account_2= accountRepository.save(account);
    	this.setRoleForAccount(account_2.getId(),Roles.REGISTRED_USER);
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
        return accountRepository.findAll(); // Add filtering logic if needed
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll(); // Retrieve all accounts
    }

    @Override
    public List<Group> getGroupsForAccount(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        return account != null ? account.getGroups() : null; // Assuming Account has a `getGroups()` method
    }

    // New method to set a role for an account
    public String setRoleForAccount(Integer accountId, Roles roleEnum) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            return "Account not found";
        }

        // Find the role by its enum value
        Role role = roleRepository.findByRole(roleEnum)
                                   .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        // Assign the role to the account
        account.setRole(role);

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
		
		logger.info("email:"+acc.getEmail());
		
		Account account=accountRepository.findByEmail(acc.getEmail());
		
		logger.info("after fetching acc user is:"+account.getUser().getFirstName());
		logger.info("agter fetching user is:"+account.getRole());
		
		UserLoggedDTO dto=new UserLoggedDTO();
		dto.setRole(account.getRole());
		dto.setEmail(account.getEmail());
		dto.setUser(account.getUser());
		
		logger.info("after assiging the role in the dto is :"+dto.getRole());
		return dto;
	}

    @Override
    public UserLoggedDTO loadInfo(String email) {
        Account acc=accountRepository.findByEmail(email);
        UserLoggedDTO dto=new UserLoggedDTO();
        dto.setRole(acc.getRole());
        dto.setEmail(acc.getEmail());
        dto.setId(acc.getId());
        dto.setUser(acc.getUser());
        return dto;
    }

    @Override
    public Account getAccountFromToken(Principal principal) {
        UserDetails user = accountDetailService.loadUserByUsername(principal.getName());
        AccountDetails accountDetails = (AccountDetails) user;
        return accountDetails.getAccount();
    }

}
