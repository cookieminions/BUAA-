package taxi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Vector;

/* Overview: ����࿪��һ��3s���������� �����󷢳�λ����Χ4��4�����м�¼�����ĳ��⳵��Ϣ �����������ڽ�����ѡȡ����Ҫ��ĳ��⳵��Ӧ����
 * Objects: cityMap, taxis, startTime, RouteGuider, req;
 * grabWindowͨ��getRange�����õ����󷢳�λ����Χ4��4����
 * grabWidow��grab�����ܹ���¼���󷢳�3s�ڵĳ��⳵��Ϣ ����3s������ѡ�����Ҫ��ĳ��⳵
 * grabWindow��run�����е���grab��������⳵
 * invariant: !((cityMap==null||taxis==null||!((Object)startTime instanceof Long)||(RouteGuider==null)||req==null)||
 * 				(cityMap.length!=80||taxis.length!=100)||(\exist int i;0<=i<80;cityMap[i].length!=80)||
 * 				(\exist int i;0<=i<100;taxis[i]==null))
 */
public class grabWindow extends Thread{
	private point[][] cityMap;
	private taxi[] taxis;
	private long startTime;
	private RouteBFS RouteGuider;
	private request req;
	
	public boolean repOK(){
		/* 
		  @EFFECTS: (cityMap==null||taxis==null||!((Object)startTime instanceof Long)||(RouteGuider==null)||req==null) ==> \result == false;
		  			(cityMap.length!=80||taxis.length!=100) ==> \result == false;
		  			(\exist int i;0<=i<80;cityMap[i].length!=80) ==> \result == false;
		  			(\exist int i,j;0<=i<80,0<=j<80,;cityMap[i][j]==null) ==> \result == false;
		  			(\exist int i;0<=i<100;taxis[i]==null) ==> \result == false;
		  			(�����������) ==> \result == true;
		  */
		if(cityMap==null||taxis==null||!((Object)startTime instanceof Long)||(RouteGuider==null)||req==null)
			return false;
		if(cityMap.length!=80||taxis.length!=100)	return false;
		for(int i=0;i<80;i++){
			if(cityMap[i].length!=80)	return false;
			for(int j=0;j<80;j++){
				if(cityMap[i][j]==null)	return false;
			}
		}
		for(int i=0;i<100;i++){
			if(taxis[i]==null)	return false;
		}
		return true;
	}
	
	public grabWindow(point[][] map,taxi[] ts,request r,long st){
		/*
		  @MODIFIES: cityMap, taxis, startTime, RouteGuider, req;
		  */
		cityMap = map;
		taxis = ts;
		startTime = st;
		req = r;
		RouteGuider = new RouteBFS(cityMap);
	}
	
	public void run(){
		/* 
		  @MODIFIES: taxis;
		  @EFFECTS: (grab(taxis)!=null) ==> (grab(taxis).addTaxiReq(req)&&grab(taxis).setStatus("moving"));
		  */
		createFile();
		taxi gotTaxi = grab(taxis);
		if(gotTaxi!=null){
			gotTaxi.addTaxiReq(req);
			gotTaxi.setStatus("moving");
		}else{
			System.out.println("No Taxi Response at "+req.toString());
		}
	}
	
	private taxi grab(taxi[] taxis){
		/*
		  @MODIFIES: None;
		  @EFFECTS: (3s����������,���󷢳���Ϊ���ĵ� 4 �� 4 ���������ܹ���Ӧ����ĳ��⳵) ==> \result == getHopeOne();
		  */
		Vector<point> Range = getRange(req.start_point);
		Vector<taxi> grabTaxis = new Vector<taxi>();
		int count = 0;	String record = "";
		while(true){
			if((getTime()-req.req_t)>=30.0)
				break;
			for(int i=0;i<100;i++){
			for(int j=0;j<Range.size();j++){
				if(taxis[i].getPosition().equals(Range.get(j))){
					if(count==0){
						record += "taxi-"+taxis[i].getTaxiId()+" credit:"+taxis[i].getCredit()+" "+taxis[i].getStatus()+" "+taxis[i].getPosition().toString()+"  ";
					}
					if(!grabTaxis.contains(taxis[i])&&taxis[i].getStatus().equals("waiting")){
						taxis[i].addCredit(1);
						grabTaxis.add(taxis[i]);
					}
					break;
				}
			}
			}
			if(count==0){count=1;	record += "\r\n";}
		}
		for(int i=0;i<grabTaxis.size();i++){
			record += "taxi-"+grabTaxis.get(i).getTaxiId()+" ";
		}	record+="\r\n";
		
		taxi HopeOne = getHopeOne(grabTaxis,req.start_point);
		
		if(HopeOne!=null)	record += "taxi-"+HopeOne.getTaxiId()+" Response\r\n";
		else				record += "No Taxi Response\r\n";
		writeFile(record);
		
		return HopeOne;
	}
	
