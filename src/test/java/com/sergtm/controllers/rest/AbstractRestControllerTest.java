package com.sergtm.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AbstractRestControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    protected <T> String writeValueAsString(T request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }

    protected <T> MultiValueMap convertRequestToMultiValueMap(T request) throws JsonProcessingException {
        Map<String, String> valuesHashMap = objectMapper.readValue(writeValueAsString(request), HashMap.class);
        Map<String, List<String>> multiValueMap = valuesHashMap.entrySet().stream().collect(
            Collectors.groupingBy(Map.Entry::getKey,
                    Collectors.mapping(e -> String.valueOf(e.getValue()), Collectors.toList()))
        );
        return new LinkedMultiValueMap<>(multiValueMap);
    }
}
