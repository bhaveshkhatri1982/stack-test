package com.stack.operations.rest;

import java.util.EmptyStackException;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stack.operations.model.PeekStatus;
import com.stack.utility.Status;


@Controller
@RequestMapping("/stack")
public class StackController 
{
	static final Logger logger = LoggerFactory.getLogger(com.stack.operations.rest.StackController.class);
	
	@Autowired
	private HttpSession session;
	
	
	@RequestMapping(value="/getAuthenticatedUser", method=RequestMethod.GET)
	public @ResponseBody Status getAuthenticatedUser()
	{
		boolean isLoggedIn = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (!(auth instanceof AnonymousAuthenticationToken)) 
		{
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			if(userDetail != null && !userDetail.getUsername().equalsIgnoreCase(""))
			{
				isLoggedIn = true;
			}
		}
		
		if(isLoggedIn)
			return new Status(isLoggedIn,"logged In");
		else
			return new Status(isLoggedIn,"Not logged In");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/display", method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Stack<Integer> display(HttpSession session)
	{ 
		Stack<Integer> stack = (Stack<Integer>)session.getAttribute("currentStack");
		if(stack == null)
		{
			stack = new Stack<Integer>();
		}	
		session.setAttribute("currentStack", stack);
	
		return stack;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/push/{number}", method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Stack<Integer> push(@PathVariable int number, HttpSession session)
	{
		Stack<Integer> stack = (Stack<Integer>)session.getAttribute("currentStack");
		stack.push(number);
		return stack;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/pop", method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Stack<Integer> pop(HttpSession session)
	{
		Stack<Integer> stack = (Stack<Integer>)session.getAttribute("currentStack");
		try
		{
			stack.pop();
		}
		catch (EmptyStackException e) 
		{
			logger.info("trying to pop a number from emtpy stack");
		}
		return stack;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/peek", method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PeekStatus peek(HttpSession session)
	{
		Stack<Integer> stack = (Stack<Integer>)session.getAttribute("currentStack");
		int number = 0;
		String message = "";
		try
		{
			number = stack.peek();
		}
		catch (EmptyStackException e) 
		{
			logger.info("trying to peek a number from emtpy stack");
			number = 0;
			message = "Empty";
		}
		return new PeekStatus(number,message);
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public void logout (HttpServletRequest request, HttpServletResponse response) 
	{
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null)
	    {    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	}
}
