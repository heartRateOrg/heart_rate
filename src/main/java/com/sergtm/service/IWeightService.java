package com.sergtm.service;

import com.sergtm.controllers.rest.request.WeightRequest;

import java.util.List;

public interface IWeightService extends IDeletableByPersonService {
	void addWeight(Long personId, WeightRequest weightDto);
	void removeWeight(Long weightId);
	List<WeightRequest> findWeights();
}
