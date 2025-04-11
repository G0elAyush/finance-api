package com.myapp.restapi.user;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
//@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long>{
	
	
	
	public User findByUserName(String userName);
	
	@Modifying
	@Transactional
	public int deleteByUserName(String userName);
	

}
