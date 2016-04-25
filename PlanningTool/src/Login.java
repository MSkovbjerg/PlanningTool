
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
		return currEmp;
	}

}
