package com.sims.service;

import com.sims.dao.AppRoleDAO;
import com.sims.dao.AppUserDAO;
import com.sims.model.AppUser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOGGER = Logger.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
    private AppUserDAO appUserDAO;
	
	@Autowired
    private AppRoleDAO appRoleDAO;
	
	private UserDetails userDetails;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		AppUser appUser = this.appUserDAO.findUserAccount(username);
		try {
			LOGGER.info("user["+username+"] found.");
		} catch (Exception e) {
			LOGGER.error("Exception in loadUserByUserName(): user["+username+"] not found." );
		}
        
        
        List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());
        List<GrantedAuthority> grantList = new ArrayList<>();
        
        try {
            for (String role : roleNames) {
            	LOGGER.info("user role["+role+"] found.");
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
            userDetails = new User(appUser.getUserName(), appUser.getEncrytedPassword(), grantList);
        } catch (Exception e){
        	LOGGER.error("Exception in loadUserByUserName(): user role["+username+"] not found." );
        }
        
        return userDetails;

	}

}
