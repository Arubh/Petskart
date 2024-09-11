package com.example.demo.models;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.GenerationType;

@Entity
public class FormData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = false,nullable = false,updatable = true)
    private String name;
    @Column(unique = false,nullable = false,updatable = false)
    private String email;
    @Column(unique = false,nullable = false,updatable = true)
    private String subject;
    @Column(unique = false,nullable = false,updatable = false)
    private String message;
    
    private Boolean TicketResolved= false;
    public Boolean getTicketResolved() {
		return TicketResolved;
	}

	public void setTicketResolved(Boolean ticketResolved) {
		TicketResolved = ticketResolved;
	}

	public String getAttended() {
		return attended;
	}

	public void setAttended(String attended) {
		this.attended = attended;
	}

	public Boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	private String attended="NO";
    private Boolean activeStatus= true;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;

    // Constructors, getters, and setters

    public FormData() {
    }

    public FormData(String name, String email, String subject, String message) {
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    // Getters and setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}