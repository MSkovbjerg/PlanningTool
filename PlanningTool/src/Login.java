
public class Login {
	Employee currEmp = null;
	ProjectManager projMan = null;
	
	public Login(ProjectManager newProjMan) {
		projMan = newProjMan;
	}

	public Employee loggedIn() {
		return currEmp;
	}

	public Employee signIn(String name) {
		currEmp = projMan.getEmployee(name);
		if (currEmp == null){
			System.err.print("Error: Wrong login.\nTry again.\n");
		}else{
			System.out.println("Logged in succesfully.");
		}
		return currEmp;
	}

}
