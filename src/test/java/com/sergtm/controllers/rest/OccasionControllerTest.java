package com.sergtm.controllers.rest;

import com.sergtm.OccasionLevel;
import com.sergtm.controllers.rest.request.OccasionRequest;
import com.sergtm.entities.Occasion;
import com.sergtm.entities.Person;
import com.sergtm.repository.OccasionRepository;
import com.sergtm.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OccasionController.class)
public class OccasionControllerTest extends AbstractRestControllerTest {
    private static final Long OCCASION_ID = 1L;
    private static final Long PERSON_ID = 1L;
    private static final String GET_ALL_OCCASIONS_URL = "/occasions";
    private static final String DELETE_OCCASION_URL = String.format("/occasions/%s", OCCASION_ID);
    private static final String CREATE_OCCASION_URL = String.format("/occasions/%s", PERSON_ID);

    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private OccasionRepository occasionRepository;

    @Mock
    private Person person;

    @Test
    void shouldReturnAllOccasions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_ALL_OCCASIONS_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void shouldCreateOccasion() throws Exception {
        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.of(person));
        when(person.getId()).thenReturn(PERSON_ID);

        OccasionRequest request = OccasionRequest.builder()
                .id(OCCASION_ID)
                .convulsion(false)
                .occasionLevel(OccasionLevel.LOW)
                .occasionDate(LocalDateTime.now())
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .put(CREATE_OCCASION_URL)
                        .queryParams(convertRequestToMultiValueMap(request))
                )
                .andDo(print())
                .andExpect(status().isCreated());

        verify(occasionRepository).save(any(Occasion.class));
    }

    @Test
    @Transactional
    void shouldDeleteOccasion() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(DELETE_OCCASION_URL)
                )
                .andDo(print())
                .andExpect(status().isOk());

        verify(occasionRepository).deleteById(OCCASION_ID);
    }

}
