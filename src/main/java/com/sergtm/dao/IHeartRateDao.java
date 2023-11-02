package com.sergtm.dao;

import com.sergtm.entities.HeartRate;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Sergey on 18.07.2017.
 */
public interface IHeartRateDao {
	void addHeartRate(HeartRate heartRate);
	void deleteHeartRate(HeartRate heartRate);
	Collection<HeartRate> getByPage(int firstResult, int maxResult);
	Collection<HeartRate> findHeartRatesByDateRangeAndPerson(Date from, Date to, Long personId);

	HeartRate findById(Long id);
}
