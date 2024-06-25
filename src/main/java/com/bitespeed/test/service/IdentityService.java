package com.bitespeed.test.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitespeed.test.model.Contact;
import com.bitespeed.test.object.request.IdentityQueryRequest;
import com.bitespeed.test.object.response.IdentityQueryResponse;
import com.bitespeed.test.repository.ContactRepository;
import com.bitespeed.test.service.helperService.IdentityHelperService;

@Service(value = "identityService")
public class IdentityService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    IdentityHelperService helperService;

    public static final Logger LOGGER = LoggerFactory.getLogger(IdentityService.class);

    public Contact insertContactAndGetParent(IdentityQueryRequest request){
        LOGGER.info("Received request to Insert Contact and Get Parent Contact: {}", request);
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();

        if(StringUtils.isBlank(phoneNumber)){
            return helperService.insertContactByEmailAndGetParent(email);
        }
        else if(StringUtils.isBlank(email)){
            return helperService.insertContactByPhoneNumberAndGetParent(phoneNumber);
        }
        else {
            return helperService.insertFullContactAndGetParent(email, phoneNumber);
        }
    }

	public IdentityQueryResponse getIdentityByParent(Contact parentContact) {
        List<Contact> secondaryContacts = helperService.getSecondaryContactsForParent(parentContact);
        IdentityQueryResponse response = new IdentityQueryResponse();
        IdentityQueryResponse.IdentityResponse idResponse = new IdentityQueryResponse.IdentityResponse(); // Added missing semicolon
        response.setContact(idResponse);

        idResponse.setPrimaryContactId(parentContact.getId());

        Set<String> emails = new HashSet<>();
        if(StringUtils.isNotBlank(parentContact.getEmail()))
            emails.add(parentContact.getEmail());
        for(Contact sc: secondaryContacts){
            if(StringUtils.isNotBlank(sc.getEmail())) emails.add(sc.getEmail());
        }
        idResponse.setEmails(new ArrayList<>(emails));

        Set<String> phoneNumbers = new HashSet<>();
        if(StringUtils.isNotBlank(parentContact.getPhoneNumber()))
            phoneNumbers.add(parentContact.getPhoneNumber());
        for(Contact sc: secondaryContacts){
            if(StringUtils.isNotBlank(sc.getPhoneNumber())) phoneNumbers.add(sc.getPhoneNumber());
        }
        idResponse.setPhoneNumbers(new ArrayList<>(phoneNumbers));

        idResponse.setSecondaryContactIds(secondaryContacts.stream().map(Contact::getId).collect(Collectors.toList()));

        return response;
    }

}
