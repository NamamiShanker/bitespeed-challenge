package com.bitespeed.test.model;

import java.sql.Timestamp;

import com.bitespeed.test.enums.LinkPrecedence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "contact")
@SuppressWarnings("unused")
@Getter
@ToString
@NoArgsConstructor
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int                        id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_id", referencedColumnName = "id")
    private Contact linkedContact;

    @Enumerated(EnumType.STRING)
    @Column(name = "linked_precedence")
    private LinkPrecedence linkPrecedence;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Contact(String phoneNumber, String email){
        // Contructor will assume we are creating a new record
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.linkedContact = null;
        this.linkPrecedence = LinkPrecedence.PRIMARY;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
        this.deletedAt = null;

    }

    public Contact(String phoneNumber, String email, Contact linkedContact){

        this.phoneNumber = phoneNumber;
        this.email = email;
        this.linkedContact = linkedContact;
        this.linkPrecedence = LinkPrecedence.SECONDARY;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
        this.deletedAt = null;
    }

    public void setLinkPrecedence(LinkPrecedence linkPrecedence){
        this.linkPrecedence = linkPrecedence;
    }

    public void setLinkedContact(Contact linkedContact){
        this.linkedContact = linkedContact;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email){
        this.email = email;
    }

}
