package com.sergtm.service;

import com.sergtm.controllers.rest.request.OccasionRequest;

import java.util.List;

public interface IOccasionService extends IDeletableByPersonService {
	void addOccasion(Long personId, OccasionRequest occasionRequest);
	void removeOccasion(Long occasionId);
	List<OccasionRequest> findOccasions();
}
