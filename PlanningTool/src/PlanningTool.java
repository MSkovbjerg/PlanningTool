import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PlanningTool {
	private static Employee emp = null;
	private static ProjectManager projMan = new ProjectManager();
	
	public static Employee login(ProjectManager projMan, String logemp){
		Login li = new Login(projMan);
		return li.signIn(logemp);
	}
	
	public static Employee getEmployee(String name){
		return projMan.getEmployee(name);
	}
	
	public static void help(){
    	System.out.print("The following options are available:\n"
    			+ "1 - Create Project\n"
    			+ "2 - View All Projects\n"
    			+ "3 - View All Employees\n"
    			+ "4 - View Employee Dates\n"
    			+ "5 - View Employee Work Time\n"
    			+ "6 - Edit Work Time\n"
    			+ "7 - View Project\n"
    			+ "8 - View All Activities\n"
    			+ "9 - View Activity\n"
    			+ "10 - Create Activity\n"
    			+ "11 - Assign Project Lead\n"
    			+ "12 - View Activity Work Time\n"
    			+ "13 - Add Employee To Activity\n"
    			+ "14 - Log out\n"
    			+ "0 - Help\n");
	}
	
    public static void main(String[] args) { 	
    	Scanner in = new Scanner(System.in);
    	
    	System.out.println("Write your initials:");

    	while(true){
    		// Da man kan logge ud tjekkes login inde i loopet, så man kan logge ind igen.
        	if (emp == null){
        		String logemp = in.nextLine();
            	
            	emp = login(projMan, logemp);
            	
            	if (emp != null){
            		help();
            	}
        	}else{
        		// Interfacet styres med et switch-case. Case-inputtet er tal, da kommandoerne ellers kan blive lange.
        		switch (in.nextLine()) {
        		//Create Project
            	case "1":
            		System.out.println("Type project name:");
            		String projName = in.nextLine();
            		System.out.println("Type project lead name: (Optional)");
            		String leadName = in.nextLine();
            		System.out.println("Type project start date: (format: ww, yyyy) (Optional)");
            		String startDate = in.nextLine();
            		System.out.println("Type project end date: (format: ww, yyyy) (Optional)");
            		String endDate = in.nextLine();
            		
            		Map<String, Object> param = new HashMap<String, Object>();
            		
            		// Informationen kommer kun med hvis der er blevet indtastet noget.
            		if (!leadName.equals("")){
            			param.put("projLead", getEmployee(leadName));
            		}
            		if (!startDate.equals("")){
            			param.put("projStart", startDate);
            		}
            		if (!endDate.equals("")){
            			param.put("projEnd", endDate);
            		}
            	
            		try{
            			projMan.createProject(projName, param);
            		}catch (IllegalArgumentException e){
            			System.err.println("Error: Project not created. Wrong Input.");
            		}
            		break;
        		// View All Projects
            	case "2":
            		Set<Project> projects = projMan.getProjects();
            		for (Project p : projects){
            			System.out.println(p.getID() + " " + p.getName());
            		}
            		break;
            	// View All Employees
            	case "3":
            		projMan.getEmployeeMap();
            		break;
        		// View Employee Dates
        		// Viser alle datoer en bestemt ansat har arbejdet på.
            	case "4":
            		System.out.println("Type employee name:");
            		String empName = in.nextLine();
            		projMan.getEmployeeWorkDates(projMan.getEmployee(empName));
            		break;
        		// View Employee Work Time
        		// Viser time-information for den valgte ansatte på den valgte dato eller dato-interval.
            	case "5":
            		System.out.println("Type employee name:");
            		String dateEmpName = in.nextLine();
            		Employee dateEmp = projMan.getEmployee(dateEmpName);
            		System.out.println("Type from date: (format: yyyy mm dd)");
            		String date = in.nextLine();
            		System.out.println("Type to date: (Leave empty if only one date is needed.)");
            		String date2 = in.nextLine();
            		if (!date2.equals("")){
            			date = date + " " + date2;
            		}
            		dateEmp.getWorkTime(date);
            		break;
        		// Edit Work Time
            	case "6":
            		System.out.println("Enter project ID:");
            		String timeEditProjID = in.nextLine();
            		Project timeEditProj = projMan.getProj(timeEditProjID);
            		if (timeEditProj == null){
            			System.err.println("Invalid project.");
            			break;
            		}
            		System.out.println("Enter activity name:");
            		String timeEditName = in.nextLine();
            		Activity timeEditAct = timeEditProj.getAct(timeEditName);
            		if (timeEditAct == null){
            			System.err.println("Invalid activity.");
            			break;
            		}
            		System.out.println("Enter date: (format yyyy mm dd)");
            		String workDate = in.nextLine();
            		System.out.println("Enter start time: (format hh:mm)");
            		String start = in.nextLine();
            		System.out.println("Enter end time: (format hh:mm)");
            		String end = in.nextLine();
            		String timedate = workDate + " " + start + " " + end;
            		// Arbejdstiden ændres både hos aktiviteten og den ansatte.
            		emp.editWorkTime(timedate, timeEditAct);
            		timeEditAct.editWorkTime(timedate, emp);
            		break;
        		// View Project
            	case "7":
            		System.out.println("Enter project ID:");
            		String infoProjID = in.nextLine();
            		Project infoProj = projMan.getProj(infoProjID);
            		if (infoProj == null){
            			System.err.println("Invalid project.");
            			break;
            		}
            		System.out.println("Project name:");
            		System.out.println(infoProj.getName());
            		System.out.println("Lead name:");
            		System.out.println(infoProj.getLead().getName());
            		System.out.println("Start to end");
            		System.out.println(infoProj.getStartDate() + " to " + infoProj.getEndDate());
            		break;
        		// View All Activities
        		// Alle aktiviteter for et bestemt projekt, ikke alle i hele programmet.
            	case "8":
            		System.out.println("Enter project ID:");
            		String checkActProjID = in.nextLine();
            		Project checkActProj = projMan.getProj(checkActProjID);
            		if (checkActProj == null){
            			System.err.println("Invalid project.");
            			break;
            		}
            		checkActProj.getActivities(emp);
            		break;
        		// View Activity
            	case "9":
            		System.out.println("Enter project ID:");
            		String actInfoProjID = in.nextLine();
            		Project actInfoProj = projMan.getProj(actInfoProjID);
            		if (actInfoProj == null){
            			System.err.println("Invalid project.");
            			break;
            		}
            		System.out.println("Enter activity name:");
            		String getActName = in.nextLine();
            		Activity getAct = actInfoProj.getAct(getActName);
            		if (getAct == null){
            			System.err.println("Invalid activity.");
            			break;
            		}
            		System.out.println("Project name:");
            		System.out.println(getAct.getProject().getName());
            		System.out.println("Employees:");
            		getAct.getEmployees();
            		System.out.println("Start to end:");
            		System.out.println(getAct.getStart() + " to " + getAct.getEnd());
            		System.out.println("Budget:");
            		System.out.println(getAct.getBudget());
            		System.out.println("Work Dates:");
            		getAct.getWorkDates();
            		break;
        		// Create Activity
            	case "10":
            		System.out.println("Enter project ID.");
            		String projID = in.nextLine();
            		Project actProj = projMan.getProj(projID);
            		if (actProj == null){
            			System.err.println("Invalid project.");
            			break;
            		}
            		if (actProj.getLead() != emp){
            			System.err.println("You don't have permission to do that.");
            			break;
            		}
            		System.out.println("Type activity name:");
            		String actName = in.nextLine();
            		System.out.println("Type employees: (space between each)");
            		String actEmps = in.nextLine();
            		Set<Employee> actEmpsSet = new HashSet<Employee>();
            		for (String em : actEmps.split(" ")){
            			actEmpsSet.add(projMan.getEmployee(em));
            		}
            		System.out.println("Type start date: (format ww, yyyy)");
            		String actStart = in.nextLine();
            		System.out.println("Type end date: (format ww, yyyy)");
            		String actEnd = in.nextLine();
            		System.out.println("Type budget weeks:");
            		String sActBudget = in.nextLine();
            		if (sActBudget.equals("")){
            			System.err.println("Invalid budget.");
            			break;
            		}
            		int actBudget = Integer.parseInt(sActBudget);
            		
            		try{
            			actProj.createActivity(actName, actEmpsSet, actStart, actEnd, actBudget);
            		}catch (IllegalArgumentException | StringIndexOutOfBoundsException e){
            			System.err.println(e.getMessage());
            		}
            		break;
            	// Assign Project Lead
            	case "11":
            		System.out.println("Enter project ID:");
            		String addProjID = in.nextLine();
            		Project addProj = projMan.getProj(addProjID);
            		if (addProj == null){
            			System.err.println("Invalid project.");
            			break;
            		}
            		System.out.println("Type project lead name:");
            		String addLeadName = in.nextLine();
            		Employee newLead = projMan.getEmployee(addLeadName);
            		if (newLead == null){
            			System.err.println("Invalid employee.");
            			break;
            		}
            		addProj.setLead(newLead);
            		break;
        		// View Activity Work Time
        		// Viser time-information for den valgte aktivitet på den valgte dato eller dato-interval.
            	case "12":
            		System.out.println("Enter project ID:");
            		String actTimeProjID = in.nextLine();
            		Project actTimeProj = projMan.getProj(actTimeProjID);
            		if (actTimeProj == null){
            			System.err.println("Invalid project.");
            			break;
            		}
            		if (actTimeProj.getLead() != emp){
            			System.err.println("You don't have permission to do that.");
            			break;
            		}
            		System.out.println("Enter activity name:");
            		String timeActName = in.nextLine();
            		Activity timeAct = actTimeProj.getAct(timeActName);
            		if (timeAct == null){
            			System.err.println("Invalid activity.");
            			break;
            		}
            		System.out.println("Type from date: (format: yyyy mm dd)");
            		String actDate = in.nextLine();
            		System.out.println("Type to date: (Leave empty if only one date is needed.)");
            		String actDate2 = in.nextLine();
            		if (!actDate2.equals("")){
            			actDate = actDate + " " + actDate2;
            		}
            		timeAct.getWorkTime(actDate);
            		break;
            	case "13":
            		System.out.println("Enter project ID:");
            		String addEmpProjID = in.nextLine();
            		Project addEmpProj = projMan.getProj(addEmpProjID);
            		if (addEmpProj == null){
            			System.err.println("Invalid project.");
            			break;
            		}
            		if (addEmpProj.getLead() != emp){
            			System.err.println("You don't have permission to do that.");
            			break;
            		}
            		System.out.println("Enter activity name:");
            		String addEmpActName = in.nextLine();
            		Activity addEmpAct = addEmpProj.getAct(addEmpActName);
            		if (addEmpAct == null){
            			System.err.println("Invalid activity.");
            			break;
            		}
            		System.out.println("Current employees:");
            		Set<Employee> curremps = addEmpAct.getEmployees();
            		if (!curremps.contains(emp) && addEmpProj.getLead() != emp){
            			System.err.println("You don't have permission to do that.");
            			break;
            		}
            		System.out.println("Type new employees name:");
            		String addEmpName = in.nextLine();
            		Employee addedEmp = projMan.getEmployee(addEmpName);
            		addEmpAct.addEmployee(addedEmp);
            		break;
        		// Log out
            	case "14":
            		emp = null;
            		System.out.println("Write your initials:");
            		break;
        		// Help
            	case "0":
            		help();
            		break;
            	default:
            		System.err.println("Invalid input.");
        		}
        	}	 
    	}       		
    }
}
