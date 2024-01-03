package com.example.demo.ImplService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Constant.ConstantFile;
import com.example.demo.Model.Documents;
import com.example.demo.Repo.DocumentsRepo;
import com.example.demo.Service.ImageUploadService;



@Service
public class ImageUpoadServiceImpl implements ImageUploadService {

	// For Learning, doing Simple Image Upload using Post man...
	@Override
	public String imgUpload(String path, MultipartFile file) throws IOException {

		String name = file.getOriginalFilename();

		String randomId = UUID.randomUUID().toString();

		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));

		String filePath = path + File.separator + fileName1;

		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		Files.copy(file.getInputStream(), Paths.get(filePath));

		return ConstantFile.IMG_UPLOAD_SUCCESFULLY;
	}

	@Autowired
	DocumentsRepo docRepo;

	// For Project Implementation Getting the image , customer Id , type , Status
	// from the User From Front End ...........
	@Override
	public String documentUpload(String path, MultipartFile file, int cid, String type) throws IOException {

		String FilePath = this.uploadDocumentsByMutiPart(path, file);

		int GetDocStatus = this.checkDocumentStatus(cid, type);

		if (GetDocStatus == 0) {

			String ImageUploadToDb = this.insertAllDataOFImageUploadIntoDb(cid, FilePath, type);

			return ImageUploadToDb;

		} else {
			return ConstantFile.ERROR_MESS;
		}

	}

	// Showing ALl The Uploaded Documents By User To the User .........
	@Override
	public List<Documents> getAllDoc(int cid) {

		return this.getAllTheActiveDocuments(cid);
	};

	/**
	 *  
	 * 
	 */
	@Override
	public String makeDocStatusInactive(int cid, String type, String status) {

		List<Documents> doc1 = docRepo.findByCustomerIdAndDocumentTypeAndDocStatus(cid, type, status);

		for (Documents prevDoc : doc1) {
			return this.settingTheStatuInactive(prevDoc);
		}

		return ConstantFile.ERROR_MESS;

	};

	@Override
	public ResponseEntity<FileSystemResource> getTheImage(String path , String sendImagePath) {
		try {

			//String basePath = "E:/coding/MyReact/FrontEnd/WalletApp/walletapp/src/img/";
			System.out.println("APi fired " + path);
			String fullPath = sendImagePath + path;

			File imageFile = new File(fullPath);

			
			if (!imageFile.exists()) {
				return ResponseEntity.notFound().build();
			}

			
			MediaType mediaType = getMediaType(imageFile);
			if (mediaType == null) {
				return ResponseEntity.status(500).build(); 
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mediaType);

			return ResponseEntity.ok().headers(headers).body(new FileSystemResource(imageFile));
		} catch (Exception e) {
			
			return ResponseEntity.status(500).build();
		}

	}

	private MediaType getMediaType(File file) {
		
		String fileName = file.getName().toLowerCase();
		if (fileName.endsWith(".png")) {
			return MediaType.IMAGE_PNG;
		} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
			return MediaType.IMAGE_JPEG;
		} else if (fileName.endsWith(".gif")) {
			return MediaType.IMAGE_GIF;
		}
		return null; 
	}

	
	
	
	
	
	// Helping Methods or Functions Starts from Here Which i am using in above code

	private String uploadDocumentsByMutiPart(String path, MultipartFile file) throws IOException {
		String name = file.getOriginalFilename();

		String randomId = UUID.randomUUID().toString();

		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));

		String filePath = path + File.separator + fileName1;

		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		Files.copy(file.getInputStream(), Paths.get(filePath));

		return filePath;

	};

	private String insertAllDataOFImageUploadIntoDb(int cid, String filePath, String type) {

		

		Documents dc = new Documents();

		dc.setCustomerId(cid);
		dc.setDocumentType(type);
		dc.setDocumentPath(filePath);
		dc.setDocStatus(ConstantFile.STATUS_ACTIVE);
		dc.setUploadedDate(LocalDateTime.now());

		docRepo.save(dc);

		return ConstantFile.SUCCESS;
	};

	private int checkDocumentStatus(int cid, String type) {

		

		List<Documents> result = docRepo.findByCustomerIdAndDocumentTypeAndDocStatus(cid, type, ConstantFile.STATUS_ACTIVE);

		if (!result.isEmpty()) {
			return 1;

		} else {
			return 0;
		}

	};

	private List<Documents> getAllTheActiveDocuments(int cid) {

		return docRepo.findByCustomerIdAndDocStatus(cid, ConstantFile.STATUS_ACTIVE);

	};

	private String settingTheStatuInactive(Documents docToChange) {

		Documents finalChangedDoc = docToChange;

		finalChangedDoc.setDocStatus(ConstantFile.STATUS_INACTIVE);

		docRepo.save(finalChangedDoc);

		return ConstantFile.SUCCESS;

	};

}
