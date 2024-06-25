package com.bitespeed.test.repository;

import com.bitespeed.test.model.Contact;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends CrudRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c WHERE c.email = :email OR c.phoneNumber = :phoneNumber")
    List<Contact> findByEmailOrPhoneNumber(@Param("email") String email, @Param("phoneNumber") String phoneNumber);

    List<Contact> findByEmail(String email);

    List<Contact> findByPhoneNumber(String phoneNumber);

    Optional<Contact> findFirstByEmail(String email);

    Optional<Contact> findFirstByPhoneNumber(String phoneNumber);

    @Query("SELECT c FROM Contact c WHERE c.email = :email AND c.linkPrecedence = 'PRIMARY'")
    Optional<Contact> findPrimaryContactByEmail(@Param("email") String email);

    @Query("SELECT c FROM Contact c WHERE c.phoneNumber = :phoneNumber AND c.linkPrecedence = 'PRIMARY'")
    Optional<Contact> findPrimaryContactByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT c FROM Contact c WHERE c.linkedContact.id = :linkedId AND c.linkPrecedence = 'SECONDARY'")
    List<Contact> findSecondaryContactsForLinkedId(@Param("linkedId") Integer linkedId);

    @Query("SELECT count(c) FROM Contact c WHERE c.linkedContact.id = :parentId")
    int getSecondaryContactsCountByParentId(@Param("parentId") Integer parentId);

    @Modifying
    @Transactional
    @Query("UPDATE Contact c SET c.linkedContact.id = :newParentId where c.linkedContact.id = :originalParentId")
    void updateParentContact(@Param("originalParentId") Integer originalParentId, @Param("newParentId") Integer newParentId);

}
