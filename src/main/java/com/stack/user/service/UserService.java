package com.stack.user.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stack.user.model.User;


/**
 * @author Bhavesh
 *
 */
@Service("userDetailsService")
//@Transactional(readOnly = true)
public class UserService implements UserDetailsService
{	
	static final Logger logger= Logger.getLogger(com.stack.user.service.UserService.class);
	
	@Autowired
	User user;
	
	//@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException
	{
		// below code is for authenticating user with database. But in our stack test application I don't use database.
		/* 
		ArrayList<RolePermission> listRolePermission = new ArrayList<RolePermission>();
		try
		{ 
			//user = loginRepository.findUser(emailAddress);
			
			//Hibernate.initialize(employee.getRole());
			//System.out.println("role = "+employee.getRole());
			//com.dmt.auto.model.settings.role.Role role = employee.getRole();
			//System.out.println("role = "+role.getRoleName());
			
			
			//Hibernate.initialize(employee.getRole().getListRolePermission());
			/ *listRolePermission = new ArrayList<RolePermission>(role.getListRolePermission());
			System.out.println("listRolePermission = "+listRolePermission);
			System.out.println("listRolePermission size = "+listRolePermission.size());* /
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new UsernameNotFoundException(e.getCause().getMessage());
		}
		*/
		
		// Below code is to authenticate user with hard coded values.
		user.setName("Stack Admin");
		user.setEmailAddress("admin@admin.com");
		user.setPassword("admin");
		user.setAuthorities("ROLE_ADMIN");
		user.setIsActive("true");
		
		boolean isActive = false;
		if(user.getIsActive().equalsIgnoreCase("true") || user.getIsActive().equalsIgnoreCase("false"))
		{
			isActive = Boolean.valueOf(user.getIsActive());
		}
		String uname= user.getEmailAddress();
		String pass=user.getPassword();
		boolean enabled=isActive;
		boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
		
		Collection<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getAuthorities()));
		/*for(RolePermission rolePermission :listRolePermission)
		{
			if(rolePermission.getIsActive().equalsIgnoreCase(Constants.ACTIVE_PERMISSION))
			{
				authorities.add(new SimpleGrantedAuthority(rolePermission.getPermission().getPermissionName()));	
			}
		}*/
		org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(uname, pass, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		return securityUser;
	}
}
