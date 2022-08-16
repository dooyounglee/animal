package com.doo.controller;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.doo.persistence.MemberRepository;
import com.doo.security.SecurityUser;
import com.doo.vo.Member;

import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/member")
public class MemberController {

	//@Autowired
	//private PasswordEncoder pe;
	private PasswordEncoder pe = new BCryptPasswordEncoder();
	
	@Autowired
	private MemberRepository mr;
	
	@GetMapping("/login")
	public String loginGet() {
		Object temp=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(!temp.equals("anonymousUser")) {
			log.info(""+((SecurityUser)temp).getMember());
			return "redirect:/";
		}
		return null;
	}
	
	@GetMapping("/join")
	public void joinGet() {
		
	}
	
	@Transactional
	@PostMapping("/join")
	public String joinPost(Member member) {
		String encPw=pe.encode(member.getPw());
		member.setPw(encPw);
		mr.save(member);
		return "redirect:/member/login";
	}
	
	@PostMapping("/logout")
	public String logoutPost() {
		return "redirect:/";
	}
}
