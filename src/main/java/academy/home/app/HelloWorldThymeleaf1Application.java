package academy.home.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import academy.home.app.config.properties.MyProperties;

@SpringBootApplication
@EnableConfigurationProperties(value= {MyProperties.class})
public class HelloWorldThymeleaf1Application {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldThymeleaf1Application.class, args);
	}

}
