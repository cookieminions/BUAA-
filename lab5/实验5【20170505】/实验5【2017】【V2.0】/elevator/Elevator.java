package elevator;

import java.util.ArrayList;




//interface
interface SchedulableCarrier {
	final double moveTime = 0.5, callTime = 1.0;
	abstract boolean moveUP();
	abstract boolean moveDOWN();
	abstract boolean callOpenAndClose();
}

//implement
public class Elevator implements SchedulableCarrier {
	/*OVERVIEW: �����࣬ģ�����ʵ�壬�ܹ���Ӧ�û����󲢸ı����״̬�������Լ���¼�͹�������˶������е�״̬�仯��������ǰͣ����¥�㣬ͣ����������֮���ʱ�䣬��ǰ�˶�����*/

	private int highLevel, lowLevel;//���¥������¥��
	private Query curStatus;//���ݵ�ǰ״̬����¼ͣ��¥�㡢������֮��ʱ�䡢��ǰ�˶�����
	private ArrayList<Query> curHandleQuery;//��ǰ�Ӵ��������

	public Elevator(int high, int low) 
	/*@REQUIRES��(high > low)&&(high > 0)&&(low > 0)&&(high<=highLevel)
  	  @EFFECTS�� (\result = this) && (this.curStatus != null) && (this.highLevel == high) && (this.lowLevel == low)&&(this.curHandleQuery.isEmpty())
	*/
		highLevel = high;
		lowLevel = low;
		curStatus = new Query(1, 0);
		curHandleQuery = new ArrayList<Query>();
	}

	@Override 
	
	public boolean moveUP() {
		if(getCurFloor() + 1 > highLevel) {
			return false;
		} else {
			curStatus = new Query(getCurFloor() + 1, getCurTime() + moveTime, Query.Direction.UP);
			return true;
		}
	}

	@Override
	
	public boolean moveDOWN() {
		if(getCurFloor() - 1 < lowLevel) {
			return false;
		} else {
			curStatus = new Query(getCurFloor() - 1, getCurTime() + moveTime, Query.Direction.DOWN);
			return true;
		}
	}

	@Override
	
	public boolean callOpenAndClose() {
		curStatus = new Query(getCurFloor(), getCurTime() + callTime);
		return true;
	}

	@Override
	public String toString() {
		return "(" + this.getCurFloor() + "," + this.getCurDirect() + "," + this.getCurQuery().getTarget() + ")";
	}
	
	
	public int getCurFloor() {
		return curStatus.getTarget();
	}
	
	public double getCurTime() {
		return curStatus.getTime();
	}

	public Query.Direction getCurDirect() {
		return curStatus.getDirection();
	}

	public Query getCurQuery() {
		return emptyQuery() ? null : curHandleQuery.get(0);
	}
	
	public boolean emptyQuery() {
		return curHandleQuery.isEmpty();
	}
	
	//OVERVIEW:����Ƿ����Ѿ���ɵ�����
	public boolean checkFinishedQuery(){
	//*@REQUIRES��None
	   @MODIFIES: curHandleQuery
	   @EFFECTS: 
            (\all int i;  0 <= i< curHandleQuery.size;curHandleQuery[i].targetFloor!=this.curStatus.targetFloor)==>\result==false
           (\any int i; 0 <= i< curHandleQuery.size; old(curHandleQuery)[i].targetFloor==(this.curStatus.tartgetFloor)==>(curHandleQuery.contains(\old(curHandleQuery)[i])==false)&&(curHandleQuery.size = \old(curHandleQuery).size - 1)\result==true) 
         */
        
        //����ݹ���޸Ĵ���
	for(int i = 1; i < curHandleQuery.size(); ++i) {
			Query pickedQuery = curHandleQuery.get(i);
			if(pickedQuery.getTarget() == getCurFloor()) {
				System.out.printf("(%d, %s, %.1f)\t(%s)\n", getCurFloor(), "STAY", getCurTime(), pickedQuery.toString());
				curHandleQuery.remove(i);
                                break;
			}
		}
		
		return true;
	}
	
	
	
	//OVERVIEW:�������Ӵ����������������ݵ�ǰ�������
	public void pickupQuery(Query req) { 
	
        /*@REQUIRES��(req!=null)&&(req.queryTime<=this.curStatus.queryTime)&&(req.queryDirection==this.curStatus.Direction)
	      @MODIFIES: this
	      @EFFECTS:    (\old(this).curHandleQuery.isEmpty())==>(this.curStatus ==req);
           (\all int i, j;  0 <= i & i < j & j < curHandleQuery.size; (curHandleQuery.size == \old(curHandleQuery).size+1) && (curHandleQuery.contains(req)==true)&&(curHandleQuery[i].queryTime<=curHandleQuery[j].queryTime)
	*/

	//����ݹ�񲹳����

         }
	


	//������Ӧ��ǰ�Ӵ��������
	public void moveForQuery() throws Exception {
		
		if(emptyQuery()) {
			return;
		}
		
		Query req = getCurQuery();
		
		boolean ifOpenAndClose = false;
		
		//��ʼ��������֮ǰ����ѯ��ǰ�Ӵ��������Ƿ���������������У�ȫ���޳���Ȼ�󿪹���һ��
		ifOpenAndClose=checkFinishedQuery();
		if(ifOpenAndClose) {
			callOpenAndClose();
			return;
		}
				
		//ִ�������������µ���״̬
		int directDelta = (int)Math.signum(req.getTarget() - getCurFloor());
		String curDirect;
		switch(directDelta) {
			case -1 : {
				curDirect = "DOWN";
				moveDOWN();
				break;
			}
			case 0 : {
				curDirect = "STAY";
				break;
			}
			case 1 : {
				curDirect = "UP";
				moveUP();
				break;
			}
			default : throw new Exception("Invalid Status.");
		}
		if(Query.checkTime(getCurTime()) == false) {
			throw new Exception("Time Out Of Range");
		}
		
		// ÿ������һ�ε���״̬��ѯ��ǰ�Ӵ��������Ƿ���������������У�ȫ���޳�����������һ��
		ifOpenAndClose=checkFinishedQuery();
		
		
		//���������
		if(req.getTarget() == getCurFloor()) {
			ifOpenAndClose = true;
			System.out.printf("(%d, %s, %.1f)\n", getCurFloor(), curDirect, getCurTime());
			curHandleQuery.remove(0);
		}
			
		if(ifOpenAndClose) {
			callOpenAndClose();
		}
	}
		
		
	}

