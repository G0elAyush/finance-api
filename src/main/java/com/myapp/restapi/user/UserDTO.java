package com.myapp.restapi.user;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.Nulls;
import com.myapp.restapi.util.beanvalidation.IntRange;
import com.myapp.restapi.util.beanvalidation.ValidationGroups;
import com.myapp.restapi.util.beanvalidation.Views;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
@JsonPropertyOrder({ "id", "userName" ,"firstName","lastName","email","phone"})
@JsonIgnoreProperties(value = "_links", allowGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class UserDTO extends RepresentationModel<UserDTO>{
	
	@JsonView(Views.View.class)
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	@JsonSetter(nulls = Nulls.FAIL)
	private long id;
	
	
	@NotBlank //input validation 
	@Size(min=3,max=12) 
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	@JsonSetter(nulls = Nulls.FAIL)
	private String userName;
	
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
	@NotBlank
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	@JsonSetter(nulls = Nulls.FAIL)
	private String email;
	
	
	@JsonView(Views.Create.class)
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	@JsonSetter(nulls = Nulls.FAIL)
	@NotBlank
	@Size(min=3,max=12)
    private String password;
	
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	@JsonSetter(nulls = Nulls.FAIL)
	@NotBlank
	@Size(min=1,max=30)
	private String firstName;
	
	@NotBlank
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	@JsonSetter(nulls = Nulls.FAIL)
	@Size(min=1,max=30)
    private String lastName;
	
	
	@Pattern(regexp ="\\d{10}")
	@NotBlank
	@JsonSetter(nulls = Nulls.FAIL)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String phone;
	
	@JsonView(Views.View.class)
	@IntRange(min = 1, max=6, groups = ValidationGroups.Update.class)
	@JsonSetter(nulls = Nulls.FAIL)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int status;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonSetter(nulls = Nulls.FAIL)
	@JsonView(Views.View.class)
	private String creation_date;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonSetter(nulls = Nulls.FAIL)
	@JsonView(Views.View.class)
	private String last_modified_date;
	

}
