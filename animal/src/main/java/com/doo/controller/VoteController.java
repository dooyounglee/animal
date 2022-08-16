package com.doo.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.doo.persistence.VoteAnswerRepository;
import com.doo.persistence.VoteOpinionRepository;
import com.doo.persistence.VoteRepository;
import com.doo.security.SecurityUser;
import com.doo.vo.Member;
import com.doo.vo.VoteOpinion;
import com.doo.vo.Vote;
import com.doo.vo.VoteAnswer;

@Controller
@RequestMapping("/vote")
public class VoteController {

	@Autowired
	private VoteRepository vr;
	
	@Autowired
	private VoteOpinionRepository vor;
	
	@Autowired
	private VoteAnswerRepository var;
	
	private Vote v;
	
	@GetMapping("/list")
	public void list(Model model) {
		List<Vote> vlist=new ArrayList<Vote>();
		vr.findAll().forEach(e->vlist.add(e));
		
		model.addAttribute("vlist", vlist);
	}
	
	@GetMapping("/write")
	public void writeGet(Vote v) {
		
	}
	
	@PostMapping("/write")
	public void writePost(Vote v) {
		Member m=null;
		Object temp=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(!temp.equals("anonymousUser")) {
			m=((SecurityUser)temp).getMember();
			v.setEmail(m.getEmail());
		}else {
			v.setEmail("비로그인");
		}
		
		vr.save(v);
	}
	
	@GetMapping("/get")
	public void get(Vote v,Model model) {
		//v=vr.findById(v.getVno()).get();
		//Object[] ob=vr.getVoteWithCnt(v.getVno());
		//for(int i=0;i<ob.length;i++) {
		//	System.out.println(ob[i].toString());
		//	
		//}
		//model.addAttribute("v", );
	}
	
	@PostMapping("/submit")
	public String submitPost(Vote v,Long ono) {
		//v=vr.findById(v.getVno()).get();
		//VoteOpinion o=or.findById(ono).get();
		
		Object temp=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Member m=((SecurityUser)temp).getMember();
		
		VoteAnswer va=new VoteAnswer();
		va.setEmail(m.getEmail());
		va.setOno(ono);
		va.setVno(v.getVno());
		
		try {
			var.save(va);
		} catch (Exception e) {
			System.out.println("error");
		}
		
		return "redirect:/vote/get?vno="+v.getVno();
	}
}
