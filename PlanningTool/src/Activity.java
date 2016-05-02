import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Activity {
	private String name = null;
	private Set<Employee> employees = null;
	private String start = null;
	private String end = null;
	private int budget = 0;
	private Project projMan;
	private Map<String, String> workTimeMap = new HashMap<String, String>();
	
	public Activity(String actName, Set<Employee> actEmployees, String actStart, String actEnd, int actBudget, Project actMan){
		name = actName;
		for (Employee emp : actEmployees) {
		    if(emp == null){
		    	actEmployees.remove(emp);
		    	System.err.println("Error: Invalid employee found.");
		    }
		}
		employees = actEmployees;

		start = actStart;
		int week = Integer.parseInt(start.substring(0, 2));
		int year = Integer.parseInt(start.substring(4));
		if (week < 0 || week > 52 || year < 1970 || year > 9999){
			throw new IllegalArgumentException("Error: Activity not created. Invalid start.");
		}
		end = actEnd;
		week = Integer.parseInt(end.substring(0, 2));
		year = Integer.parseInt(end.substring(4));
		if (week < 0 || week > 52 || year < 1970 || year > 9999){
			throw new IllegalArgumentException("Error: Activity not created. Invalid end.");
		}
		budget = actBudget;
		projMan = actMan;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<Employee> getEmployees() {
		for (Employee emp : employees){
			System.out.println(emp.getName());
		}
		return employees;
	}
	
	public String getStart(){
		return start;
	}

	public String getEnd(){
		return end;
	}
	
	public int getBudget(){
		return budget;
	}
	
	public Project getProject(){
		return projMan;
	}
	
	public Set<Employee> addEmployee(Employee newEmployee) {
		if (newEmployee == null){
			System.err.println("Error: Employee doesn't exist.");
			return employees;
		}else{
			employees.add(newEmployee);
			return employees;	
		}
	}
	
	public void getWorkTime(String date) {
		if (date.length() < 11){
			System.out.println(workTimeMap.get(date));
		}else{
			int min = Integer.parseInt(date.substring(0, 9).replaceAll("\\s",""));
			int max = Integer.parseInt(date.substring(10).replaceAll("\\s",""));
						
			for (String key : workTimeMap.keySet()){
				
				int keydate = Integer.parseInt(key.replaceAll("\\s",""));
				
				if (min <= keydate && max >= keydate){
					System.out.println(key + " " + workTimeMap.get(key));
				}
			}
		}
	}
	
	public void getWorkDates(){
		for (String date : workTimeMap.keySet()){
			System.out.println(date);
		}
	}
	
// "2016 08 20 14 30 17 00 employee";
	public void editWorkTime(String timedate, Employee emp) {
		if (timedate.length() != 22){
			System.err.println("Invalid date or time.");
			return;
		}
		String date = timedate.substring(0, 10);
		String empTime = timedate.substring(11) + " " 
		       + emp.getName();
		
		if (workTimeMap.containsKey(date) == true){
			String time = workTimeMap.get(date) + " " + empTime;
			workTimeMap.replace(date, time);
		}else{
			workTimeMap.put(date, empTime);	
		}
	}
	
}
