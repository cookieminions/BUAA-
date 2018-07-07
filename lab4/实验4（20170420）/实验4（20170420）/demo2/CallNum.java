package demo2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * ģ�����нк�ϵͳ
 */
public class CallNum {
	public static void main(String[] args) throws InterruptedException {  
		//������������,��һ�����е�Ӫҵ��
		Center center = new Center(10);  
        ExecutorService exec = Executors.newCachedThreadPool();  
        //ģ�����������Ա
        Producer producer = new Producer(center);  
        //ģ������ͻ�
        Consumer consumer = new Consumer(center);  
        exec.execute(producer);  
        //ģ��10���ͻ�
        for (int i = 0; i < 10; i++) {  
            exec.execute(consumer);  
        }  
       
        exec.shutdown();  
    
    }
}
