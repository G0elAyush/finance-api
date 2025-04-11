package com.myapp.restapi.user;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myapp.restapi.core.PaginatedResponse;
import com.myapp.restapi.exception.BusinessException;
import com.myapp.restapi.exception.EntityExistsException;
import com.myapp.restapi.exception.EntityNotFoundException;
import com.myapp.restapi.exception.ErrorCodes;
import com.myapp.restapi.util.calendar.AppCalendar;

import jakarta.transaction.Transactional;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	public UserDTO createUser(UserDTO userDTO) {
		
		if(checkUserforCreation(userDTO.getUserName())) {
			
			User user = convertToEntity(userDTO);
			user.setStatus(1);
			user.setCreation_date(new Date());
			user.setLast_modified_date(user.getCreation_date());
			userRepo.save(user);
			return userDTO;
		}
		throw new EntityExistsException("Exception.user.exist","user","userName",userDTO.getUserName());
		
	}
	
	@Transactional
	public ArrayList<UserDTO> createUsersWithListInput(ArrayList<UserDTO> userDTOLst) {
		
		userDTOLst.forEach(userDTO -> createUser(userDTO));
		return userDTOLst;
	}
	
	public void deleteUserByUserName(String userName) throws BusinessException{
		User user = userRepo.findByUserName(userName);
		if (user == null) {
			throw new EntityNotFoundException("Exception.user.notExist","user","userName",userName);
		}
		if (user.getStatus() == 2) {
			throw new BusinessException("USER_ALREADY_DELETED_STATE",String.valueOf(ErrorCodes.USER_ALREADY_DELETED_STATE),userName);
		}
		user.setStatus(2);
		user.setLast_modified_date(new Date());
		userRepo.save(user);
	}
	
	
	public UserDTO findByUserName(String userName) {
		
		User user = userRepo.findByUserName(userName);
        if (user == null) {
            throw new EntityNotFoundException("Exception.user.notExist","user","userName",userName);
        }
        
        return convertToDTO(user);
	}

	private Boolean checkUserforCreation(String userName) {
		User user = userRepo.findByUserName(userName);
		
		//if user is deleted in DB then return false as new user can be created
		if (user == null || user.getStatus() == 2) {
			return true;
		}
		return false;
	}
	
	
	// Method to get paginated and sorted users
    public PaginatedResponse<UserDTO> getAllUsers(Integer page, Integer size, String sortBy, String sortOrder) {
    	
        Sort sort = Sort.by(Sort.Order.asc(sortBy));  // Default ascending order
        
        if ("desc".equalsIgnoreCase(sortOrder)) {
            sort = Sort.by(Sort.Order.desc(sortBy));  // Switch to descending order
        }
        
        Pageable pageable = PageRequest.of(page, size, sort);  // Apply sort and pagination
        
        Page<User> allUsers = userRepo.findAll(pageable);
        
        List<UserDTO> userDTOList = new ArrayList<UserDTO>();
        
        allUsers.getContent().stream()
        .map(user -> {
        	userDTOList.add(convertToDTO(user));
            return user;
        }).collect(Collectors.toList());
        
        return new PaginatedResponse<UserDTO>(userDTOList,allUsers.hasPrevious(),allUsers.hasNext(), allUsers.getTotalPages(),allUsers.getTotalElements()); // Fetch paginated and sorted users
    }
	

	/*
	
	public UserDTO updateUseByUserName(String userName, User userDetails) {
		getUserByUserName(userName);
		//user.setUserName(userDetails.getUserName());
		//user.setFirstName(userDetails.getFirstName());
		//user.setLastName(userDetails.getLastName());
		user.setEmail(userDetails.getEmail());
		return userRepo.save(user);
	}*/
	
	private UserDTO convertToDTO(User user) {
	        // logic to convert User to UserDTO
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail(user.getEmail());
		userDTO.setUserName(user.getUserName());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setPhone(user.getPhone());
		userDTO.setStatus(user.getStatus());
		userDTO.setPassword(user.getPassword());
		userDTO.setId(user.getId());
		userDTO.setCreation_date(AppCalendar.getDefaultTimeZoneTime(user.getCreation_date()));
		userDTO.setLast_modified_date(AppCalendar.getDefaultTimeZoneTime(user.getLast_modified_date()));
		return userDTO;
		
	}

	private User convertToEntity(UserDTO userDTO) {
	        // logic to convert UserDTO to User
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setUserName(userDTO.getUserName());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setPhone(userDTO.getPhone());
		user.setPassword(userDTO.getPassword());
		user.setStatus(userDTO.getStatus());
		return user;
	}

	
	
	

	

}
