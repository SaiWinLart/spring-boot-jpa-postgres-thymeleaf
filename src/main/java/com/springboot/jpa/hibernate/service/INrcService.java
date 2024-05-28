package com.springboot.jpa.hibernate.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.jpa.hibernate.model.MMnrc;

@Service
public interface INrcService {
	String saveMMnrc(MMnrc mmnrc); 

	List<MMnrc> getAllMMnrcs(); 

	String updateMMnrc(MMnrc mmnrc, Long id);

	String deleteMMnrc(Long id);

	String deleteAllMMnrcs(); 

	MMnrc getMMnrcById(Long id);

	 
}  