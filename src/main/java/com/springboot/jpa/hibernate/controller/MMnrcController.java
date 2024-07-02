package com.springboot.jpa.hibernate.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.jpa.hibernate.model.Employee;
import com.springboot.jpa.hibernate.model.Image;
import com.springboot.jpa.hibernate.model.MMnrc;
import com.springboot.jpa.hibernate.service.IEmployeeService;
import com.springboot.jpa.hibernate.service.IMmnrcService;

import jakarta.validation.Valid;

@Controller
public class MMnrcController {

	private final IMmnrcService mmNrcService; // Use MmnrcService interface 

	public MMnrcController(IMmnrcService mmnrcService, IEmployeeService employeeService) {
		this.mmNrcService = mmnrcService; 
	}
 
	@GetMapping("/admin/add-mmnrc")
	public String showAddMmnrcForm(Model model) {
		model.addAttribute("mmnrc", new MMnrc());
		return "addMMnrc";
	}

	@PostMapping("/admin/add-mmnrc")
	public String addMmnrc(@ModelAttribute("mmnrc") @Valid MMnrc mmnrc, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {

			attributes.addFlashAttribute("org.springframework.validation.BindingResult.mmnrc", result);
			attributes.addFlashAttribute("mmnrc", mmnrc);
			return "addMMnrc";
		} else {

			mmNrcService.saveMMnrc(mmnrc);
			return "redirect:/get-all-mmnrcs";
		}

	}
	@GetMapping("/admin/edit-mmnrc")
	public String showEditEmployeeForm(@RequestParam("mmId") String mmId, Model model) {
 
		 MMnrc mmnrc = mmNrcService.getMMnrcByMMid(mmId);
		 model.addAttribute("mmnrc", mmnrc); 
		return "editMMnrc";
	}
	
	@PostMapping("/admin/update-mmnrc")
	public String updateMMnrc(@ModelAttribute("mmnrc") @Valid MMnrc mmnrc, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		MMnrc existingMMnrc = mmNrcService.getMMnrcByMMid(mmnrc.getMmId());
		String err = "";
		 
			if (result.hasErrors()) {
				redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mmnrc", result);
				redirectAttributes.addFlashAttribute("mmnrc", mmnrc);

				return "editMMnrc";
			}
		 else {
				  
			 mmNrcService.saveMMnrc(mmnrc);

				model.addAttribute("mmnrc", mmnrc);

				return "successfullySavedPage";
			}
           
	 
	}
	
	@GetMapping("/get-all-mmnrcs")
	public String showMmnrcList(Model model) {
		List<MMnrc> mmnrcs = mmNrcService.getAllMMnrcs();
		model.addAttribute("mmnrcList", mmnrcs);
		return "showAllMmnrc";
	}


	@GetMapping("/mmnrc-details")
	public String getMMnrcByEmployeeId(@RequestParam("mmId") String mmId, Model model) {
 
		MMnrc mmnrc = mmNrcService.getMMnrcByMMid(mmId);
		model.addAttribute("mmnrc", mmnrc); 
		return "nrcDetails";
	}
	
	
	@PostMapping("/admin/delete-mmnrcs")
	public String deleteMmnrcs(@RequestParam("mmnrcIds") List<String> mmnrcIds) {
		if (mmnrcIds.isEmpty()) {

			return "redirect:/show-all-mmnrcs";
		} else {
			for (String mmnrcId : mmnrcIds) {
				mmNrcService.deleteMMnrc(mmnrcId);
			}
		}

		return "redirect:/get-all-mmnrcs";
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

	

	@GetMapping("/upload-image")
	public String showUploadForm(Model model) {
		model.addAttribute("image", new Image());
		return "uploadImage";
	}

	@PostMapping("/upload-image")
	public String uploadImage(@RequestParam("file") MultipartFile file, Image image, Model model,
			RedirectAttributes redirectAttributes) {
		try {

			mmNrcService.saveImage(file, image.getMmId());
			model.addAttribute("image", image);
			redirectAttributes.addFlashAttribute("message", "Image uploaded successfully!");
		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "Upload failed!");
		}
		return "redirect:/upload-image";
	}
	@GetMapping("/show-image")
	public String showSearchImageForm(Model model) {
		model.addAttribute("image", new Image());
		return "showImage";
	}
	@GetMapping("/get-image")
	public String showImage(@RequestParam String mmId, Model model) {
		try {
			Image image = mmNrcService.getMMnrcImageByMMid(mmId);
			if (image != null) {
				String base64Encoded = Base64.getEncoder().encodeToString(image.getImageData());
				model.addAttribute("imageSrc", "data:image/jpeg;base64," + base64Encoded);
				model.addAttribute("image", image);
				return "showImage";
			} else {
				// If no image is found, add an attribute to display an error message
				model.addAttribute("error", "No image matches this ID: " + mmId);

				model.addAttribute("image", new Image());
				return "showImage";
			}
		} catch (Exception e) {
			// Log the exception details for debugging
			e.printStackTrace();
			// Add a generic error message attribute to the model
			model.addAttribute("error", "An error occurred while retrieving the image.");
			return "showImage";
		}
	}

}