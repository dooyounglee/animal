package com.doo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.doo.vo.Member;
import com.doo.vo.MemberRole;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Getter
@Setter
@Log
public class SecurityUser extends User {

	private static final String ROLE_PREFIX="ROLE_";
	private Member member;


	public SecurityUser(Member member) {
		//super(member.getUid(),"{noop}"+member.getUpw(),makeGrantedAuthority(member.getRoles()));
		super(member.getEmail(),member.getPw(),makeGrantedAuthority(member.getRole()));
		this.member=member;
	}

	private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles) {
		List<GrantedAuthority> list=new ArrayList<>();
		
		roles.forEach(role->list.add(new SimpleGrantedAuthority(ROLE_PREFIX+role.getRoleName())));
		return list;
	}
}
