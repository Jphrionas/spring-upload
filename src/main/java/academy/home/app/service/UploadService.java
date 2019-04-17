package academy.home.app.service;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import academy.home.app.config.properties.MyProperties;

@Service
public class UploadService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MyProperties myProperties;
	
	public String uploadImage(MultipartFile file) {
		String imageId = generateImageId(file.getOriginalFilename());
		if(imageId != null) {
			try {
				
				/*
				byte[] bytes = file.getBytes();
				Files.write(pathComplete, bytes);
				*/
				Files.copy(file.getInputStream() , resolvePath(imageId).toAbsolutePath(), StandardCopyOption.REPLACE_EXISTING);
				
				return imageId;
			} catch (IOException e) {
				throw new RuntimeException("Não foi possível enviar o arquivo!", e);
			}
		}
		
		return null;
	}
	
	public Resource getResourceFrom(String filename) {
		Path pathAboslute = this.resolvePath(filename).toAbsolutePath();
		
		try {
			Resource resource = new UrlResource(pathAboslute.toUri());
			if(resource.isReadable() && resource.exists()) {
				return resource;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("Resource is not found!");
		}
		return null;
	}
	
	public void deleteImage(String imageName) {
		
			
		if(imageName != null && !imageName.isEmpty() && imageName.length() > 0 ) {
			Path path = resolvePath(imageName);
			
			if(Files.isReadable(path.toAbsolutePath()) && Files.exists(path.toAbsolutePath())) {
				try {
					Files.deleteIfExists(path.toAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private Path resolvePath(String filename) {
		return Paths.get(this.myProperties.getUpload().getAbsolutePath()).resolve(filename);
	}
	
	private String generateImageId(String originalFilename) {
		String fileExtension = originalFilename.substring(originalFilename.length() - 4);
		
		boolean isImage = Arrays
					.stream(new String[]  {".jpg", ".png", ".jpeg"})
					.anyMatch(extension -> extension.equalsIgnoreCase(fileExtension));
		
		if(isImage) {
			return  UUID.randomUUID().toString() + fileExtension;
		}
		
		return "";
	
	}
}
