package academy.home.app;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import academy.home.app.config.properties.MyProperties;

@SpringBootApplication
@EnableConfigurationProperties(value= {MyProperties.class})
public class HelloWorldThymeleaf1Application implements CommandLineRunner {

	@Autowired
	private MyProperties myProperties;
	
	public static void main(String[] args) {
		SpringApplication.run(HelloWorldThymeleaf1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Path path = Paths.get(myProperties.getUpload().getAbsolutePath());		
		if(!Files.exists(path.toAbsolutePath())) {
			System.out.println("Criando diretorio");
			Files.createDirectories(path);
		}
	}

}
