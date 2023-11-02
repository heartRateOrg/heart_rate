package com.sergtm.controllers.rest;

import com.sergtm.controllers.rest.request.WeightRequest;
import com.sergtm.entities.Person;
import com.sergtm.entities.Weight;
import com.sergtm.repository.PersonRepository;
import com.sergtm.repository.WeightRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeightController.class)
public class WeightControllerTest extends AbstractRestControllerTest {
    private static final Long WEIGHT_ID = 1L;
    private static final Long PERSON_ID = 1L;
    private static final String DELETE_WEIGHT_URL = String.format("/weight/%s", WEIGHT_ID);
    private static final String CREATE_WEIGHT_URL = String.format("/weight/%s", PERSON_ID);
    private static final String GET_ALL_WEIGHTS_URL = String.format("/weight/weights");

    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private WeightRepository weightRepository;

    @Mock
    private Person person;

    @Test
    void shouldReturnAllWeights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_ALL_WEIGHTS_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void shouldCreateWeight() throws Exception {
        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.of(person));
        when(person.getId()).thenReturn(PERSON_ID);

        WeightRequest request = WeightRequest.builder()
                .id(WEIGHT_ID)
                .weight(BigDecimal.ONE)
                .date(LocalDate.now())
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .put(CREATE_WEIGHT_URL)
                        .queryParams(convertRequestToMultiValueMap(request))
                )
                .andDo(print())
                .andExpect(status().isCreated());

        verify(weightRepository).save(any(Weight.class));
    }

    @Test
    @Transactional
    void shouldDeleteWeight() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(DELETE_WEIGHT_URL)
                )
                .andDo(print())
                .andExpect(status().isOk());

        verify(weightRepository).deleteById(WEIGHT_ID);
    }
}
