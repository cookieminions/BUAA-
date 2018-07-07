package taxi;

public class Taxi_Main {
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		
		city_map city_map = new city_map();
		boolean MapCorrect = city_map.readMap();
		int[][] map = city_map.tmpMap;
		
		TaxiGUI gui = new TaxiGUI();
		gui.LoadMap(map, 80);
		
		if(MapCorrect){
			point[][] cityMap = city_map.get_cityMap();
			requestList reqlist = new requestList(startTime);
			Taxi_Sys taxi_Sys = new Taxi_Sys(cityMap, reqlist, startTime, gui);
			input_handle iHandle = new input_handle(startTime, reqlist);
			
			iHandle.start();
			taxi_Sys.start();
			
			demothread demot = new demothread(reqlist, taxi_Sys);
			demot.start();
		}else{
			System.out.println("cityMap error");
		}
	}
}