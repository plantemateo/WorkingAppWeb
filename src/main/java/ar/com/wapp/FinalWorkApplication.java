package ar.com.wapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })

public class FinalWorkApplication extends SpringBootServletInitializer implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FinalWorkApplication.class, args);
	}

	@Autowired
	private DefaultData defaultData;

	@Override
	public void run(String... args) throws Exception {
		defaultData.ensureAllRoles();
		defaultData.ensureUserIntegration();
		defaultData.ensureUserGetToken();

	}

}
