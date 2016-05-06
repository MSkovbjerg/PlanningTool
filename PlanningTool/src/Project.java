import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Project extends Exception{
	private String name = null;
	private Employee lead = null;
	private String start = null;
	private String end = null;
	private int id = 0;
	private Map<String, Activity> activities = new HashMap<String, Activity>();
	
	public Project(String projName, int nextID, ProjectManager projMan, Map<String, Object> param) {
	name = projName;
	if (!param.isEmpty()){
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
			if (week < 1 || week > 52 || year < 1970 || year > 9999){
				throw new IllegalArgumentException("Error: Project not created. Wrong Input.");
			}
		}
		if (param.containsKey("projEnd")) {
			end = param.get("projEnd").toString();
			int week = Integer.parseInt(end.substring(0, 2));
			int year = Integer.parseInt(end.substring(4));
			if (week < 1 || week > 52 || year < 1970 || year > 9999){
				throw new IllegalArgumentException("Error: Project not created. Wrong Input.");
			}
		}
	}

	Calendar now = Calendar.getInstance();
	int year = now.get(Calendar.YEAR);
	// ID er det nuværende år plus ID tallet paddet til 6 cifre.
	// ID starter med 0 og stiger med 1 for hvert projekt. Dvs. IDene ser således ud:
	// Første projekt: 2016000000 Andet projekt: 2016000001 Tredje projekt: 2016000002 osv. 
	id = Integer.parseInt(Integer.toString(year) + String.format("%06d", nextID));
	System.out.println("Project " + id + " " + name + " created.");
}

	public String getName() {
		return name;
	}


	public Employee getLead() {
		return lead;
	}


	public String getStartDate() {
		return start;
	}


	public String getEndDate() {
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
			System.out.println(lead.getName() + " assigned as project leader for project " + id + " " + name);
			return true;
		}		
	}

	public void getActivities(Employee emp) {
		for (Activity act : activities.values()){
			// Man kan se aktiviteterne man selv er på, eller alle sammen hvis man er projekt-leder.
			if (emp == lead || act.getEmployees().contains(emp)){
				System.out.println(Integer.toString(id) + " " + act.getName());
			}
		}
	}

	public Activity getAct(String act){
		return activities.get(act);
	}
	
	public Activity createActivity(String actName, Set<Employee> actEmployees, String actStart, String actEnd, int actBudget) {
		for (Activity act : activities.values()) {
		    if(act.getName() == actName){
		    	System.err.println("Error: Invalid activity name.");
		    	return null;
		    } 
		}
		Activity newAct = new Activity(actName, actEmployees, actStart, actEnd, actBudget, this);
		activities.put(actName, newAct);
		System.out.println("Activity " + actName + " created.");
		return newAct;
	}
}