	private Vector<point> getRange(point cPoint){
		/*
		  @MODIFIES: None;
		  @EFFECTS: \result == (�������󷢳���Ϊ���ĵ� 4 �� 4 �����point��ɵ�Vector);
		  */
		int x_s = (cPoint.x-2)>=0?(cPoint.x-2):2;	int x_e = (cPoint.x+2)<=79?(cPoint.x+2):79;
		int y_s = (cPoint.y-2)>=0?(cPoint.y-2):2;	int y_e = (cPoint.y+2)<=79?(cPoint.y+2):79;
		Vector<point> rangePoints = new Vector<point>();
		for(int i=x_s;i<=x_e;i++){
			for(int j=y_s;j<=y_e;j++){
				rangePoints.add(cityMap[i][j]);
			}
		}
		return rangePoints;
	}
	private taxi getHopeOne(Vector<taxi> grabTaxis,point cPoint){
		/*
		  @MODIFIES: None;
		  @EFFECTS: \result == (�ܹ���Ӧ����ĳ��⳵��������ߵĳ��⳵�������������ͬ��������������);
		  */
		Vector<taxi> HopeOnes = new Vector<taxi>();
		for(int i=0;i<grabTaxis.size();i++){
			if(grabTaxis.get(i).getStatus().equals("waiting")){
				taxi tmpTaxi = grabTaxis.get(i);
				if(HopeOnes.size()==0)	HopeOnes.add(tmpTaxi);
				else{
					if(HopeOnes.get(0).getCredit()==tmpTaxi.getCredit())	HopeOnes.add(tmpTaxi);
					else if(HopeOnes.get(0).getCredit()<tmpTaxi.getCredit()){
						HopeOnes.removeAllElements();
						HopeOnes.add(tmpTaxi);
					}
				}
			}
		}
		if(HopeOnes.size()==0)	return null;
		else if(HopeOnes.size()==1)	return HopeOnes.get(0);
		else{
			int maxdist = 99999999;
			taxi tmpTaxi = null;
			for(int i=0;i<HopeOnes.size();i++){
				int dist = HopeOnes.get(i).findroute(HopeOnes.get(i).getnextPoint(), cPoint).size();
				if(dist<maxdist){
					maxdist = dist;
					tmpTaxi = HopeOnes.get(i);
				}
			}
			return tmpTaxi;
		}
	}
	public double getTime() {
		/* 
		  @MODIFIES: None;
		  @EFFECTS: \result == Double((System.currentTimeMillis()-startTime)/100);
		  */
		double t = (System.currentTimeMillis() - startTime)/100;
		t = Double.parseDouble(new DecimalFormat("#0.0").format(t));
		return t;
	}
	
	private void createFile(){
		/* 
		  @MODIFIES: None;
		  @EFFECTS: �ļ�(req.toString()+".txt")������ ==> �½��ļ�(req.toString()+".txt");
		  			�ļ�(req.toString()+".txt")���� ==> ����ļ�(req.toString()+".txt");
		  */
		String path = req.toString()+".txt";
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {}
	}
	private void writeFile(String str){
		/* 
		  @MODIFIES: None
		  */
		String path = req.toString()+".txt";
		File file = new File(path);
		try {
			FileWriter fw = new FileWriter(file,true);
			fw.write(str);
			fw.close();
		} catch (IOException e) {}
	}
	
}
