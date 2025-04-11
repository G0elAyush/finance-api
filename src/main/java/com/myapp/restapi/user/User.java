package com.myapp.restapi.user;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "user_details",uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name","status"})})
@Data
public class User {
	
	
	@Column(nullable = false, name = "user_seq")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_generator")
	@SequenceGenerator(name="user_generator", sequenceName = "user_seq", allocationSize=1)
	private long id;
	
	
	@Column(nullable = false, name = "user_name",length = 12)
	private String userName;
	
	
	@Column(nullable = false, name = "user_email",length = 65)
	private String email;
	
	
	@Column( nullable = false,name = "user_password", length=12)
	private String password;
	
	@Column(nullable = false, name = "user_fname", length=30)
	private String firstName;
	
	
	@Column(nullable = false, name = "user_lname", length=30)
    private String lastName;
	
	
	@Column(nullable = false, name = "user_phone")
	private String phone;
	
	@Column(nullable = false, name = "user_stat")
	private int status;
	
	
	@Column(nullable = false, name = "user_crt_dt")
	private Date creation_date;
	
	@Column(nullable = false, name = "lst_mod_dt")
	private Date last_modified_date;
	

	
	/*
 @JsonIgnore
    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    
    Set<Role> roleSet;
    */

}
