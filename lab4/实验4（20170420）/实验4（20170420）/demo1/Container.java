package demo1;

import java.util.ArrayList;
import java.util.List;


/**
 * װ��Ʒ������

 *
 */
public class Container<T> {
    
    private final int capacity;
    
    private final List<T> list;
    
    public Container(int capacity){
        this.capacity = capacity;
        list = new ArrayList<T>(capacity);
    }
    
    public List<T> getList(){
        return list;
    }
    
    
    /**
     * ����������Ӳ�Ʒ 
     * @param product
     */
    public synchronized add(T product){
        
    }
    
    /**
     * �����Ƿ�����
     * @return
     */
    public synchronized isFull(){
        
    }
    
    
    /**
     * �����Ƿ�Ϊ��
     * @return
     */
    public synchronized isEmpty(){
        
    }
    
    
    /**
     * ȡ�������һ������
     * @return
     */
    public synchronized T get(){
        
    }
    
    
    
    /**
     * ����Ŀǰ��С��ģ��Ŀǰ�ܹ��ж��ٿͻ��ڵȴ�
     * @return
     */
    public int getSize(){
        
    }
    
    
    /**
     * ����������С��ģ�����д����ܹ��������ɶ��ٿͻ�����
     * @return
     */
    public int getCapacity(){
       
    }
}
