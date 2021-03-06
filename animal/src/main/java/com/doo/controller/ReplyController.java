package com.doo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.doo.persistence.AnimalNicknameRepository;
import com.doo.persistence.AnimalRepository;
import com.doo.persistence.ReplyRepository;
import com.doo.security.SecurityUser;
import com.doo.vo.Animal;
import com.doo.vo.AnimalEmail;
import com.doo.vo.AnimalNickname;
import com.doo.vo.Board;
import com.doo.vo.Member;
import com.doo.vo.Reply;

import lombok.extern.java.Log;

@Controller
@RequestMapping("/reply")
public class ReplyController {

	@Autowired
	private ReplyRepository rr;
	
	@Autowired
	private AnimalNicknameRepository anr;
	
	@Autowired
	private AnimalRepository ar;
	
	@PostMapping("/write")
	public String writePost(Reply r, Long b_no) {
		Board b=new Board();
		b.setB_no(b_no);
		r.setBoard(b);
		
		//동물이름 가져오기
		//로그인 상태니?
		Object temp=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(!temp.equals("anonymousUser")) {//로그인 상태면
			Member mem=((SecurityUser)temp).getMember();
			AnimalEmail ae=new AnimalEmail();
			ae.setEmail(mem.getEmail());
			ae.setB_no(b_no); //email,b_no로
			
			Optional<AnimalNickname> oan=anr.findById(ae); //animal_no찾기
			if(oan.isPresent()) {//있으면
				oan.ifPresent(e->ar.findById(e.getAnimal_no()).ifPresent(f->r.setAnimal(f.getAnimal_name())));//찾아서 r에 담기
			}else {//없으면 동물 부여
				//남은 동물들
				List<Animal> remainAnimalList=anr.getRemainAnimal(b_no);
				int no=(int)(remainAnimalList.size()*Math.random()); //랜덤만들고

				AnimalNickname an=new AnimalNickname();
				an.setAnimal_no(remainAnimalList.get(no).getAnimal_no());
				an.setB_no(b_no);
				an.setEmail(mem.getEmail()); //숫자3개 담아서
				anr.save(an); //저장
				
				r.setAnimal(remainAnimalList.get(no).getAnimal_name());
			}
			
		} else {
			r.setAnimal("김익명");
		}
		System.out.println("리플 결과는?"+r);
		rr.save(r);
		return "redirect:/board/get?b_no="+b_no;
	}
}
