package com.emarzate.service.impl;

import org.springframework.stereotype.Service;

import com.emarzate.service.SimpleService;
import com.emarzate.service.model.CustomObject;

@Service
public class SimpleServiceImpl implements SimpleService {

	
	private CustomObject customObject = new CustomObject(2);
	
	private static SimpleServiceImpl internal = null;
	
	private SimpleServiceImpl() {
		
	}
	
	public static SimpleServiceImpl getInstance(){
		
		if(internal == null){
			synchronized (SimpleServiceImpl.class) {
				internal = new SimpleServiceImpl();
			}
		}
		
		return internal;
	}
	
	
	@Override
	public int invokeService() {
		
		synchronized (customObject) {
			int count = customObject.getCount();
			customObject.setCount(0);
			return count;
		}
		
	}

}
