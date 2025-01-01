package org.sid.appbackser;


import org.sid.appbackser.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(AppApplication.class, args);
	}
	@Bean
	CommandLineRunner start(AccountService accountService){
		return args -> {
			// accountService.saveRole(new AppRole(null,"USER"));
			// accountService.saveRole(new AppRole(null,"ADMIN"));
			// accountService.saveRole(new AppRole(null,"CUSTOMER_MANAGER"));
			// accountService.saveRole(new AppRole(null,"PRODUCT_MANAGER"));
			// accountService.saveRole(new AppRole(null,"BILLS_MANAGER"));
			// accountService.saveRole(new AppRole(null,"CUSTOMER_MANAGER"));
			// accountService.saveRole(new AppRole(null,"SUPPLIER_MANAGER"));
			// accountService.saveRole(new AppRole(null,"STOCK_MANAGER"));
			// accountService.saveRole(new AppRole(null,"SALES_MANAGER"));
		};

}
}