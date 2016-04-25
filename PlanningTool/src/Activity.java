import java.util.Set;

public class Activity {
	private String name = null;
	private Set<Employee> employees = null;
	private String start = null;
	private String end = null;
	private int budget = 0;
	
	public Activity(String actName, Set<Employee> actEmployees, String actStart, String actEnd, int actBudget){
		name = actName;
		for (Employee emp : actEmployees) {
		    if(emp == null){
		    	actEmployees.remove(emp);
		    	System.err.println("Error: Invalid employee found.");
		    }
		}
		employees = actEmployees;
		start = actStart;
		end = actEnd;
		budget = actBudget;
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
	
}
