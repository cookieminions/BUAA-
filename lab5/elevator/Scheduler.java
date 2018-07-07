package elevator;

public class Scheduler {
	private Elevator elevator;
	private QueryList queryList;
	private int highLevel, lowLevel;
	public Scheduler() {
		this(10, 1);
	}
	//��ʼ��������
	public Scheduler(int high, int low) {
		if(low != 1) {
			low = 1;
		}
		if(high < low) {
			high = low;
		}
		highLevel = high;
		lowLevel = low;
		elevator = new Elevator(high, low);
		queryList = new QueryList(high, low);
	}
	
	public boolean addQuery(Query req) throws Throwable {
		return queryList.append(req);
	}
	protected Elevator getElevator() {
		return elevator;
	}
	protected QueryList getQueryList() {
		return queryList;
	}
	protected int getHighLevel() {
		return highLevel;
	}
	protected int getLowLevel() {
		return lowLevel;
	}
}

 class ALS_Scheduler extends Scheduler {
	public ALS_Scheduler() {
		this(10, 1);
	}
	public ALS_Scheduler(int high, int low) {
		super(high, low);
	}
	public void runElevator() {
		// fetch super.properties
		Elevator elevator = getElevator();
		QueryList queryList = getQueryList();
		int highLevel = getHighLevel(), lowLevel = getLowLevel();
		// A Little Smart schedule
		if(queryList.getSize() == 0) {
			System.out.println("Empty Query.");
			return;
		} else if(queryList.getQuery(0).getTime() != 0) {
			System.out.println("Query Not Started At Time 0.");
			return;
		}
		//ɨ�赱ǰ��������Ķ��У��ж��Ƿ��е��ݵ�ǰ״̬�¿����Ӵ�������������ӵ����ݵ�ǰ�����������
		while(queryList.getSize() > 0) {
			try {
				//ȡ��������,��Ϊ������Ҫ��Ӧ��������
				Query query = queryList.getQuery(0);
				int target = query.getTarget();
				if(target < lowLevel || target > highLevel) {
					throw new Exception("Invalid Query.");
				}
			    //������������ӵ����ݵĵ�ǰ�Ӵ����������
				elevator.pickupQuery(query);
				//�������������������������Ƴ�
				queryList.remove(0);
				//���ݵ���������Ŀ��¥��ʱ�ĸò�ָ����Ӵ�
				boolean ifPick = query.getTarget() != elevator.getCurFloor();
				//�жϵ�ǰ�����˶�����������Ӵ���������
				Query.Direction pickedDirect = ifPick ? (query.getTarget() < elevator.getCurFloor() ? Query.Direction.DOWN : Query.Direction.UP) : Query.Direction.NONE;
				
				//ֻҪ�Ӵ�������������δִ���꣬�ͼ�����ѯ�Ƿ�����Ҫ�Ӵ�������
				do {
					//����������л�ÿ��Խ����Ӵ����������󣬼��뵽���ݵ�ǰ�����������
					if(ifPick) {
						//��õ��ݵ�ǰͣ����¥��
						int curFloor = elevator.getCurFloor();
						//����Ӵ����еĶ������󣨼���ǰ�����󣩵�Ŀ��¥��
						int targetFloor = elevator.getCurQuery().getTarget();//
						
						for(int index = 0; index < queryList.getSize(); ++index) {
							Query req = queryList.getQuery(index);
							//����ʱ��λ�û�в����µ�����ֱ������ѭ��
							//���ؼ��������Ӵ���������˶���ִ��elevator.moveforQuery
							//ʹ��break��ǰ�����������������Ѿ��ǰ�ʱ������
							if(elevator.getCurTime() < req.getTime()) {
								break;
							}
							//����������Ե����ڣ�����ķ��������Ӵ�Ҫ���������������Ӵ�����
							if(req.getDirection() == Query.Direction.NONE) { // ER
								if(pickedDirect == Query.Direction.UP && req.getTarget() >= curFloor
								|| pickedDirect == Query.Direction.DOWN && req.getTarget() <= curFloor) {
									elevator.pickupQuery(req);//�Ӵ�
									queryList.remove(index);
									--index;
								}
								//�����������¥�㣬����ķ��������Ӵ�Ҫ���������������Ӵ�����
							} else if(req.getDirection() == pickedDirect) { // FR
								if(pickedDirect == Query.Direction.UP && req.getTarget() >= curFloor && req.getTarget() <= targetFloor
								|| pickedDirect == Query.Direction.DOWN && req.getTarget() <= curFloor && req.getTarget() >= targetFloor) {
									elevator.pickupQuery(req);//�Ӵ�
									queryList.remove(index);
									--index;
								}
							}
						}
					}
					
					//��Ӧ�Ӵ���������е���������
					elevator.moveForQuery();
				} while(!elevator.emptyQuery());
			} catch(Exception except) {
				System.out.print(except.getMessage());
				System.out.println("Ignored.");
			}
		}
		queryList.clear();
	}
}