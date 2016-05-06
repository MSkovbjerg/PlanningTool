import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Employee {
	private String name = null;
	private Map<String, String> workTimeMap = new HashMap<String, String>();
	
	public Employee(String newName) {
		name = newName;
	}
	
	public String getName(){
		return name;
	}

	public Set<String> getWorkDates(){
		return workTimeMap.keySet();
	}
	
	public void getWorkTime(String date) {
		if (date.length() < 11){
			System.out.println(workTimeMap.get(date));
		}else{
			// Alle mellemrum fjernes så f.eks. 2016 06 12 bliver til 20160612.
			int min = Integer.parseInt(date.substring(0, 9).replaceAll("\\s",""));
			int max = Integer.parseInt(date.substring(10).replaceAll("\\s",""));
			
			for (String key : workTimeMap.keySet()){
				int keydate = Integer.parseInt(key.replaceAll("\\s",""));
				
				// Da vi har formatet yyyymmdd er et lavere tal altid en tidligere dato.
				// Derfor kan man bare se på værdien af tallene.
				if (min <= keydate && max >= keydate){
					System.out.println(key + " " + workTimeMap.get(key));
				}
			}
		}
	}
	
	// Format eksempel: "2016 08 20 14:30 17:00 newProj newAct";
	public void editWorkTime(String timedate, Activity newAct) {
		if (timedate.length() != 22){
			System.err.println("Invalid date or time.");
			return;
		}
		String date = timedate.substring(0, 10);
		String actTime = timedate.substring(11) + " " + Integer.toString(newAct.getProject().getID())
						 + " " + newAct.getName();
		if (workTimeMap.containsKey(date) == true){
			String time = workTimeMap.get(date) + " " + actTime;
			workTimeMap.replace(date, time);
		}else{
			workTimeMap.put(date, actTime);	
		}
		System.out.println("Work time registered.");		
	}

}
