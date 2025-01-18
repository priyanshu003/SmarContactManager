package com.scm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.scm.repository", "com.scm.models" , "com.scm.services" , "com.scm.controllers" , "com.scm.utils","com.scm.helper","com.scm.config"})
@EnableJpaRepositories(basePackages = "com.scm.repository")
@EntityScan(basePackages = "com.scm.models")
public class SmartContactManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartContactManagerApplication.class, args);
	}
	// @Bean
    // public ApplicationRunner inspectContext(ApplicationContext context) {
    //     return args -> {
    //         System.out.println("Beans in the context:");
    //         Arrays.stream(context.getBeanDefinitionNames())
    //               .filter(bean -> bean.contains("security"))
    //               .forEach(System.out::println);
    //     };
    // }

}
