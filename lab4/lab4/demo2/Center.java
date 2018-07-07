package demo2;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
/**
 ��������
 
 */
public class Center extends Thread {  
    private BlockingQueue<Waiter> waiters;  
    private BlockingQueue<Customer> customers;  
  
   
    private final static int PRODUCERSLEEPSEED = 3000;  
    private final static int CONSUMERSLEEPSEED = 100000;  
  
    public Center(int num) {  
    	//�����ṩ����Ĺ�̨���к�ȡ�úŵĿͻ�����
    	waiters = new LinkedBlockingQueue<Waiter>(num);
    	for(int i=0;i<num;i++){
    		Waiter waiter = new Waiter();
    		try {
				waiters.put(waiter);
			} catch (InterruptedException e) {}
    	}
    	customers = new LinkedBlockingQueue<Customer>();
    	
    }  
       //ȡ�Ż������º���
    public void produce() {  
    	Customer newCustomer = new Customer();
        System.out.println(newCustomer.toString()+"�Ź˿����ڵȴ�����");
        try {
        	customers.put(newCustomer);
			sleep(PRODUCERSLEEPSEED/10);
		} catch (InterruptedException e1) {
		}
       
    }  
  //�ͻ���÷�����ע���̰߳�ȫ��ʵ��
    public void consume() {  
        try {
			Waiter takeWaiter = waiters.take();
			Customer newCustomer = customers.take();
			System.out.println("��"+newCustomer.toString()+"�Ź˿͵�"+takeWaiter.toString()+"�Ŵ���");
			System.out.println(takeWaiter.toString()+"�Ŵ�������Ϊ"+newCustomer.toString()+"�Ź˿Ͱ���ҵ��");
			sleep(CONSUMERSLEEPSEED/10);
			waiters.put(takeWaiter);
		} catch (InterruptedException e) {}
        
    }  
} 