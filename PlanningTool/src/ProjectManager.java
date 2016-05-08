import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Michael
public class ProjectManager {
	private Map<String, Employee> employeeMap = new HashMap<String, Employee>();
	private Map<String, Project> projectMap = new HashMap<String, Project>();
	private int nextID = 0;
	
	public ProjectManager() {
		// De ansatte er allerede i systemet, og indsættes automatisk når programmet starter.
		employeeMap.put("hund", new Employee("hund"));
		employeeMap.put("kat", new Employee("kat"));
		employeeMap.put("fritte", new Employee("fritte"));
	}
	
	public Map<String, Employee> getEmployeeMap() {
		for (String empName : employeeMap.keySet()) {
			System.out.println(empName);
		}
		return employeeMap;
	}
	
	public void getEmployeeWorkDates(Employee emp){
		if (emp == null){
			System.err.println("Employee doesn't exist.");
		}else{
			Set<String> workDates = emp.getWorkDates();
			for (String date : workDates){
				System.out.println(date);
			}
		}
	}
	
	public Employee getEmployee(String name){
		return employeeMap.get(name);
	}

	public Set<Project> getProjects() {
		return new HashSet<Project>(projectMap.values());
	}
	
	public Project getProj(String id){
		return projectMap.get(id);
	}
	
	public Project createProject(String projName, Map<String, Object> param) {
		Project newProj = new Project(projName, nextID, this, param);
		// Projektet sættes ind i et map med dets ID som key.
		projectMap.put(Integer.toString(newProj.getID()), newProj);
		nextID++;
		return newProj;
	}
}
