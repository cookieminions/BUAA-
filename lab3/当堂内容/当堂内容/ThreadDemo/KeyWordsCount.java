package ThreadDemo;

public class KeyWordsCount {
		  
	// ͳ�ƹؼ��ֵĶ��� 
 
	    private static KeyWordsCount kc;  
	      
	    private int count = 0;  
	    private KeyWordsCount(){  
	          
	    }  
    
	    public static synchronized KeyWordsCount getCountObject(){  
	        if(kc == null){  
	            kc = new KeyWordsCount();  
	        }  
	        return kc;  
	    }  
	  
	    public synchronized void  addCount(int count){  
	        System.out.println("���Ӵ�����"+count);  
	        this.count += count;  
	    }  
	      
	    public int getCount() {  
	        return count;  
	    }  
	  
	    public void setCount(int count) {  
	        this.count = count;  
	    }  
	      
	}  
