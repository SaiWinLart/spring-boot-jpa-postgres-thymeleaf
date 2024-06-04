package com.springboot.jpa.hibernate.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.jpa.hibernate.model.Image;
import com.springboot.jpa.hibernate.model.MMnrc;

@Service
public interface IMmnrcService {
	String saveMMnrc(MMnrc mmnrc); 

	List<MMnrc> getAllMMnrcs(); 
 

	String deleteMMnrc(String id);

	String deleteAllMMnrcs(); 

	//MMnrc getMMnrcById(Long id);
	
	MMnrc getMMnrcByMMid(String mmnrcId);

	boolean mmIdExist(String mmnrcId);

	String updateMMnrc(MMnrc nrc, String id);  

	Image getMMnrcImageByMMid(String mmnrcId);

	void saveImage(MultipartFile imageFile, String mmId) throws IOException;  
}  