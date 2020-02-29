package com.doo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.doo.persistence.MemberRepository;

import lombok.extern.java.Log;

@Service
@Log
public class UsersService implements UserDetailsService {

	@Autowired
	MemberRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SecurityUser eee=repo.findById(username)
				.filter(m -> m != null)
				.map(m -> new SecurityUser(m)).get();
		return
			repo.findById(username)
			.filter(m -> m != null)
			.map(m -> new SecurityUser(m)).get();
		
	}

}
