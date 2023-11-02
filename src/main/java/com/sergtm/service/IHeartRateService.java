package com.sergtm.service;

import com.sergtm.dto.StatisticOnDay;
import com.sergtm.entities.HeartRate;
import com.sergtm.entities.IEntity;
import com.sergtm.form.AddHeartRateForm;

import java.time.LocalDateTime;
import java.util.Collection;

public interface IHeartRateService extends IDeletableByPersonService {
	Collection<? extends IEntity> createHeartRate(int upperPressure, int lowerPressure, int beatsPerMinute, LocalDateTime dt, String firstName, String secondName);

	HeartRate createHeartRate(Long id, AddHeartRateForm form);
	void addHeartRateByPersonId(Long id, int upperPressure, int lowerPressure, int beatsPerMinute, LocalDateTime dt);

	boolean deleteHeartRate(Long id);

	Collection<StatisticOnDay> getChartData(Long id, String from, String to, String userName);

	Collection<? extends IEntity> findHeartRatesByDateRangeAndPerson(Long personId, LocalDateTime from, LocalDateTime to, String userName);
	Collection<? extends IEntity> findByPage(int page);
	HeartRate findById(Long id);
	Collection<? extends IEntity> getHelp(String query, String topicName);
}
