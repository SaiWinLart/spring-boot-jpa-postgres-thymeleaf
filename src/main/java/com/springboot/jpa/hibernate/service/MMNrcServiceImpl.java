package com.springboot.jpa.hibernate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.springboot.jpa.hibernate.model.MMnrc;
import com.springboot.jpa.hibernate.respository.INrcRepository;

@Service
public class MMNrcServiceImpl implements IMmnrcService {

	private final INrcRepository nrcRepository;

	public MMNrcServiceImpl(INrcRepository nrcRepository) {
		this.nrcRepository = nrcRepository;
	}

	@Override
	public String saveMMnrc(MMnrc MMnrc) {
		nrcRepository.save(MMnrc);
		return "Data of : " + MMnrc.getName() + " succefully save in database.";
	}

//	@Override
//	public MMnrc getMMnrcById(String id) {
//
//		MMnrc MMnrcData = nrcRepository.getMMnrcByMMid(id)
//
//		if (MMnrcData != null) {
//			return MMnrcData;
//		} else {
//			return null;
//		}
//	}

	@Override
	public List<MMnrc> getAllMMnrcs() {

		return nrcRepository.findAll();
	}

	@Override
	public String updateMMnrc(MMnrc nrc, String id) {
		if (nrcRepository.mmIdExist(id)) {
			nrc.setMmId(id);
			nrcRepository.save(nrc);
			return "Data of :  " + nrc.getName() + " is updated.";
		} else {
			return id + " not exist";
		}

	}

	@Override
	public String deleteMMnrc(String id) {
		if (nrcRepository.mmIdExist(id)) {
			nrcRepository.delete(nrcRepository.getMMnrcByMMid(id).get());
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

	@Override
	public MMnrc getMMnrcByMMid(String mmnrcId) {

		if (StringUtils.isEmptyOrWhitespace(mmnrcId)) {
			throw new IllegalArgumentException("Name cannot be null or blank");
		}

		return nrcRepository.getMMnrcByMMid(mmnrcId).get();
	}

	@Override
	public boolean mmIdExist(String mmnrcId) {

		return nrcRepository.mmIdExist(mmnrcId);
	}

}
