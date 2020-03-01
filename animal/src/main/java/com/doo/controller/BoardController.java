package com.doo.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.doo.persistence.BoardRepository;
import com.doo.persistence.ReplyRepository;
import com.doo.security.SecurityUser;
import com.doo.vo.Board;
import com.doo.vo.Member;
import com.doo.vo.Reply;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardRepository br;
	
	@Autowired
	private ReplyRepository rr;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	@GetMapping("/list")
	public void list(Model model) {
		List<Board> list=new ArrayList<Board>();
		br.findAllBoardInListPage().forEach(e->{
			Board b=((Board)e[0]);
			Member m=((Member)e[1]);
			
			b.setSignYN(m!=null?"Y":"N");
			b.setNickname(m!=null?m.getNickname():b.getWriter());
			b.setRcount((Long)e[2]);
			list.add(b);
		});
		model.addAttribute("list", list);
		model.addAttribute("today",sdf.format(new Date()));
	}
	
	@GetMapping("/write")
	public void writeGet() {
		
	}
	
	@PostMapping("/write")
	public String writePost(Board b) {
		Board tempb=b;
		b.setIpAddress(getIpAddress());//ip는 매번수정?
		if(b.getB_no()!=null) {//수정
			b=getBoard(b.getB_no());
			b.setTitle(tempb.getTitle());
			b.setContent(tempb.getContent());
			br.save(b);
			return "redirect:/board/get?b_no="+b.getB_no();
		}else {//새글
			br.save(b);
			return "redirect:/board/list";
		}
        
	}
	
	@GetMapping("/get")
	public void get(Board b,Model model) {
		//Board
		Board getb=getBoard(b.getB_no());
		//작성자가 아니면 조회수1올리기
		Object temp=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//비로그인이거나 작성자가 아니면
		if(temp.equals("anonymousUser") || !( (((SecurityUser)temp).getMember()).getEmail().equals(getb.getWriter())) ) {
			getb.setViewCount(getb.getViewCount()+1);
			br.save(getb);
		}
		model.addAttribute("b", getb);
		
		//Reply
		List<Reply> rlist=new ArrayList<>();
		rr.getReplyOfBoard(b).forEach( e->{
			Reply r=(Reply)e[0];
			Member m=(Member)e[1];
			
			r.setNickname(m!=null?m.getNickname():r.getReplyer());
			r.setSignYN(m!=null?"Y":"N");
			rlist.add(r);
		});
		model.addAttribute("rlist", rlist);

		model.addAttribute("today",sdf.format(new Date()));
	}
	
	@GetMapping("/edit")
	public String editGet(Board b,Model model) {
		model.addAttribute("b", getBoard(b.getB_no()));
		return "board/write";
	}
	
	@PostMapping("/del")
	public String delPost(Board b) {
		Board getb=getBoard(b.getB_no());
		getb.setDelYN("Y");
		br.save(getb);
		return "redirect:/board/list";
	}
	
	@PostMapping("/pw")
	public ResponseEntity<Board> getPw(@RequestBody Board b){
		return new ResponseEntity<>(getBoard(b.getB_no()),HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping("/upload")
	public ResponseEntity<Void> upload(MultipartHttpServletRequest req,HttpServletResponse res) {
		System.out.println("들어왔고");
		MultipartFile file=req.getFile("uploadFile");
		String savePath=req.getSession().getServletContext().getRealPath("resources");
		//String savePath=req.getSession().getServletContext().getRealPath("resources/static");
		//		.replace("\\webapp\\","\\");//D:\git\animal\animal\src\main\resources\static
		String filePath=savePath+"\\upload\\"+file.getOriginalFilename();
		System.out.println(filePath);
		try {
			file.transferTo(new File(filePath));
			res.getWriter().println(file.getOriginalFilename());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	Board b=null;
	public Board getBoard(Long b_no) {
		//br.findById(b_no).ifPresent(board-> b=board);
		br.findBoardInGetPage(b_no).forEach(e->{
			b=((Board)e[0]);
			Member m=((Member)e[1]);
			
			b.setSignYN(m!=null?"Y":"N");
			b.setNickname(m!=null?m.getNickname():b.getWriter());
		});
		return b;
	}
	
	public String getIpAddress() {
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = req.getRemoteAddr();
        return ip;
	}
}
