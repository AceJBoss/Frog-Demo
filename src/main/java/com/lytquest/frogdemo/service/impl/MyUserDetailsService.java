package com.lytquest.frogdemo.service.impl;

import com.lytquest.frogdemo.entity.User;
import com.lytquest.frogdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService{
	private UserRepository userRepository;
//	String ROLE_PREFIX = "ROLE_";
	public MyUserDetailsService(UserRepository userRepository){
		this.userRepository = userRepository;
	}

	 @Override
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	     Optional<User> user = userRepository.findByUsername(username);
		 return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
				 mapRolesToAuthorities(user.get().getRole().getRoleName().toString()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String role) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority(role));
		return list;
	}
}
