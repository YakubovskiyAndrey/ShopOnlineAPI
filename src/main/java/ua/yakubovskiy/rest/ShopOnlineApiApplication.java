package ua.yakubovskiy.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ShopOnlineApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopOnlineApiApplication.class, args);
	}

}
