package com.akinnova.BookReviewMav;

import com.akinnova.BookReviewMav.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@EnableWebMvc
@SpringBootApplication
@Slf4j
public class BookReviewMavApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookReviewMavApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				WebMvcConfigurer.super.addCorsMappings(registry);
				registry
						.addMapping("/**")
						.allowedOrigins("*")
						.allowedHeaders("*")
						.allowedMethods("POST", "GET", "PUT", "PATCH", "OPTIONS");
			}
		};
	}

//	@Bean
//	@Transactional
//	public CommandLineRunner commandLineRunner(UserRepository userRepository){
//		log.info("I am in the command line runner");
//		return args -> {
//			userRepository.findAll().stream().peek(p->{
//				p.setActiveStatus(true);
//				userRepository.save(p);
//				System.out.println("User data has been updated");
//			});
//		};
//	}

}
