package elevator;

import java.util.ArrayList;

import elevator.Query.Direction;




class UnsortedException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnsortedException(String message) {
		super(message);
	}
}

public class QueryList {
/*OVERVIEW:��������࣬����˿�����Query��
    ���������Ҫ���������ʱ������ĺϷ��Խ��ж����жϣ�������Ҫ��¼¥���ȡֵ��Χ�����������һ�������ʱ��
  ���ڲ�����ʱ��ǽ�������������Ҫ�׳�һ���ɱ�ʶ���쳣���ڴ����߼������Զ��ⶨ����һ��UnsortedException��
��������ṩ��ӡ���������յķ���*/
	private ArrayList<Query> queue;//�������
	private int highLevel, lowLevel;//��ߺ����¥��
	private double lastTime;//���������һ�������ʱ��
	

	public QueryList(int high, int low, double time) {
		queue = new ArrayList<Query>();
		highLevel = high;
		lowLevel = low;
		lastTime = time;
	}
	public QueryList(int high, int low) {
		this(high, low, 0);
	}
	
	public boolean append(Query req) {
       /*@ REQUIRES: req != null ;
         @ MODIFIES: this;
         @ EFFECTS: 
         (this.lastTime>req.queryTime)==>\result=false;
         (req.targetFloor=low && req.queryDirection==Direction.DOWN)==>\result=false;
         (req.targetFloor=high && req.queryDirection==Direction.UP)==>\result=false;        
         (this.queue.size == \old(this.queue).size+1) && (this.queue.contains(req)==true)&&(this.queue.lastTime==req.queryTime) && (\result==true); 
       */
	//����ݹ�񲹳����
		if(this.lastTime>req.getTime())	return false;
		if(req.getTarget()==lowLevel&&req.getDirection()==Direction.DOWN)	return false;
		if(req.getTarget()==highLevel&&req.getDirection()==Direction.UP)	return false;
		
		queue.add(req);
		lastTime = req.getTime();
		return true;
	}
	

	public boolean remove(int index) throws EmptyQueueException, InvalidIndexException{
        /*@MODIFIES:this
        @EFFECTS:
           normal_behavior
           (\old(this).get(index) !=null) ==> (this.size == \old(this).size-1) && (this.contains(\old(this).get(index))==false) && (\result==true) ; 
           (\old(this).size ==0)==>exceptional_behavior(EmptyQueueException)
           (index >=\old(this).size) ==> exceptional_behavior (InvalidIndexException);
           (index < 0) ==> exceptional_behavior (InvalidIndexException);

        */
		try {
			queue.remove(index);}
		 catch(Exception e) {
		   return false;
		}
		return true;
	}
	

	public int getSize() {
		return queue.size();
	}
	
	public Query getQuery(int index) {
		return queue.get(index);
	}
	
	public void clear() {
		queue.clear();
	}
}