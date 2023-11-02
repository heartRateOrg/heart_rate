package com.sergtm.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.sergtm.repository.HeartRateRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sergtm.dao.IHeartRateDao;
import com.sergtm.dao.IHeartRateWithWeatherDao;
import com.sergtm.dao.IHelpDao;
import com.sergtm.dao.IPersonDao;
import com.sergtm.dto.StatisticOnDay;
import com.sergtm.entities.HeartRate;
import com.sergtm.entities.HeartRateWithWeatherPressure;
import com.sergtm.entities.IEntity;
import com.sergtm.entities.Person;
import com.sergtm.entities.User;
import com.sergtm.form.AddHeartRateForm;
import com.sergtm.service.IHeartRateService;
import com.sergtm.service.IUserService;
import com.sergtm.util.DateUtils;

import javax.annotation.Resource;

@Service
@Transactional(readOnly = true)
public class HeartRateServiceImpl implements IHeartRateService {
	private static final int RECORD_COUNT_ON_PAGE = 5;
	@Autowired
	private IHeartRateDao heartRateDao;
	@Autowired
	private IHeartRateWithWeatherDao heartRateWithWeatherDao;
	@Resource
	private HeartRateRepository heartRateRepository;

	@Autowired
	private IPersonDao personDao;
	@Autowired
	private IHelpDao helpDao;
	@Autowired
	private IUserService userService;

	@Override
	@Transactional
	public Collection<? extends IEntity> createHeartRate(int upperPressure, int lowerPressure, int beatsPerMinute, LocalDateTime datetime, String firstName, String secondName) {
		List<Person> people = personDao.getPersonByName(firstName, secondName);

		if (people.size() == 1) {
			HeartRate hr = createAndSaveHeartRate(null, upperPressure, lowerPressure, beatsPerMinute, datetime, people.get(0));
			return Arrays.asList(hr);
		} else if (people.size() == 0) {
			Person person = Person.createPerson(firstName, secondName);
			personDao.savePerson(person);

			HeartRate hr = createAndSaveHeartRate(null, upperPressure, lowerPressure, beatsPerMinute, datetime, person);
			return Arrays.asList(hr);
		} else {
			return people;
		}
	}

	// Rewrite
	@Override
	@Transactional
	public HeartRate createHeartRate(Long id, AddHeartRateForm form) {
		Person person = personDao.getPersonById(form.getPersonId());
		return createAndSaveHeartRate(id, form.getUpperPressure(),
				form.getLowerPressure(), form.getBeatsPerMinute(),
				form.getDate(), person);
	}

	// Rewrite
	@Override
	@Transactional
	public void addHeartRateByPersonId(Long id, int upperPressure, int lowerPressure, int beatsPerMinute, LocalDateTime dt) {
		Instant dtInstant = dt.atZone(ZoneId.systemDefault()).toInstant();

		Person person = personDao.getPersonById(id);
		HeartRate heartRate = HeartRate.createHeartRate(upperPressure, lowerPressure, beatsPerMinute, Date.from(dtInstant), person);
		heartRateDao.addHeartRate(heartRate);
	}

	@Override
	public Collection<? extends IEntity> getHelp(String query, String topicName) {
		return helpDao.getByTopic(query, topicName);
	}

	@Override
	public Collection<? extends IEntity> findByPage(final int page) {
		int pageNumber = page;
		if (page <= 0) {
			pageNumber = 1;
		}
		int firstResult = (pageNumber * RECORD_COUNT_ON_PAGE) - RECORD_COUNT_ON_PAGE;
		return heartRateDao.getByPage(firstResult, RECORD_COUNT_ON_PAGE);
	}

	@Override
	@Transactional
	public boolean deleteHeartRate(Long id) {
		HeartRate heartRate = heartRateDao.findById(id);
		if (heartRate == null) {
			return false;
		}
		try {
			heartRateDao.deleteHeartRate(heartRate);
			return true;
		} catch (HibernateException e) {

		}
		return false;
	}

	@Override
	public Collection<? extends IEntity> findHeartRatesByDateRangeAndPerson(Long personId, LocalDateTime from, LocalDateTime to, String userName) {
		Instant fromInstant = from.with(LocalTime.of(0, 0, 0)).atZone(ZoneId.of("UTC")).toInstant();
		Instant toInstant = to.with(LocalTime.of(23, 59, 59)).atZone(ZoneId.of("UTC")).toInstant();

		return heartRateDao.findHeartRatesByDateRangeAndPerson(Date.from(fromInstant), Date.from(toInstant), personId);
	}

	@Override
	public Collection<StatisticOnDay> getChartData(Long personId, String from, String to, String userName) {
		User user = userService.findUserByUsername(userName);

		LocalDate now = LocalDate.now();
		LocalDateTime firstDayOfMonth = now.withDayOfMonth(1).atStartOfDay();
		LocalDateTime lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth()).atTime(23, 59);

		LocalDateTime fromDate = DateUtils.parseDate(from, firstDayOfMonth);
		LocalDateTime toDate = DateUtils.parseDate(to, lastDayOfMonth);

		Collection<HeartRateWithWeatherPressure> heartRateWithWeatherPressures = heartRateWithWeatherDao
				.getData(
						Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant()),
						Date.from(toDate.atZone(ZoneId.systemDefault()).toInstant()),
						personId, user);

		return heartRateWithWeatherPressures.stream().map(StatisticOnDay::new)
				.collect(Collectors.toList());
	}

	private HeartRate createAndSaveHeartRate(Long id, int upperPressure, int lowerPressure, int beatsPerMinute, LocalDateTime dt,
			Person person) {
		Instant dtInstant = dt.with(LocalTime.now()).atZone(ZoneId.of("UTC")).toInstant();

		final HeartRate hr;
		if (id == null) {
			hr = HeartRate.createHeartRate(upperPressure, lowerPressure,
					beatsPerMinute, Date.from(dtInstant), person);
		} else {
			hr = findById(id);

			hr.setUpperPressure(upperPressure);
			hr.setLowerPressure(lowerPressure);
			hr.setBeatsPerMinute(beatsPerMinute);
			hr.setDate(Date.from(dtInstant));
			hr.setPerson(person);
		}
		heartRateDao.addHeartRate(hr);

		return hr;
	}

	@Override
	public HeartRate findById(Long id) {
		return heartRateDao.findById(id);
	}

	@Override
	public void deleteByPerson(Person person) {
		heartRateRepository.deleteByPerson(person);
	}
}
