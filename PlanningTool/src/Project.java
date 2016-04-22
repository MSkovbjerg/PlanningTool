
public class Project {
	private String name = null;
	private Employee lead = null;
	private String start = null;
	private String end = null;
	private int id = 0;
	
	
	public Project(String projName, Employee projLead, String projStart, String projEnd, int nextID) {
		name = projName;
		lead = projLead;
		start = projStart;
		end = projEnd;
		id = Integer.parseInt(projStart.substring(projStart.length()-4) + String.format("%06d", nextID));
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

}
