package com.stack.common.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/")
public class CommonController 
{	
	//Rest call to display login page.
	@RequestMapping(value="login",method = RequestMethod.GET)
	public String home()
	{
		return "redirect:/pages/index.html";
	}
}
