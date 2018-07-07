package ThreadDemo;

import java.io.File;  
import java.io.RandomAccessFile;  
import java.util.concurrent.CountDownLatch;  
  
public class MultiReadTest {  
  
    /** 
     * ���̶߳�ȡ�ļ����� 
     * @param args 
     */  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
        final int DOWN_THREAD_NUM = 10;//��10���߳�ȥ��ȡָ���ļ�  
        final String OUT_FILE_NAME = "e:\\1.txt";  
        final String keywords = "�޼�";  
         //jdk1.5���ϰ汾�ṩ���̸߳����࣬�����̵߳ȴ��������߳�ִ����Ϻ�ʹ�õ��࣬  
        CountDownLatch doneSignal = new CountDownLatch(DOWN_THREAD_NUM);  
        RandomAccessFile[] outArr = new RandomAccessFile[DOWN_THREAD_NUM];  
        try{  
            long length = new File(OUT_FILE_NAME).length();  
            System.out.println("�ļ��ܳ��ȣ�"+length+"�ֽ�");  
            //ÿ�߳�Ӧ�ö�ȡ���ֽ���    
            long numPerThred = length / DOWN_THREAD_NUM;    
            System.out.println("ÿ���̶߳�ȡ���ֽ�����"+numPerThred+"�ֽ�");  
          //�����ļ�������ʣ�µ�����    
            long left = length % DOWN_THREAD_NUM;  
            for (int i = 0; i < DOWN_THREAD_NUM; i++) {    
                //Ϊÿ���̴߳�һ����������һ��RandomAccessFile��
                outArr[i] = new RandomAccessFile(OUT_FILE_NAME, "rw");    
                if (i == DOWN_THREAD_NUM - 1) {    
                      new ReadThread(i * numPerThred, (i + 1) * numPerThred    
                            + left, outArr[i],keywords,doneSignal).start();    
                } else {    
                    //ÿ���̸߳����ȡһ����numPerThred���ֽ�    
                	new ReadThread(i * numPerThred, (i + 1) * numPerThred,    
                            outArr[i],keywords,doneSignal).start();    
                }    
            }  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
 
        //ȷ�������߳�������ɣ���ʼִ�����̵߳Ĳ���  
        try {  
            doneSignal.await();  
        } catch (InterruptedException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        //������Ҫ�����жϣ�������read�����߳�ȫ��ִ���ꡣ  
        KeyWordsCount k = KeyWordsCount.getCountObject();  
        System.out.println("ָ���ؼ��ֳ��ֵĴ�����"+k.getCount());  
    }  
  
}  