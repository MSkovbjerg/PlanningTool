import java.util.HashMap;
import java.util.Map;

public class Employee {
	private String name = null;
	private Map<String, String> workTimeMap = new HashMap<String, String>();
	
	public Employee(String newName) {
		name = newName;
	}
	
	public String getName(){
		return name;
	}

	public String getWorkTime(String date) {
		if (date.length() < 11){
			return workTimeMap.get(date);
		}
		
		int min = Integer.parseInt(date.substring(0, 9).replaceAll("\\s",""));
		int max = Integer.parseInt(date.substring(10).replaceAll("\\s",""));
		/*
		int year1 = Integer.parseInt(date.substring(0, 4));
		int month1 = Integer.parseInt(date.substring(5, 7));
		int day1 = Integer.parseInt(date.substring(8, 10));
		int year2 = Integer.parseInt(date.substring(11, 15));
		int month2 = Integer.parseInt(date.substring(16, 18));
		int day2 = Integer.parseInt(date.substring(19));
		*/
		
		String out = "";
		
		for (String key : workTimeMap.keySet()){
			/*
			int keyyear = Integer.parseInt(key.substring(0, 4));
			int keymonth = Integer.parseInt(key.substring(5, 7));
			int keyday = Integer.parseInt(key.substring(8, 10));
			*/
			
			int keydate = Integer.parseInt(key.replaceAll("\\s",""));
			
			if (min <= keydate && max >= keydate){
				out += key + " " + workTimeMap.get(key) + "\n";
			}
		}
		if (out.length() > 0){
			out = out.substring(0,out.length()-1);
		}
		return out;
	}
	
	public void replaceWorkTime(String timedate, Activity newAct){
		workTimeMap.remove(timedate.substring(0, 10));
		editWorkTime(timedate, newAct);
	}
	
// "2016 8 20 14 30 17 00 newProj newAct";
	public void editWorkTime(String timedate, Activity newAct) {
		String date = timedate.substring(0, 10);
		String actTime = timedate.substring(11) + " " + newAct.getProject().getName()
						 + " " + newAct.getName();
		if (workTimeMap.containsKey(date) == true){
			String time = workTimeMap.get(date) + " " + actTime;
			workTimeMap.replace(date, time);
		}else{
			workTimeMap.put(date, actTime);	
		}
		
	}

}
