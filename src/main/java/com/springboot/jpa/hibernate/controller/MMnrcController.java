package com.springboot.jpa.hibernate.controller;

import com.springboot.jpa.hibernate.service.INrcService;

public class MMnrcController {

	private final INrcService nrcService;

	public MMnrcController(INrcService nrcService) {
		this.nrcService = nrcService;
	}

}