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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.doo.persistence.BoardRepository;
import com.doo.persistence.ReplyRepository;
import com.doo.vo.Board;

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
		br.findBoardByDelYNWithReplyCountNative().forEach(b->{
			Board bo=((Board)(b[0]));
			bo.setRcount(((Long)(b[1])));
			list.add(bo);
		});
		model.addAttribute("list", list);
		model.addAttribute("today",sdf.format(new Date()));
	}
	
	@GetMapping("/write")
	public void writeGet() {
		
	}
	
	@PostMapping("/write")
	public String writePost(Board b) {
        b.setIpAddress(getIpAddress());
        
		br.save(b);
		return "redirect:/board/list";
	}
	
	@GetMapping("/get")
	public void get(Board b,Model model) {
		model.addAttribute("b", getBoard(b.getB_no()));
		model.addAttribute("rlist", rr.getReplyOfBoard(b));
		model.addAttribute("today",sdf.format(new Date()));
	}
	
	@GetMapping("/edit")
	public String editGet(Board b,Model model) {
		model.addAttribute("b", getBoard(b.getB_no()));
		return "board/write";
	}
	
	@PostMapping("/del")
	public String delPost(Board b) {
		br.deleteById(b.getB_no());
		return "redirect:/board/list";
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
		br.findById(b_no).ifPresent(board-> b=board);
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
