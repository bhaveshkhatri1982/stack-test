package com.stack.operations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stack.operations.repository.StackRepository;

//This is service layer as part of architecture.
@Service("stackService")
@Transactional(readOnly = true)
public class StackService 
{
	@Autowired
	private StackRepository stackRepositotry;
	
}
