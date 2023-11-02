package com.sergtm.controllers.rest;

import com.sergtm.controllers.rest.request.WeightRequest;
import com.sergtm.service.IWeightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/weight")
public class WeightController {
	@Resource
	private IWeightService weightService;

	@GetMapping("/weights")
	public List<WeightRequest> weights() {
		return weightService.findWeights();
	}

	@PutMapping("/{personId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void weight(@PathVariable Long personId, @Valid WeightRequest weightDto) {
		weightService.addWeight(personId, weightDto);
	}

	@DeleteMapping("/{weightId}")
	public void weight(@PathVariable Long weightId) {
		weightService.removeWeight(weightId);
	}
}
