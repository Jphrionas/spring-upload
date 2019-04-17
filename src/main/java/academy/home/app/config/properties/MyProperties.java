package academy.home.app.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties(prefix="app")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyProperties {

	private Upload upload;
}
