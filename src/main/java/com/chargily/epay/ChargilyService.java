package com.chargily.epay;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

import javax.validation.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ChargilyService {

    private final String URL = "https://epay.chargily.com.dz/api/invoice";

    //to sent htttp requests
    private final RestTemplate restTemplate = new RestTemplate();

    // to format object into json
    private final ObjectMapper objectMapper = new ObjectMapper();


    //validation
    void validateInvoice(Invoice invoice) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public ResponseEntity<String> createPayment( Invoice invoice , String APIKey) {

        //validate invoice if it's invalid throw constraint violation exception
        validateInvoice(invoice);
        //create headers
        HttpHeaders httpHeaders = new HttpHeaders();
        //set content-type header
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
       httpHeaders.add("X-Authorization",APIKey);

        try {
            //map invoice object into a JSON
            String invoiceJson = objectMapper.writeValueAsString(invoice);
            //create the request
            HttpEntity<String> entity = new HttpEntity<String>(invoiceJson,httpHeaders);
             //send the request and return the response
         return this.restTemplate.postForEntity(URL, entity, String.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
