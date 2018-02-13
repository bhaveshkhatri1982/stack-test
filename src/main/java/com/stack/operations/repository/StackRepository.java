package com.stack.operations.repository;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


//This is data access layer as part of architecture.
@Repository("stackRepository")
public class StackRepository 
{
	static final Logger logger= Logger.getLogger(com.stack.operations.repository.StackRepository.class);

	@Autowired
	private SessionFactory sessionFactory;
	
}
