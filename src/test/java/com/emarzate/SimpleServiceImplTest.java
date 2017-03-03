package com.emarzate;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.emarzate.service.SimpleService;
import com.emarzate.service.impl.SimpleServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleServiceImplTest {

	private SimpleService simpleService = SimpleServiceImpl.getInstance();
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	static HashMap<String, Integer> results = new HashMap<>();
	
	@Test
	public void testInvokeService() throws InterruptedException, BrokenBarrierException {
		for(int i = 0; i < 10; i++){
			CyclicBarrier gate = new CyclicBarrier(3);
			Thread t1 = new Thread(new Worker("1", gate));
			Thread t2 = new Thread(new Worker("2", gate));
			t1.start();
			t2.start();
			
			gate.await();
			
			while(t1.isAlive() || t2.isAlive()){
				
			}
			
			System.out.println("1: "+results.get("1"));
			System.out.println("2: "+results.get("2"));
			assertTrue(2 == results.get("1"));
			assertTrue(0 == results.get("2"));
		}
		

		
	}
	
	private class Worker implements Runnable{

		private String id;
		CyclicBarrier gate;
		
		public Worker(String id, CyclicBarrier gate){
			this.id = id;
			this.gate = gate;
		}
		
		@Override
		public void run() {
			try {
				gate.await();
				System.out.println("Execution time "+this.id+"  "+ Calendar.getInstance().getTimeInMillis() );
				int count = simpleService.invokeService();
				results.put(id, count);
			} catch (InterruptedException | BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
