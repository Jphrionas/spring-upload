package academy.home.app.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Upload {
	private String baseUrl;
	private String folderName;
	private String absolutePath;
}
