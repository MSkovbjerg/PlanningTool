import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProjectManager {
	private Map<String, Employee> employeeMap = new HashMap<String, Employee>();
	private Set<Project> projectSet = new HashSet<Project>();
	// Ikke helt sikker på hvordan de vil have ID.
	private int nextID = 0;
	
	public ProjectManager() {
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
	
	public Employee getEmployee(String name){
		return employeeMap.get(name);
	}

	public Set<Project> getProjects() {
		return projectSet;
	}
	
	public Project createProject(String projName, Map<String, Object> param) {
		Project newProj = new Project(projName, nextID, this, param);
		nextID++;
		projectSet.add(newProj);
		return newProj;
	}
}
