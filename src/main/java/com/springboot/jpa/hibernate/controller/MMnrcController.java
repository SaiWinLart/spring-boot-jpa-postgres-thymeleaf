package com.springboot.jpa.hibernate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.jpa.hibernate.model.MMnrc;
import com.springboot.jpa.hibernate.service.IMmnrcService;

import jakarta.validation.Valid;

@Controller
public class MMnrcController {

	private final IMmnrcService mmNrcService; // Use MmnrcService interface

	public MMnrcController(IMmnrcService mmnrcService) {
		this.mmNrcService = mmnrcService;
	}

	@GetMapping("/mmnrcList")
	public String viewHomePage(Model model) {

		return "index";
	}

	@GetMapping("/add-mmnrc")
	public String showAddMmnrcForm(Model model) {
		model.addAttribute("mmnrc", new MMnrc());
		return "addMMnrc";
	}

	@PostMapping("/add-mmnrc")
	public String addMmnrc(@ModelAttribute("mmnrc") @Valid MMnrc mmnrc, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {

			attributes.addFlashAttribute("org.springframework.validation.BindingResult.mmnrc", result);
			attributes.addFlashAttribute("mmnrc", mmnrc);
			return "addMMnrc";
		} else {

			mmNrcService.saveMMnrc(mmnrc);
			return "redirect:/get-all-employees";
		}

	}

	@GetMapping("/show-all-mmnrcs")
	public String showMmnrcList(Model model) {
		List<MMnrc> mmnrcs = mmNrcService.getAllMMnrcs();
		model.addAttribute("mmnrcs", mmnrcs);
		return "showAllMmnrcs";
	}

	@PostMapping("/delete-mmnrcs")
	public String deleteMmnrcs(@RequestParam("mmnrcIds") List<String> mmnrcIds) {
		if (mmnrcIds.isEmpty()) {

			return "redirect:/show-all-mmnrcs";
		} else {
			for (String mmnrcId : mmnrcIds) {
				mmNrcService.deleteMMnrc(mmnrcId);
			}
		}

		return "redirect:/show-all-mmnrcs";
	}

	@GetMapping("/checkId/{mmid}")
	public ResponseEntity<Map<String, Boolean>> checkIdExists(@PathVariable String mmid) {
		// add '/' back, in client we remove '/'
		String newId = mmid.replaceAll("(\\d)([a-zA-Z])", "$1/$2");
		boolean exists = mmNrcService.mmIdExist(newId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("exists", exists);
		return ResponseEntity.ok(response);
	}

}