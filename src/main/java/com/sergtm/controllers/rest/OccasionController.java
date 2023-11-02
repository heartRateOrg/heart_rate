package com.sergtm.controllers.rest;

import com.sergtm.controllers.rest.request.OccasionRequest;
import com.sergtm.service.IOccasionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/occasions")
public class OccasionController {
	@Resource
	private IOccasionService occasionService;

	@GetMapping
	public List<OccasionRequest> occasions() {
		return occasionService.findOccasions();
	}

	@PutMapping("/{personId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void occasion(@PathVariable Long personId, @Valid OccasionRequest occasionRequest) {
		occasionService.addOccasion(personId, occasionRequest);
	}

	@DeleteMapping("/{occasionId}")
	public void occasion(@PathVariable Long occasionId) {
		occasionService.removeOccasion(occasionId);
	}
}
