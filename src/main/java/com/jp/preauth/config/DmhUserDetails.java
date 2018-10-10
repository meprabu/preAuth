package com.jp.preauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jp.preauth.dao.UserDao;
import com.jp.preauth.entity.BCUser;

import static java.util.Collections.emptyList;

@Service
public class DmhUserDetails implements UserDetailsService{
	
	@Autowired
	UserDao userdao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		/*BCUser user = new BCUser();
		user.setWho("dmh.prabu.jayapandia");
		user.setFirst_name("Prabu");
		user.setLast_name("Jayapandian");
		user.setLocation_code("999");
		user.setPermit("999");
		user.setCsu("Y");
		user.setCl("Y");*/
		
		BCUser user = userdao.getUserById(username);
		if(user == null){
			throw new UsernameNotFoundException("user is null");
		}
		
		if(!username.equalsIgnoreCase(user.getWho())){
			throw new UsernameNotFoundException("User not found");
		}
		
		UserDetails details = new org.springframework.security.core.userdetails.User(user.getWho(),
				user.getPermit(), emptyList());
		
		return details;
	}

	
	
	
}
