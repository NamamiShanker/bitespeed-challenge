package com.bitespeed.test.service.helperService;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bitespeed.test.enums.LinkPrecedence;
import com.bitespeed.test.model.Contact;
import com.bitespeed.test.repository.ContactRepository;

@Service(value = "identityHelperService")
public class IdentityHelperService {
    
    @Autowired
    ContactRepository contactRepository;

    public static final Logger LOGGER = LoggerFactory.getLogger(IdentityHelperService.class);

    public Contact findParentByChildContact(Contact contact){
        LOGGER.info("Find parent for contact: {}", contact);
        if(Objects.isNull(contact)) return null;
        while(contact.getLinkPrecedence() != LinkPrecedence.PRIMARY){
            contact = contact.getLinkedContact();
        }
        LOGGER.info("Found parent contact: {}", contact);
        return contact;
    }

    public List<Contact> getSecondaryContactsForParent(Contact parentContact) {
        LOGGER.info("Fetching list of secondary contacts for contact: {}", parentContact);
        Integer linkedId = parentContact.getId();
        return contactRepository.findSecondaryContactsForLinkedId(linkedId);
    }

    @Transactional
    public Contact updateHeirarchyAndGetParent(Contact parent1, Contact parent2){
        LOGGER.info("Update heirarchy of parent contacts:\n {} \n{}", parent1, parent2);
        if(parent1.getCreatedAt().compareTo(parent2.getCreatedAt()) > 0) {
            LOGGER.info("Parent contacts swapped");
            Contact temp = parent1;
            parent1 = parent2;
            parent2 = temp;
        }
        parent2.setLinkPrecedence(LinkPrecedence.SECONDARY);
        parent2.setLinkedContact(parent1);
        contactRepository.save(parent2);
        contactRepository.updateParentContact(parent2.getId(), parent1.getId());
        LOGGER.info("New parent2 Contact: {}", parent2);
        return parent1;
    }

    @Transactional
    public Contact insertContactByEmailAndGetParent(String email) {
        LOGGER.info("Inserting contact for Email: {}", email);
        Contact contact = contactRepository.findFirstByEmail(email).orElse(null);
        Contact parentContact;
        if(Objects.nonNull(contact)){
            parentContact = findParentByChildContact(contact);
        } else {
            parentContact = contactRepository.save(new Contact(null, email));
        }
        LOGGER.info("Found parent contact: {}", parentContact);
        return parentContact;
    }

    @Transactional
    public Contact insertContactByPhoneNumberAndGetParent(String phoneNumber) {
        LOGGER.info("Inserting contact for Phone Number: {}", phoneNumber);
        Contact contact = contactRepository.findFirstByPhoneNumber(phoneNumber).orElse(null);
        Contact parentContact;
        if(Objects.nonNull(contact)){
            parentContact = findParentByChildContact(contact);
        } else {
            parentContact = contactRepository.save(new Contact(phoneNumber, null));
        }
        LOGGER.info("Found parent contact: {}", parentContact);
        return parentContact;
    }

    @Transactional
    public Contact insertFullContactAndGetParent(String email, String phoneNumber) {
        LOGGER.info("Inserting full contact for email: {}, phone number: {}", email, phoneNumber);
        List<Contact> emailContacts = contactRepository.findByEmail(email);
        List<Contact> phoneNumberContacts = contactRepository.findByPhoneNumber(phoneNumber);

        LOGGER.debug("Email contacts found: {}", emailContacts);
        LOGGER.debug("Phone number contacts found: {}", phoneNumberContacts);

        if(emailContacts.size() != 0 && phoneNumberContacts.size() == 0){
            // Search if any of the emailContacts has empty phone Number
            Contact emptyPhoneContact = null;
            Contact parentContact = findParentByChildContact(emailContacts.get(0));
            LOGGER.debug("Found parent contact: {}", parentContact);
            for(Contact emailContact: emailContacts){
                if(StringUtils.isBlank(emailContact.getPhoneNumber())){
                    emptyPhoneContact = emailContact;
                    break;
                }
            }
            LOGGER.debug("Empty phone contact: {}", emptyPhoneContact);
            if(Objects.nonNull(emptyPhoneContact)){
                emptyPhoneContact.setPhoneNumber(phoneNumber);
                contactRepository.save(emptyPhoneContact);
            } else {
                contactRepository.save(new Contact(phoneNumber, email, parentContact));
            }
            return parentContact;
        }
        else if(emailContacts.size() == 0 && phoneNumberContacts.size() != 0){

            Contact emptyEmailContact = null;
            Contact parentContact = findParentByChildContact(phoneNumberContacts.get(0));
            LOGGER.debug("Found parent contact: {}", parentContact);
            for(Contact phoneNumberContact: phoneNumberContacts){
                if(StringUtils.isBlank(phoneNumberContact.getEmail())){
                    emptyEmailContact = phoneNumberContact;
                    break;
                }
            }
            LOGGER.debug("Empty Email contact: {}", emptyEmailContact);
            if(Objects.nonNull(emptyEmailContact)){
                emptyEmailContact.setEmail(email);
                contactRepository.save(emptyEmailContact);
            } else {
                contactRepository.save(new Contact(phoneNumber, email, parentContact));
            }
            return parentContact;
        }
        else if(emailContacts.size() != 0 && phoneNumberContacts.size() != 0){
            Contact emailParent = findParentByChildContact(emailContacts.get(0));
            Contact phoneNumberParent = findParentByChildContact(phoneNumberContacts.get(0));

            LOGGER.debug("Found email parent: {}", emailParent);
            LOGGER.debug("Found phone number parent: {}", phoneNumberParent);
            if(emailParent.getId() != phoneNumberParent.getId()){
                return updateHeirarchyAndGetParent(emailParent, phoneNumberParent);
            } else {
                return emailParent;
            }
        }
        else {
            return contactRepository.save(new Contact(phoneNumber, email));
        }
    }
}
