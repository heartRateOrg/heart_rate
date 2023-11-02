package com.sergtm.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sergtm.OccasionLevel;
import com.sergtm.controllers.rest.request.OccasionRequest;
import com.sergtm.entities.Disease;
import com.sergtm.entities.Occasion;
import com.sergtm.entities.Person;
import com.sergtm.repository.DiseaseRepository;
import com.sergtm.repository.OccasionRepository;
import com.sergtm.service.IOccasionService;
import com.sergtm.service.IPersonService;

@RunWith(MockitoJUnitRunner.class)
public class OccasionServiceImplTest {
	private static final Long PERSON_ID = 1L;
	private static final String DISEASE_NAME = "epilepsy";
	private static final OccasionLevel OCCASION_LEVEL = OccasionLevel.MIDDLE;
	private static final boolean IS_CONVULSION = true;

	private static final LocalDateTime OCCASION_LOCAL_DT = LocalDateTime.of(2021, 10, 20, 17, 25);
	private static final Date OCCASION_DATE = Date.from(OCCASION_LOCAL_DT.atZone(ZoneId.systemDefault()).toInstant());

	@Mock
	private IPersonService personService;
	@Mock
	private OccasionRepository occasionRepository;
	@Mock
	private DiseaseRepository diseaseRepository;

	@Mock
	private Person person;
	@Mock
	private Disease disease;
	@Captor
	private ArgumentCaptor<Occasion> occasionCaptor;

	@InjectMocks
	private IOccasionService testedInstance = new OccasionServiceImpl();

	@Before
	public void setUp() {
		when(personService.findByIdOrThrowException(PERSON_ID)).thenReturn(person);
		when(diseaseRepository.findOneByName(DISEASE_NAME)).thenReturn(disease);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenOccasionIsNull() {
		testedInstance.addOccasion(PERSON_ID, null);
	}

	@Test
	public void shouldPopulateAndSaveOccasion() {
		testedInstance.addOccasion(PERSON_ID, createOccasionDto());

		verify(occasionRepository).save(occasionCaptor.capture());
		Occasion occasion = occasionCaptor.getValue();

		assertEquals(occasion.getPerson(), person);
		assertEquals(occasion.getOccasionLevel(), OCCASION_LEVEL);
		assertEquals(occasion.isConvulsion(), IS_CONVULSION);
		assertEquals(occasion.getDisease(), disease);
		assertEquals(occasion.getOccasionDate(), OCCASION_DATE);
	}

	private OccasionRequest createOccasionDto() {
		OccasionRequest occasionRequest = new OccasionRequest();

		occasionRequest.setOccasionLevel(OCCASION_LEVEL);
		occasionRequest.setConvulsion(IS_CONVULSION);
		occasionRequest.setOccasionDate(OCCASION_LOCAL_DT);

		return occasionRequest;
	}
}
