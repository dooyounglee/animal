package com.doo.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.doo.persistence.AnimalNicknameRepository;
import com.doo.persistence.ReplyRepository;
import com.doo.security.SecurityUser;
import com.doo.vo.Animal;
import com.doo.vo.AnimalNickname;
import com.doo.vo.Board;
import com.doo.vo.Member;
import com.doo.vo.Reply;
import com.doo.vo.dto.AnimalEmail;

@Controller
@RequestMapping("/reply")
public class ReplyController {

	@Autowired
	private ReplyRepository rr;
	
	@Autowired
	private AnimalNicknameRepository anr;
	
//	@Autowired
//	private AnimalRepository ar;
	
	@PostMapping("/write")
	public String writePost(Reply r, Long b_no) {
		Board b=new Board();
		b.setB_no(b_no);
		r.setBoard(b);
		r.setIpAddress(getIpAddress());
		
		//동물이름 가져오기
		r=getAnimal(r,b_no);
		
		//댓글이면 auto_increment값 //아니면 대댓글이니까 놔둬
		if(r.getRref()==null) {
			//reply테이블 안지운다는 조건하에, 마지막rno값+1로 채움.
			//정확히는, insert하고 나온 rno값으로 다시 update해야 할듯
			r.setRref(rr.findTop1ByOrderByRnoDesc() != null ? rr.findTop1ByOrderByRnoDesc().getRno()+10 : 1);
		}
		rr.save(r);
		return "redirect:/board/get?b_no="+b_no;
	}
	
	@ResponseBody
	@Transactional
	@PostMapping("/replyAdd")
	public ResponseEntity<Void> replyAdd(@RequestBody Reply r) {
		Board b=new Board();
		b.setB_no(r.getB_no());
		r.setBoard(b);
		r.setIpAddress(getIpAddress());
		r.setReYN("Y");
		
		//동물이름 가져오기
		r=getAnimal(r,r.getB_no());

		rr.save(r);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/replyDel")
	public ResponseEntity<Void> replyDel(@RequestBody Reply r) {
		rr.findById(r.getRno()).ifPresent(e->{
			e.setDelYN("Y");
			rr.save(e);
		});
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	Reply r=null;
	@PostMapping("/pw")
	public ResponseEntity<Reply> getPw(@RequestBody Reply re){
		rr.findById(re.getRno()).ifPresent(e->{
			r=e;
		});
		return new ResponseEntity<>(r,HttpStatus.OK);
	}
	
	public Reply getAnimal(Reply r,Long b_no) {
		//로그인 상태니?
		Object temp=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(!temp.equals("anonymousUser")) {//로그인 상태면
			Member mem=((SecurityUser)temp).getMember();
			AnimalEmail ae=new AnimalEmail();
			ae.setEmail(mem.getEmail());
			ae.setB_no(b_no); //email,b_no로
			
			Optional<AnimalNickname> oan=anr.findById(ae); //animal_no찾기
			if(oan.isPresent()) {//있으면
				//oan.ifPresent(e->ar.findById(e.getAnimal_no()).ifPresent(f->r.setAnimal(f.getAnimal_name())));//찾아서 r에 담기
				oan.ifPresent(e->r.setAnimal_no(e.getAnimal_no()));
			}else {//없으면 동물 부여
				//남은 동물들
				List<Animal> remainAnimalList=anr.getRemainAnimal(b_no); //where not animal_no in () 으로 찾은거라 이거중에 아무거나 하면 됌
				int no=(int)(remainAnimalList.size()*Math.random()); //랜덤만들고

				AnimalNickname an=new AnimalNickname();
				an.setAnimal_no(remainAnimalList.get(no).getAnimal_no());
				an.setB_no(b_no);
				an.setEmail(mem.getEmail()); //숫자3개 담아서
				anr.save(an); //저장
				
				r.setAnimal_no(an.getAnimal_no());
				//r.setAnimal(remainAnimalList.get(no).getAnimal_name());
			}
			
		} else {
			r.setAnimal(null);
		}
		return r;
	}
	
	public String getIpAddress() {
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = req.getRemoteAddr();
        return ip;
	}
}
