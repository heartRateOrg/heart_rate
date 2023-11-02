package com.sergtm.service.impl;

import com.sergtm.controllers.rest.request.WeightRequest;
import com.sergtm.entities.Person;
import com.sergtm.entities.Weight;
import com.sergtm.repository.WeightRepository;
import com.sergtm.service.IPersonService;
import com.sergtm.service.IWeightService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeightServiceImplTest {
	private static final Long PERSON_ID = 1L;

	private static final LocalDate WEIGHT_LOCAL_DATE = LocalDate.of(2021, 12, 23);
	private static final Date WEIGHT_DATE = Date.from(WEIGHT_LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant());

	private static final BigDecimal WEIGHT = BigDecimal.valueOf(123.12);

	@Mock
	private WeightRepository weightRepository;
	@Mock
	private IPersonService personService;

	@Mock
	private Person person;
	@Captor
	private ArgumentCaptor<Weight> weightCaptor;

	@InjectMocks
	private IWeightService testedInstance = new WeightServiceImpl();

	@Before
	public void setUp() {
		when(personService.findByIdOrThrowException(PERSON_ID)).thenReturn(person);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenWeightIsNull() {
		testedInstance.addWeight(PERSON_ID, null);
	}

	@Test
	public void shouldPopulateAndSaveWeight() {
		testedInstance.addWeight(PERSON_ID, createWeightDto());

		verify(weightRepository).save(weightCaptor.capture());
		Weight weight = weightCaptor.getValue();

		assertEquals(weight.getPerson(), person);
		assertEquals(weight.getDate(), WEIGHT_DATE);
		assertEquals(weight.getWeight(), WEIGHT);
	}

	private WeightRequest createWeightDto() {
		WeightRequest weightDto = new WeightRequest();

		weightDto.setDate(WEIGHT_LOCAL_DATE);
		weightDto.setWeight(WEIGHT);

		return weightDto;
	}
}
