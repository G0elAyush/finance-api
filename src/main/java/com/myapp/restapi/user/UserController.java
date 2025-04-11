package com.myapp.restapi.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myapp.restapi.core.PaginatedResponse;
import com.myapp.restapi.core.ServiceExecutor;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name="User Resource")
public class UserController implements UserApi{

	
	private static String serviceClass ;

	@Autowired
	private ServiceExecutor serviceExecutor;
	
	public UserController() {
		super();
		serviceClass = UserService.class.getCanonicalName();
	}
	
	@Override
	public ResponseEntity<UserDTO> createUser(String lang,UserDTO body) throws Throwable {
		Object[] methodArgs = { body };
		UserDTO savedUser = (UserDTO)serviceExecutor.executeService(serviceClass, "createUser", methodArgs);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{username}")
						.buildAndExpand(savedUser.getUserName())
						.toUri();
		return ResponseEntity
				.created(location)
				.build();
	}
	
	@Override
	public ResponseEntity<ArrayList<UserDTO>> createUsersWithListInput(String lang,ArrayList<UserDTO> body) throws Throwable {
	
		Object[] methodArgs = { body };
		@SuppressWarnings("unchecked")
		ArrayList<UserDTO> savedUserLst = (ArrayList<UserDTO>)serviceExecutor.executeService(serviceClass, "createUsersWithListInput", methodArgs);
		
		// Create a list of location headers for each newly created item
		List<String> locations = savedUserLst.stream()
            .map(savedUser -> ServletUriComponentsBuilder.fromCurrentContextPath().toUriString().concat("/user/").concat(savedUser.getUserName()))
            .collect(Collectors.toList());
		
		 HttpHeaders headers = new HttpHeaders();
	        for (String location : locations) {
	            headers.add("Location", location);
	            
	        }

		
		return new ResponseEntity<>(headers,HttpStatus.CREATED);
	}
	
	@Override
	public EntityModel<UserDTO> findByUserName(String lang, String username) throws Throwable {
		Object[] methodArgs = { username };
		EntityModel<UserDTO> entityModel = EntityModel.of((UserDTO)serviceExecutor.executeService(serviceClass, "findByUserName", methodArgs));
		entityModel.add(linkTo(methodOn(this.getClass()).findByUserName( lang ,username)).withSelfRel());
		entityModel.add(linkTo(methodOn(this.getClass()).deleteUserByUserName( lang ,username)).withRel("delete"));
		return entityModel;
	}
	
	@Override
	public ResponseEntity<Void> deleteUserByUserName(String lang,String username) throws Throwable {
		Object[] methodArgs = { username };
		serviceExecutor.executeService(serviceClass, "deleteUserByUserName", methodArgs);
		return ResponseEntity.noContent().build();
	}


	@Override
	public EntityModel<PaginatedResponse<UserDTO>> getAllUsers(String lang,@Valid Integer page, @Valid Integer size, @Valid String sortBy, @Valid String sortOrder) throws Throwable{
		Object[] methodArgs = { page,size,sortBy,sortOrder };
		
		@SuppressWarnings("unchecked")
		PaginatedResponse<UserDTO> userLst = (PaginatedResponse<UserDTO>)serviceExecutor.executeService(serviceClass, "getAllUsers", methodArgs);
		
		EntityModel<PaginatedResponse<UserDTO>> entityModel = EntityModel.of(userLst);
		
		
		// Add HATEOAS links to each user
		userLst.getItems().forEach(
				user -> {
					try {
						user.add(linkTo(methodOn(this.getClass()).findByUserName(lang,user.getUserName())).withRel("view")) ;
						user.add(linkTo(methodOn(this.getClass()).deleteUserByUserName(lang,user.getUserName())).withRel("delete")) ;
					} catch (Throwable e) {
						e.printStackTrace();
					}
					
					
				} );
		
		// Adding links to navigate through pagination
		entityModel.add(linkTo(methodOn(this.getClass()).getAllUsers( lang ,page , size, sortBy, sortOrder)).withSelfRel());
		if(userLst.getTotalPages()>0 && userLst.isHasNext())
		entityModel.add(linkTo(methodOn(this.getClass()).getAllUsers(lang,userLst.getTotalPages()-1, size, sortBy, sortOrder)).withRel("lastPage"));
		if(userLst.isHasNext())
		entityModel.add(linkTo(methodOn(this.getClass()).getAllUsers(lang,page + 1, size, sortBy, sortOrder)).withRel("nextPage"));
		if (userLst.isHasPrevious()) {
		entityModel.add(linkTo(methodOn(this.getClass()).getAllUsers(lang,page - 1, size, sortBy, sortOrder)).withRel("previousPage"));
		entityModel.add(linkTo(methodOn(this.getClass()).getAllUsers(lang,0, size, sortBy, sortOrder)).withRel("firstPage"));
		}
		
		return entityModel;
	}


	
	
	

	
	
	
	
	

	
	
	
	
	
	
	
	
}
