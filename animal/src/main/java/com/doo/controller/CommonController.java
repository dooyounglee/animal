package com.doo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.java.Log;

@Controller
@Log
public class CommonController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
}
