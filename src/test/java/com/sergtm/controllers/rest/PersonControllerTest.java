package com.sergtm.controllers.rest;

import com.sergtm.controllers.rest.request.PersonRequest;
import com.sergtm.entities.Person;
import com.sergtm.repository.PersonRepository;
import com.sergtm.service.IHeartRateService;
import com.sergtm.service.IOccasionService;
import com.sergtm.service.IStaffMemberService;
import com.sergtm.service.IWeightService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonControllerTest extends AbstractRestControllerTest {
    private static final String GET_ALL_PERSONS_URL = "/persons";
    private static final String CREATE_PERSON_URL = "/persons/create";
    private static final Long PERSON_ID = 1L;
    private static final String PERSON_FIRST_NAME = "firstName";
    private static final String PERSON_SECOND_NAME = "secondName";
    private static final String USER_NAME = "admin";
    private static final String DELETE_PERSON_URL = String.format("/persons/delete/%s", PERSON_ID);
    private static final String GET_PERSONS_BY_USER_NAME_URL = String.format("/persons/%s", USER_NAME);

    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private IStaffMemberService staffMemberService;
    @MockBean
    private IWeightService weightService;
    @MockBean
    private IHeartRateService heartRateService;
    @MockBean
    private IOccasionService occasionService;

    @Mock
    private Person person;

    @Test
    public void shouldReturnAllPersons() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_ALL_PERSONS_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnPersonsByUserName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_PERSONS_BY_USER_NAME_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void shouldCreatePerson() throws Exception {
        PersonRequest request = PersonRequest.builder()
                .firstName(PERSON_FIRST_NAME)
                .secondName(PERSON_SECOND_NAME)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                    .put(CREATE_PERSON_URL)
                    .queryParams(convertRequestToMultiValueMap(request))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldDeletePerson() throws Exception {
        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.of(person));
        when(person.getId()).thenReturn(PERSON_ID);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(DELETE_PERSON_URL)
                )
                .andDo(print())
                .andExpect(status().isOk());

        verify(personRepository).deleteByPerson(PERSON_ID);
        verify(staffMemberService).deleteByPerson(person);
        verify(weightService).deleteByPerson(person);
        verify(heartRateService).deleteByPerson(person);
        verify(occasionService).deleteByPerson(person);
    }
}