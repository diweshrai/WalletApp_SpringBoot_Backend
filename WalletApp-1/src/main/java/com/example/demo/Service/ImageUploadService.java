package com.example.demo.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Documents;

public interface ImageUploadService {

	public String imgUpload(String path, MultipartFile file) throws IOException;

	public String documentUpload(String path, MultipartFile file, int Cid, String type)
			throws IOException;

	public List<Documents> getAllDoc(int cid);
	
	public String makeDocStatusInactive(int cid, String type, String status);

	
	public ResponseEntity<FileSystemResource> getTheImage(String PathOfImage , String sendImagePAth );
	
	
	
	
}
