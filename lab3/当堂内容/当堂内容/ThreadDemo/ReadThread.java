package ThreadDemo;

import java.io.IOException;  
import java.io.RandomAccessFile;  
import java.util.concurrent.CountDownLatch;  
  
//������ȡ�ļ�������ȡ��ָ���ؼ���ʱ����ָ���Ķ����1 
 public class ReadThread extends Thread{  
    //�����ֽ�����ĳ���    
    private final int BUFF_LEN = 256;    
    //�����ȡ����ʼ��    
    private long start;    
    //�����ȡ�Ľ�����    
    private long end;   
    //����ȡ�����ֽ������raf��,randomAccessFile����÷������аٶ�  
    private RandomAccessFile raf;    
    //�߳�����Ҫָ���Ĺؼ���  
    private String keywords;  
    //���̶߳����ؼ��ֵĴ���  
    private int curCount = 0;  
    /** 
     * jdk1.5��ʼ������࣬�Ǹ����̸߳����� 
     * ���ڶ��߳̿�ʼǰͳһִ�в������߶��߳�ִ����ɺ�������߳�ִ����Ӧ�������� 
     */  
    private CountDownLatch doneSignal;  
    public ReadThread(long start, long end, RandomAccessFile raf,String keywords,CountDownLatch doneSignal){  
        this.start = start;  
        this.end = end;  
        this.raf  = raf;  
        this.keywords = keywords;  
        this.doneSignal = doneSignal;  
    }  
      
    public void run(){  
        try {  
            raf.seek(start);  
            //���̸߳����ȡ�ļ��Ĵ�С    
            long contentLen = end - start;    
            //���������Ҫ��ȡ���ξͿ�����ɱ��̵߳Ķ�ȡ    
            long times = contentLen / BUFF_LEN+1;    
            System.out.println(this.toString() + " ��Ҫ���Ĵ�����"+times);  
            byte[] buff = new byte[BUFF_LEN];  
            int hasRead = 0;  
            String result = null;  
            for (int i = 0; i < times; i++) {    
                //֮ǰSEEKָ������ʼλ�ã��������ָ���ֽ��鳤�ȵ����ݣ�read�������ص�����һ����ʼ����position  
                hasRead = raf.read(buff);  
                 //�����ȡ���ֽ���С��0�����˳�ѭ���� �������ֽ������ĩβ��   
                if (hasRead < 0) {    
                    break;    
                }    
                result = new String(buff,"gb2312");  
                int count = this.getCountByKeywords(result, keywords);  
                if(count > 0){  
                    this.curCount += count;  
                }  
            }  
              
            KeyWordsCount kc = KeyWordsCount.getCountObject();  
  
            kc.addCount(this.curCount);  
              
            doneSignal.countDown();//current thread finished! noted by latch object!  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }
  
       
    public int getCountByKeywords(String statement,String key){  
        return statement.split(key).length-1;  
    }  
  
   
}  