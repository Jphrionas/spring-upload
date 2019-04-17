package academy.home.app.config;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import academy.home.app.config.properties.MyProperties;
import lombok.extern.slf4j.Slf4j;

@org.springframework.context.annotation.Configuration
@Slf4j
public class Configuration extends WebMvcConfigurationSupport {
	
	@Autowired
	private MyProperties myProperties;

	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		
		String filePath = Paths.get(myProperties.getUpload().getAbsolutePath()).toAbsolutePath().toUri().toString();
		log.debug("Change dinamic path to upload images {} ", filePath);
		
		registry.addResourceHandler("/upload/**")
		.addResourceLocations(filePath);
	}
}
