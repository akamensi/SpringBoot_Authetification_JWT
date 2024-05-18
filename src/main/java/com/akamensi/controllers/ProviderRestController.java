package com.akamensi.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.akamensi.entities.Provider;
import com.akamensi.services.ProviderService;

@RestController
@RequestMapping("/providers")
@CrossOrigin("*")
public class ProviderRestController {

	private ProviderService providerService;

	public ProviderRestController(ProviderService providerService) {
		super();
		this.providerService = providerService;
	}

	@GetMapping("/list")
	@ResponseBody
	public List<Provider> getAllProvider() {
		return (List<Provider>) providerService.getAllProvider();
	}

	@GetMapping("/{providerId}")
	@ResponseBody
	public Provider findOneProvider(@PathVariable Long providerId) {
		return providerService.findOneProvider(providerId);
	}

	/*
	 * //add sans upload image
	 * 
	 * @PostMapping("/addPost")
	 * 
	 * @ResponseBody public Provider createProvider(@Valid @RequestBody Provider
	 * provider) { return providerService.createProvider(provider); }
	 */

	// add avec upload image
	@PostMapping("/addPost")
	@ResponseBody
	public Provider create(@RequestParam(name = "imageFile") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("address") String address
	// @RequestParam("imageName") String imageName
	) throws IOException {
		return providerService.createProvider(file, name, email, address);
	}

	/*
	 * edit provider sans edit image
	 * 
	 * @PutMapping("/{providerId}")
	 * 
	 * @ResponseBody public Provider editProvider(@PathVariable Long providerId,
	 * 
	 * @Valid @RequestBody Provider providerRequest) { return
	 * providerService.editProvider(providerId, providerRequest); }
	 */

	// edit provider avec edit image "MultipartFile"
	@PutMapping("/{providerId}")
	@ResponseBody
	public Provider editProvider(@RequestParam(name="imageFile",required = false) MultipartFile file,
			@RequestParam("name") String name,
			@RequestParam("email") String email, 
			@RequestParam("address") String address,
	        @RequestParam("id") long id) throws IOException
{
	        	return providerService.update(file, name, email, address, id);
	    }



	@DeleteMapping("/{providerId}")
	@ResponseBody
	public ResponseEntity<?> deleteProvider(@PathVariable Long providerId) {
		return providerService.deleteProvider(providerId);
	}

}
