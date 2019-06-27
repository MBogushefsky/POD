package com.bogaware.entities;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
 
import org.hibernate.annotations.OptimisticLockType;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, optimisticLock = OptimisticLockType.ALL)
@Table(name = "UserAccount", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID"),
        @UniqueConstraint(columnNames = "Username"),
        @UniqueConstraint(columnNames = "Email"),
        @UniqueConstraint(columnNames = "PhoneNumber") })
public class UserAccountEntity implements Serializable {
	
	private static final long serialVersionUID = -1798070786993154676L;
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int userAccountId;
	 
    @Column(name = "Username", unique = true, nullable = false, length = 100)
    private String username;
 
    @Column(name = "Password", unique = false, nullable = false, length = 100)
    private String password;
    
    @Column(name = "FirstName", unique = false, nullable = false, length = 100)
	private String firstName;
    
    @Column(name = "LastName", unique = false, nullable = false, length = 100)
	private String lastName;
	 
    @Column(name = "Email", unique = true, nullable = false, length = 100)
	private String email;
    
    @Column(name = "PhoneNumber", unique = true, nullable = false, length = 100)
	private String phoneNumber;
	
	public void setUsername(String user) {
		this.username = user;
	}
	
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
 
    
    
}
