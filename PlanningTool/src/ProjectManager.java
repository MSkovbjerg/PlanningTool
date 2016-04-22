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
	}
	
	public Map<String, Employee> getEmployeeMap() {
		return employeeMap;
	}
	
	public Employee getEmployee(String name){
		return employeeMap.get(name);
	}

	public Set<Project> getProjects() {
		return projectSet;
	}

	public Project createProject(String projName, Employee projLead, String projStart, String projEnd) {
		Project newProj = new Project(projName, projLead, projStart, projEnd, nextID);
		nextID++;
		projectSet.add(newProj);
		return newProj;
	}
}
