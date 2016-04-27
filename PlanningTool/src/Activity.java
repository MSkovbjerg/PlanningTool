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
			throw new IllegalArgumentException("Error: Activity not created. Wrong Input.");
		}
		end = actEnd;
		week = Integer.parseInt(end.substring(0, 2));
		year = Integer.parseInt(end.substring(4));
		if (week < 0 || week > 52 || year < 1970 || year > 9999){
			throw new IllegalArgumentException("Error: Activity not created. Wrong Input.");
		}
		budget = actBudget;
		projMan = actMan;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<Employee> getEmployees() {
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
	
	public Set<Employee> removeEmployee(Employee oldEmployee) {
		employees.remove(oldEmployee);
		return employees;
	}
	
	public String setStart(String newStart){
		start = newStart;
		return start;
	}

	public String setEnd(String newEnd){
		end = newEnd;
		return end;
	}
	
	public int setBudget(int newBudget){
		budget = newBudget;
		return budget;
	}
	
	public void replaceWorkTime(String timedate, Employee emp){
		workTimeMap.remove(timedate.substring(0, 10));
		editWorkTime(timedate, emp);
	}
	
	public String getWorkTime(String date) {
		return workTimeMap.get(date);
	}
// "2016 8 20 14 30 17 00 employee";
	public void editWorkTime(String timedate, Employee emp) {
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
