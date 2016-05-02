import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


//Skal nok deles op i flere klasser. Dog i starten med happy path vil dette nok være bedst.
public class testOpretProjekt{
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}

	// 1. Som medarbejer, vil jeg gerne kunne logge ind i systemet, så jeg kan oprette et projekt.
	@Test
	public void testLogin(){
		ProjectManager projMan = new ProjectManager();
		Login li = new Login(projMan);

		// Alle er lige i login, man er kun manager per projekt.
		assertNull(li.loggedIn());

		Employee emp = li.signIn("hund");
		assertTrue(outContent.toString().contains("Logged in succesfully."));

		assertTrue(projMan.getEmployeeMap().get("hund") == emp);
		// Hvis man er logget ind er emp ikke Null, ellers er emp Employee objektet for den medarbejder.
		assertNotNull(emp);
		assertSame(li.loggedIn(), emp);
	}

	@Test
	public void testLoginFailed(){
		ProjectManager projMan = new ProjectManager();
		Login li = new Login(projMan);

		// Alle er lige i login, man er kun manager per projekt.
		assertNull(li.loggedIn());

		// Alt man indtaster bliver en String, så det er kun Strings der behøver at testes.
		Employee emp = li.signIn("forkert");
		assertTrue(errContent.toString().contains("Error: Wrong login."));
		
		assertTrue(projMan.getEmployeeMap().get("forkert") == emp);
		// Hvis man er logget ind er emp ikke Null, ellers er emp Employee objektet for den medarbejder.
		assertNull(emp);
		assertSame(li.loggedIn(), emp);
	}

    // 2. Som medarbejder, vil jeg gerne kunne oprette et projekt, så jeg kan have overblik over aktiviteter og medarbejdere.

	@Test
    public void testCreateProject(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	// Kun projName er obligatorisk. De andre er valgfri. ID er automatisk.
    	String projName = "Fedt Projekt";
    	// getEmployee skal give én ansat ud fra navn, getEmployeeSet skal give dem alle sammen.
    	Employee projLead = projMan.getEmployee("hund");
    	// Projekt-tidslinjen skal være per uge. Der skal dog også bruges datoer senere, men Calender kan konvertere i mellem dem.
    	// Week, year
    	String projStart = "14, 2016";
    	String projEnd = "18, 2016";
    	int year = 2016; 
    	// Var der andre ting man kan initialisere?

    	assertEquals(projMan.getProjects().size(), 0);

    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param.put("projLead", projLead);
    	param.put("projStart", projStart);
    	param.put("projEnd", projEnd);
    	
    	Project newProj = projMan.createProject(projName, param);

    	
    	assertEquals(newProj.getName(), projName);
    	assertSame(newProj.getLead(), projLead);

    	assertEquals(newProj.getStartDate(), projStart);
    	assertEquals(newProj.getEndDate(), projEnd);

    	// Vi skal lave en funktion i ProjectManager der returner alle projekter her kaldet getProjects()
    	assertEquals(projMan.getProjects().size(), 1);

    	// Vi behøver kun at finde projektet på denne måde i test, så det gør ikke så meget.
    	// Det er besværligt i test, og kan gøres lidt anderledes, men normalt så har man allerede projektet
    	// f.eks. fra en liste hvilket man så slår op i Settet.
    	Project foundProj = projMan.getProjects().iterator().next();
    	assertSame(foundProj, newProj);
    	assertEquals(foundProj.getName(), projName);
    	assertSame(foundProj.getLead(), projLead);
    	assertEquals(foundProj.getStartDate(), projStart);
    	assertEquals(foundProj.getEndDate(), projEnd);

    	// Tjekker om ID er 10 cifre langt og om de første 4 cifre er årstallet.
    	int id = foundProj.getID();
    	
    	assertTrue(outContent.toString().contains("Project " + id + " " + projName + " created."));
    	
    	assertEquals(10, String.valueOf(id).length()) ;
    	assertEquals(year, Integer.parseInt(Integer.toString(id).substring(0, 4)));
    }

    @Test
    public void testCreateProjectEmpty(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	// Kun projName er obligatorisk. De andre er valgfri. ID er automatisk.
    	String projName = "Fedt Projekt";
    	// getEmployee skal give én ansat ud fra navn, getEmployeeSet skal give dem alle sammen.
    	int year = 2016; 
    	// Var der andre ting man kan initialisere?

    	assertEquals(projMan.getProjects().size(), 0);

    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	//Project newProj = projMan.createProject(projName, projLead, projStart, projEnd);
    	Project newProj = projMan.createProject(projName, param);

    	
    	assertEquals(newProj.getName(), projName);
    	assertNull(newProj.getLead());

    	assertNull(newProj.getStartDate());
    	assertNull(newProj.getEndDate());

    	// Vi skal lave en funktion i ProjectManager der returner alle projekter her kaldet getProjects()
    	assertEquals(projMan.getProjects().size(), 1);

    	// Vi behøver kun at finde projektet på denne måde i test, så det gør ikke så meget.
    	// Det er besværligt i test, og kan gøres lidt anderledes, men normalt så har man allerede projektet
    	// f.eks. fra en liste hvilket man så slår op i Settet.
    	Project foundProj = projMan.getProjects().iterator().next();
    	assertSame(foundProj, newProj);
    	assertEquals(foundProj.getName(), projName);
    	assertNull(foundProj.getLead());
    	assertNull(foundProj.getStartDate());
    	assertNull(foundProj.getEndDate());

    	// Tjekker om ID er 10 cifre langt og om de første 4 cifre er årstallet.
    	int id = foundProj.getID();
    	
    	assertTrue(outContent.toString().contains("Project " + id + " " + projName + " created."));
    	
    	assertEquals(10, String.valueOf(id).length()) ;
    	assertEquals(year, Integer.parseInt(Integer.toString(id).substring(0, 4)));
    }

    @Test
    public void testCreateProjectTwoVar(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	// Kun projName er obligatorisk. De andre er valgfri. ID er automatisk.
    	String projName = "Fedt Projekt";
    	// getEmployee skal give én ansat ud fra navn, getEmployeeSet skal give dem alle sammen.
    	Employee projLead = projMan.getEmployee("hund");
    	// Projekt-tidslinjen skal være per uge. Der skal dog også bruges datoer senere, men Calender kan konvertere i mellem dem.
    	// Week, year
    	String projStart = "14, 2016";
    	int year = 2016; 
    	// Var der andre ting man kan initialisere?

    	assertEquals(projMan.getProjects().size(), 0);

    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param.put("projLead", projLead);
    	param.put("projStart", projStart);
    	
    	Project newProj = projMan.createProject(projName, param);
    	
    	assertEquals(newProj.getName(), projName);
    	assertSame(newProj.getLead(), projLead);

    	assertEquals(newProj.getStartDate(), projStart);
    	assertNull(newProj.getEndDate());

    	// Vi skal lave en funktion i ProjectManager der returner alle projekter her kaldet getProjects()
    	assertEquals(projMan.getProjects().size(), 1);

    	// Vi behøver kun at finde projektet på denne måde i test, så det gør ikke så meget.
    	// Det er besværligt i test, og kan gøres lidt anderledes, men normalt så har man allerede projektet
    	// f.eks. fra en liste hvilket man så slår op i Settet.
    	Project foundProj = projMan.getProjects().iterator().next();
    	assertSame(foundProj, newProj);
    	assertEquals(foundProj.getName(), projName);
    	assertSame(foundProj.getLead(), projLead);
    	assertEquals(foundProj.getStartDate(), projStart);
    	assertNull(foundProj.getEndDate());

    	// Tjekker om ID er 10 cifre langt og om de første 4 cifre er årstallet.
    	int id = foundProj.getID();
    	
    	assertTrue(outContent.toString().contains("Project " + id + " " + projName + " created."));
    	
    	assertEquals(10, String.valueOf(id).length()) ;
    	assertEquals(year, Integer.parseInt(Integer.toString(id).substring(0, 4)));
    }

    @Test
    public void testCreateProjectWrongLead(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	// Kun projName er obligatorisk. De andre er valgfri. ID er automatisk.
    	String projName = "Nederen Projekt";
    	// getEmployee skal give én ansat ud fra navn, getEmployeeSet skal give dem alle sammen.
    	Employee projLead = projMan.getEmployee("forkert");
    	// Projekt-tidslinjen skal være per uge. Der skal dog også bruges datoer senere, men Calender kan konvertere i mellem dem.
    	// Week, year
    	String projStart = "14, 2016";
    	// Var der andre ting man kan initialisere?

    	assertEquals(projMan.getProjects().size(), 0);

    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param.put("projLead", projLead);
    	param.put("projStart", projStart);
    	
    	Project newProj = null;
    	
    	try{
    		newProj = projMan.createProject(projName, param);	
    	}catch (Exception e){
    		assertTrue(e.getMessage() == "Error: Project not created. Wrong Input.");
    	}
    	
    	assertNull(newProj);
    }

    @Test
    public void testCreateProjectWrongStart(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	// Kun projName er obligatorisk. De andre er valgfri. ID er automatisk.
    	String projName = "Nederen Projekt";
    	// getEmployee skal give én ansat ud fra navn, getEmployeeSet skal give dem alle sammen.
    	Employee projLead = projMan.getEmployee("hund");
    	// Projekt-tidslinjen skal være per uge. Der skal dog også bruges datoer senere, men Calender kan konvertere i mellem dem.
    	// Week, year
    	String projStart = "1, 201677";
    	String projEnd = "18, 2016";
    	// Var der andre ting man kan initialisere?

    	assertEquals(projMan.getProjects().size(), 0);

    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param.put("projLead", projLead);
    	param.put("projStart", projStart);
    	param.put("projEnd", projEnd);
    	
    	Project newProj = null;
    	
    	try{
    		newProj = projMan.createProject(projName, param);
    		assertTrue(false);
    	}catch (IllegalArgumentException e){}
    	
    	assertNull(newProj);
    }

    // 3. Som medarbejder, vil jeg gerne kunne tildele en projektleder til et projekt, så nogen kan lede projektet.
    
    @Test
    public void testAssignProjLead(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	String projName = "Fedt Projekt";
    	Employee projLead = projMan.getEmployee("hund");

		Map<String, Object> param = new HashMap<String, Object>();
    	
    	// Opret projekt uden project lead.
    	Project newProj = projMan.createProject(projName, param);
    	
    	assertEquals(newProj.getName(), projName);

    	assertNull(newProj.getLead());

    	boolean newLead = newProj.setLead(projLead);

    	int id = newProj.getID();
    	
    	assertTrue(outContent.toString().contains(projLead.getName() + " assigned as project leader for project " + id + " " + projName));
    	
    	assertTrue(newLead);
    	
    	assertSame(newProj.getLead(), projLead);

    	assertSame(projMan.getProjects().iterator().next().getLead(), projLead);
    }
    
    @Test
    public void testAssignProjLeadWrongLead(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	String projName = "Fedt Projekt";
    	Employee projLead = projMan.getEmployee("forkert");

		Map<String, Object> param = new HashMap<String, Object>();
    	
    	// Opret projekt uden project lead.
    	Project newProj = projMan.createProject(projName, param);

    	assertEquals(newProj.getName(), projName);

    	assertNull(newProj.getLead());

    	boolean newLead = newProj.setLead(projLead);

    	assertTrue(errContent.toString().contains("Error: Invalid project leader."));
    	    	
    	assertFalse(newLead);
    	
    	assertNull(newProj.getLead());

    	assertNull(projMan.getProjects().iterator().next().getLead());
    }

    // 4. Som projektleder, vil jeg gerne opdele projekter i aktiviteter, så jeg kan fordele opgaver til medarbejdere.

    @Test
    public void testCreateActivity(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	String projName = "Et eller andet projekt";
    	Employee projLead = projMan.getEmployee("hund");

    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("projLead", projLead);
    	
    	Project newProj = projMan.createProject(projName, param);

    	assertEquals(newProj.getName(), projName);

    	String actName = "En Aktivitet";

    	Set<Employee> actEmployees = new HashSet<Employee>();
    	Employee empOne = projMan.getEmployee("hund");
    	actEmployees.add(empOne);
    	
    	// I ugenumre.
    	int actBudget = 10;
    	String actStart = "14, 2016";
    	String actEnd = "25, 2016";
    	
    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(""));
    	
		Activity newAct = newProj.createActivity(actName, actEmployees, actStart, actEnd, actBudget);

		assertTrue(outContent.toString().contains(actName));
		
		assertSame(newAct.getEmployees(), actEmployees);
		assertSame(newAct.getEmployees().iterator().next(), empOne);

    	Activity foundAct = newProj.getAct(actName);

    	assertSame(newAct, foundAct);
    	assertSame(foundAct.getEmployees(), actEmployees);
    	assertEquals(foundAct.getName(), actName);
    }

    @Test
    public void testCreateActivityWrongEmployee(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	String projName = "Et eller andet projekt";
    	Employee projLead = projMan.getEmployee("hund");

    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("projLead", projLead);
    	
    	Project newProj = projMan.createProject(projName, param);

    	assertEquals(newProj.getName(), projName);

    	String actName = "En Aktivitet";

    	Set<Employee> actEmployees = new HashSet<Employee>();
    	Employee empOne = projMan.getEmployee("forkert");
    	actEmployees.add(empOne);
    	
    	assertNull(empOne);
    	
    	// I ugenumre.
    	int actBudget = 10;
    	String actStart = "14, 2016";
    	String actEnd = "25, 2016";

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(""));
    	
		Activity newAct = newProj.createActivity(actName, actEmployees, actStart, actEnd, actBudget);

		assertTrue(errContent.toString().contains("Error: Invalid employee found."));

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(actName));
		assertSame(newAct.getEmployees(), actEmployees);
		assertEquals(0, newAct.getEmployees().size());
		
    	Activity foundAct = newProj.getAct(actName);

    	assertSame(newAct, foundAct);
    	assertSame(foundAct.getEmployees(), actEmployees);
    	assertEquals(foundAct.getName(), actName);
    }

    @Test
    public void testCreateActivityTwoEmployees(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	String projName = "Et eller andet projekt";
    	Employee projLead = projMan.getEmployee("hund");

    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("projLead", projLead);
    	
    	Project newProj = projMan.createProject(projName, param);

    	assertEquals(newProj.getName(), projName);

    	String actName = "En Aktivitet";

    	Set<Employee> actEmployees = new HashSet<Employee>();
    	Employee empOne = projMan.getEmployee("hund");
    	Employee empTwo = projMan.getEmployee("kat");
    	actEmployees.add(empOne);
    	actEmployees.add(empTwo);
    	    	
    	// I ugenumre.
    	int actBudget = 10;
    	String actStart = "14, 2016";
    	String actEnd = "25, 2016";

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(""));
    	
		Activity newAct = newProj.createActivity(actName, actEmployees, actStart, actEnd, actBudget);

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(actName));
		
		assertSame(newAct.getEmployees(), actEmployees);
		assertEquals(2, newAct.getEmployees().size());
		
		assertTrue(newAct.getEmployees().contains(empOne));
		assertTrue(newAct.getEmployees().contains(empTwo));

    	Activity foundAct = newProj.getAct(actName);

    	assertSame(newAct, foundAct);
    	assertSame(foundAct.getEmployees(), actEmployees);
    	assertEquals(foundAct.getName(), actName);
    }

    @Test
    public void testCreateActivityDupName(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	String projName = "Et eller andet projekt";
    	Employee projLead = projMan.getEmployee("hund");

    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("projLead", projLead);
    	
    	Project newProj = projMan.createProject(projName, param);

    	assertEquals(newProj.getName(), projName);

    	String actName = "En Aktivitet";

    	Set<Employee> actEmployees = new HashSet<Employee>();
    	Employee empOne = projMan.getEmployee("hund");
    	actEmployees.add(empOne);
    	
    	// I ugenumre.
    	int actBudget = 10;
    	String actStart = "14, 2016";
    	String actEnd = "25, 2016";

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(""));
    	
		Activity newAct = newProj.createActivity(actName, actEmployees, actStart, actEnd, actBudget);

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(actName));
		
		assertSame(newAct.getEmployees(), actEmployees);
		assertSame(newAct.getEmployees().iterator().next(), empOne);

    	Activity foundAct = newProj.getAct(actName);

    	assertSame(newAct, foundAct);
    	assertSame(foundAct.getEmployees(), actEmployees);
    	assertEquals(foundAct.getName(), actName);
    	
		Activity newAct2 = newProj.createActivity(actName, actEmployees, actStart, actEnd, actBudget);
		
		assertTrue(errContent.toString().contains("Error: Invalid activity name."));

		assertNull(newAct2);

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(actName));
    }
    
    // 5. Som projektleder, vil jeg gerne kunne tilføje mere end én medarbejder på et projekt og dets aktiviteter,
    //	  så flere medarbejdere kan arbejde på samme projekt.

    @Test
    public void testAddEmployee(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	String projName = "Et eller andet projekt";
    	Employee projLead = projMan.getEmployee("hund");

    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("projLead", projLead);
    	
    	Project newProj = projMan.createProject(projName, param);

    	assertEquals(newProj.getName(), projName);

    	String actName = "En Aktivitet";

    	Set<Employee> actEmployees = new HashSet<Employee>();
    	Employee empOne = projMan.getEmployee("hund");
    	Employee empTwo = projMan.getEmployee("kat");
    	actEmployees.add(empOne);
    	actEmployees.add(empTwo);
    	
    	// I ugenumre.
    	int actBudget = 10;
    	String actStart = "14, 2016";
    	String actEnd = "25, 2016";

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(""));
    	
		Activity newAct = newProj.createActivity(actName, actEmployees, actStart, actEnd, actBudget);

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(actName));
		
		assertSame(newAct.getEmployees(), actEmployees);

		assertTrue(newAct.getEmployees().contains(empOne));
		assertTrue(newAct.getEmployees().contains(empTwo));

    	Activity foundAct = newProj.getAct(actName);

    	assertSame(newAct, foundAct);
    	assertSame(foundAct.getEmployees(), actEmployees);
    	assertEquals(foundAct.getName(), actName);

    	assertSame(newAct, foundAct);
    	assertTrue(foundAct.getEmployees().contains(empOne));
    	assertTrue(foundAct.getEmployees().contains(empTwo));
    	assertEquals(foundAct.getName(), actName);

    	Employee empThree = projMan.getEmployee("fritte");

    	newAct.addEmployee(empThree);

    	assertEquals(3, newAct.getEmployees().size());
    	assertTrue(newAct.getEmployees().contains(empThree));
    	assertTrue(foundAct.getEmployees().contains(empThree));
    }

    @Test
    public void testAddEmployeeWrongEmp(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	String projName = "Et eller andet projekt";
    	Employee projLead = projMan.getEmployee("hund");

    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("projLead", projLead);
    	
    	Project newProj = projMan.createProject(projName, param);

    	assertEquals(newProj.getName(), projName);

    	String actName = "En Aktivitet";

    	Set<Employee> actEmployees = new HashSet<Employee>();
    	Employee empOne = projMan.getEmployee("hund");
    	Employee empTwo = projMan.getEmployee("kat");
    	actEmployees.add(empOne);
    	actEmployees.add(empTwo);
    	
    	// I ugenumre.
    	int actBudget = 10;
    	String actStart = "14, 2016";
    	String actEnd = "25, 2016";

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(""));
    	
		Activity newAct = newProj.createActivity(actName, actEmployees, actStart, actEnd, actBudget);

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(actName));
		
		assertSame(newAct.getEmployees(), actEmployees);

		assertTrue(newAct.getEmployees().contains(empOne));
		assertTrue(newAct.getEmployees().contains(empTwo));

    	Activity foundAct = newProj.getAct(actName);

    	assertSame(newAct, foundAct);
    	assertSame(foundAct.getEmployees(), actEmployees);
    	assertEquals(foundAct.getName(), actName);

    	assertSame(newAct, foundAct);
    	assertTrue(foundAct.getEmployees().contains(empOne));
    	assertTrue(foundAct.getEmployees().contains(empTwo));
    	assertEquals(foundAct.getName(), actName);

    	Employee empThree = projMan.getEmployee("forkert");

    	newAct.addEmployee(empThree);

		assertTrue(errContent.toString().contains("Error: Employee doesn't exist."));
   	
		assertEquals(2, newAct.getEmployees().size());
    	assertFalse(newAct.getEmployees().contains(empThree));
    	assertFalse(foundAct.getEmployees().contains(empThree));
    }

    // x. Som medarbejder vil gerne kunne få en liste over alle employees.

    @Test
    public void getEmployeeList(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	Map<String, Employee> emps = projMan.getEmployeeMap();
    	
		assertTrue(outContent.toString().contains("hund"));
		assertTrue(outContent.toString().contains("kat"));
		assertTrue(outContent.toString().contains("fritte"));

		Employee empsEmp = emps.get("hund");
		Employee projEmp = projMan.getEmployee("hund");
		
		assertSame(empsEmp, projEmp);
    }

    // 6. Som medarbejder, vil jeg gerne kunne registrere hvor meget tid jeg bruger på forskellige aktiviteter hver dag,
    //	  så min projektleder og jeg kan holde øje med tiden.

    @Test
    public void testRegisterTime(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	String projName = "Et eller andet projekt";
    	Employee projLead = projMan.getEmployee("hund");

    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("projLead", projLead);
    	
    	Project newProj = projMan.createProject(projName, param);

    	assertEquals(newProj.getName(), projName);

    	String actName = "En Aktivitet";

    	Set<Employee> actEmployees = new HashSet<Employee>();
    	Employee empOne = projMan.getEmployee("hund");
    	Employee empTwo = projMan.getEmployee("kat");
    	actEmployees.add(empOne);
    	actEmployees.add(empTwo);
    	
    	// I ugenumre.
    	int actBudget = 10;
    	String actStart = "14, 2016";
    	String actEnd = "25, 2016";

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(""));
    	
		Activity newAct = newProj.createActivity(actName, actEmployees, actStart, actEnd, actBudget);

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(actName));
    	
    	String timedate = "2016 08 20 14:30 17:00";
    	
		emp.editWorkTime(timedate, newAct);
		newAct.editWorkTime(timedate, emp);
		
		String date = timedate.substring(0, 10);
		String time = timedate.substring(11);
		String projActName = newProj.getID() + " " + newAct.getName();
		
		// "2016 08 20 14:30 17:00 projName actName"
		emp.getWorkTime(date);
		System.out.println(time + " " + projActName);
		assertTrue(outContent.toString().contains(time + " " + projActName));
		// "2016 08 20 14 30 17 00 employee"
		newAct.getWorkTime(date);
		assertTrue(outContent.toString().contains(time + " " + emp.getName()));
    }
    
    @Test
    public void testRegisterTimeReplace(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	String projName = "Et eller andet projekt";
    	Employee projLead = projMan.getEmployee("hund");

    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("projLead", projLead);
    	
    	Project newProj = projMan.createProject(projName, param);

    	assertEquals(newProj.getName(), projName);

    	String actName = "En Aktivitet";

    	Set<Employee> actEmployees = new HashSet<Employee>();
    	Employee empOne = projMan.getEmployee("hund");
    	Employee empTwo = projMan.getEmployee("kat");
    	actEmployees.add(empOne);
    	actEmployees.add(empTwo);
    	
    	// I ugenumre.
    	int actBudget = 10;
    	String actStart = "14, 2016";
    	String actEnd = "25, 2016";
    	
    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(""));
    	
		Activity newAct = newProj.createActivity(actName, actEmployees, actStart, actEnd, actBudget);

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(actName));
    	
    	String timedate = "2016 08 20 14:30 17:00";
    	
		emp.editWorkTime(timedate, newAct);
		newAct.editWorkTime(timedate, emp);
		
		String date = timedate.substring(0, 10);
		String time = timedate.substring(11);
		String projActName = newProj.getID() + " " + newAct.getName();
		
		// "2016 08 20 14 30 17 00 projName actName"
		emp.getWorkTime(date);
		System.out.println(time + " " + projActName);
		assertTrue(outContent.toString().contains(time + " " + projActName));
		// "2016 08 20 14 30 17 00 employee"
		newAct.getWorkTime(date);
		assertTrue(outContent.toString().contains(time + " " + emp.getName()));
		
		timedate = "2016 08 20 09:00 13:00";
		
		String newTime = timedate.substring(11);
		
		emp.editWorkTime(timedate, newAct);
		newAct.editWorkTime(timedate, emp);
		
		// "2016 08 20 14 30 17 00 projName actName"
		emp.getWorkTime(date);
		System.out.println(time + " " + projActName);
		assertTrue(outContent.toString().contains(newTime + " " + projActName));
		// "2016 08 20 14 30 17 00 employee"
		newAct.getWorkTime(date);
		assertTrue(outContent.toString().contains(newTime + " " + emp.getName()));		
    }

    @Test
    public void testGetHistory(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	String projName = "Et eller andet projekt";
    	Employee projLead = projMan.getEmployee("hund");

    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("projLead", projLead);
    	
    	Project newProj = projMan.createProject(projName, param);

    	assertEquals(newProj.getName(), projName);

    	String actName = "En Aktivitet";

    	Set<Employee> actEmployees = new HashSet<Employee>();
    	Employee empOne = projMan.getEmployee("hund");
    	Employee empTwo = projMan.getEmployee("kat");
    	actEmployees.add(empOne);
    	actEmployees.add(empTwo);
    	
    	// I ugenumre.
    	int actBudget = 10;
    	String actStart = "14, 2016";
    	String actEnd = "25, 2016";

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(""));
    	
		Activity newAct = newProj.createActivity(actName, actEmployees, actStart, actEnd, actBudget);

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(actName));
    	
    	String timedate = "2016 08 20 14:30 17:00";
    	
    	String date1 = "2016 01 21";
    	String date2 = "2017 04 23";
    	
		emp.editWorkTime(timedate, newAct);
		newAct.editWorkTime(timedate, emp);
		
		String date = timedate.substring(0, 10);
		String time = timedate.substring(11);
		String projActName = newProj.getID() + " " + newAct.getName();
		
		// "2016 08 20 14 30 17 00 projName actName"
		emp.getWorkTime(date);
    	assertTrue(outContent.toString().contains(time + " " + projActName));
		emp.getWorkTime(date1 + " " + date2);
    	assertTrue(outContent.toString().contains(timedate + " " + projActName));
    }

    @Test
    public void testGetHistoryWrong(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	String projName = "Et eller andet projekt";
    	Employee projLead = projMan.getEmployee("hund");

    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("projLead", projLead);
    	
    	Project newProj = projMan.createProject(projName, param);

    	assertEquals(newProj.getName(), projName);

    	String actName = "En Aktivitet";

    	Set<Employee> actEmployees = new HashSet<Employee>();
    	Employee empOne = projMan.getEmployee("hund");
    	Employee empTwo = projMan.getEmployee("kat");
    	actEmployees.add(empOne);
    	actEmployees.add(empTwo);
    	
    	// I ugenumre.
    	int actBudget = 10;
    	String actStart = "14, 2016";
    	String actEnd = "25, 2016";

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(""));
    	
		Activity newAct = newProj.createActivity(actName, actEmployees, actStart, actEnd, actBudget);

    	newProj.getActivities(emp);
    	assertTrue(outContent.toString().contains(actName));
    	
    	String timedate = "2016 08 20 14:30 17:00";
    	
    	String date1 = "2016 01 21";
    	String date2 = "2016 04 23";
    	
		emp.editWorkTime(timedate, newAct);
		newAct.editWorkTime(timedate, emp);
		
		String date = timedate.substring(0, 10);
		String time = timedate.substring(11);
		String projActName = newProj.getID() + " " + newAct.getName();
		
		// "2016 08 20 14 30 17 00 projName actName"
		emp.getWorkTime(date);
    	assertTrue(outContent.toString().contains(time + " " + projActName));
    	System.out.println("this is wrong");
		emp.getWorkTime(date1 + " " + date2);
    	assertTrue(outContent.toString().contains("this is wrong"));
    }
}
    
