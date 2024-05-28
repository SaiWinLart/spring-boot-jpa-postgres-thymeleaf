package com.springboot.jpa.hibernate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.jpa.hibernate.model.MMnrc;
import com.springboot.jpa.hibernate.respository.INrcRepository;

@Service
public class NrcServiceImpl implements INrcService {

	private final INrcRepository nrcRepository;

	public NrcServiceImpl(INrcRepository nrcRepository) {
		this.nrcRepository = nrcRepository;
	}

	@Override
	public String saveMMnrc(MMnrc MMnrc) {
		  nrcRepository.save(MMnrc);
		  return "Data of : "+ MMnrc.getName() + " succefully save in database.";
	}

	@Override
	public MMnrc getMMnrcById(Long id) {

		MMnrc MMnrcData = nrcRepository.getReferenceById(id);

		if (MMnrcData != null) {
			return MMnrcData;
		} else {
			return null;
		}
	}

	@Override
	public List<MMnrc> getAllMMnrcs() {

		return nrcRepository.findAll();
	}

	@Override
	public String updateMMnrc(MMnrc nrc, Long id) {
		if (nrcRepository.existsById(id)) {
			nrc.setId(id);
			nrcRepository.save(nrc);
			return "Data of :  " + nrc.getName() + " is updated.";
		} else {
			return id + " not exist";
		}

	}

	@Override
	public String deleteMMnrc(Long id) { 
		if (nrcRepository.existsById(id)) {
			nrcRepository.delete(nrcRepository.getReferenceById(id));
			return id + " deleted.";
		} else {
			return id + " not exist";
		}

	}

	@Override
	public String deleteAllMMnrcs() {
		if (nrcRepository.count() > 0) {
			nrcRepository.deleteAll();
			return "All data deleted!";
		} else {
			return "Table is empty, no data is deleted. ";
		}

	}

}
