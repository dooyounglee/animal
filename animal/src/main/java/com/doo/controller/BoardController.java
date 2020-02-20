package com.doo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping("/list")
	public void list(Model model) {
		List<Board> list=new ArrayList<Board>();
		br.findAll().forEach(b->list.add(b));
		model.addAttribute("list", list);
	}
	
	@GetMapping("/write")
	public void writeGet() {
		
	}
	
	@PostMapping("/write")
	public String writePost(Board b) {
		br.save(b);
		return "redirect:/board/list";
	}
	
	@GetMapping("/get")
	public void get(Board b,Model model) {
		model.addAttribute("b", getBoard(b.getB_no()));
		model.addAttribute("rlist", rr.getReplyOfBoard(b));
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
	
	Board b=null;
	public Board getBoard(Long b_no) {
		br.findById(b_no).ifPresent(board-> b=board);
		return b;
	}
}
