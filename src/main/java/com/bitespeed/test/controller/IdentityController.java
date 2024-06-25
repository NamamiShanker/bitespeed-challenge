package com.bitespeed.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitespeed.test.model.Contact;
import com.bitespeed.test.object.request.IdentityQueryRequest;
import com.bitespeed.test.object.response.IdentityQueryResponse;
import com.bitespeed.test.service.IdentityService;
import com.fasterxml.jackson.annotation.JsonInclude;

@RestController(value = "identityController")
@RequestMapping("/identity")
public class IdentityController {

    @Autowired
    IdentityService identityService;

    public static final Logger LOGGER = LoggerFactory.getLogger(IdentityController.class);

    @PostMapping(value = "/")
    @JsonInclude()
    public ResponseEntity<IdentityQueryResponse> getIdentity(@RequestBody IdentityQueryRequest request) throws Exception{
        
        LOGGER.info("Received identity fetch request: {}", request);
        LOGGER.debug("This is a debug log");
        request.validate();

        IdentityQueryResponse response = new IdentityQueryResponse();

        Contact parentContact = identityService.insertContactAndGetParent(request);
        response = identityService.getIdentityByParent(parentContact);

        return ResponseEntity.ok().body(response);
    }
    
}
