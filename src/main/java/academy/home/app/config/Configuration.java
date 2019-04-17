package academy.home.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import academy.home.app.config.properties.MyProperties;

@org.springframework.context.annotation.Configuration
public class Configuration extends WebMvcConfigurationSupport {
	
	@Autowired
	private MyProperties myProperties;

	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		
		registry.addResourceHandler("/upload/**")
		.addResourceLocations("file:" + myProperties.getUpload().getAbsolutePath() + "/");
	}
}
