import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Project extends Exception{
	private String name = null;
	private Employee lead = null;
	private String start = null;
	private String end = null;
	private int id = 0;
	private Set<Activity> activities = new HashSet<Activity>();
	
	public Project(String projName, int nextID, ProjectManager projMan, Map<String, Object> param) {
	name = projName;
	if (param != null){
		if (param.containsKey("projLead")) {
			lead = (Employee) param.get("projLead");
			if (lead == null){
				throw new IllegalArgumentException("Error: Project not created. Wrong Input.");
			}
		}
		if (param.containsKey("projStart")) {
			start = param.get("projStart").toString();
			int week = Integer.parseInt(start.substring(0, 2));
			int year = Integer.parseInt(start.substring(4));
			if (week < 0 || week > 52 || year < 1970 || year > 9999){
				throw new IllegalArgumentException("Error: Project not created. Wrong Input.");
			}
		}
		if (param.containsKey("projEnd")) {
			end = param.get("projEnd").toString();
			start = param.get("projStart").toString();
			int week = Integer.parseInt(start.substring(0, 2));
			int year = Integer.parseInt(start.substring(4));
			if (week < 0 || week > 52 || year < 1970 || year > 9999){
				throw new IllegalArgumentException("Error: Project not created. Wrong Input.");
			}
		}
	}

	Calendar now = Calendar.getInstance();
	int year = now.get(Calendar.YEAR); 
	id = Integer.parseInt(Integer.toString(year) + String.format("%06d", nextID));
	System.out.println("Project " + id + " " + name + " created.");
}

	public Object getName() {
		return name;
	}


	public Object getLead() {
		return lead;
	}


	public Object getStartDate() {
		return start;
	}


	public Object getEndDate() {
		return end;
	}


	public int getID() {
		return id;
	}

	public boolean setLead(Employee projLead) {
		if (projLead == null){
			System.err.println("Error: Invalid project leader.");
			return false;
		}else{
			lead = projLead;
			System.out.println(lead + " assigned as project leader for project " + id + " " + name);
			return true;
		}		
	}

	public Set<Activity> getActivities() {
		return activities;
	}

	public Activity createActivity(String actName, Set<Employee> actEmployees, String actStart, String actEnd, int actBudget) {
		Activity newAct = new Activity(actName, actEmployees, actStart, actEnd, actBudget);
		activities.add(newAct);
		return newAct;
	}
}
