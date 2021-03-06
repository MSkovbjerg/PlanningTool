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

public class testOpretProjekt{
	// Det her i starten s�rger for at testen ogs� kan l�se output til konsollen.
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

	// 1. Som medarbejer, vil jeg gerne kunne logge ind i systemet, s� jeg kan oprette et projekt.
	
	// Emil
	// Login korrekt.
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

	// Emil
	// Login med ugyldige initialer.
	@Test
	public void testLoginFailed(){
		ProjectManager projMan = new ProjectManager();
		Login li = new Login(projMan);

		// Alle er lige i login, man er kun manager per projekt.
		assertNull(li.loggedIn());

		// Alt man indtaster bliver en String, s� det er kun Strings der beh�ver at testes.
		Employee emp = li.signIn("forkert");
		assertTrue(errContent.toString().contains("Error: Wrong login."));
		
		assertTrue(projMan.getEmployeeMap().get("forkert") == emp);
		// Hvis man er logget ind er emp ikke Null, ellers er emp Employee objektet for den medarbejder.
		assertNull(emp);
		assertSame(li.loggedIn(), emp);
	}

    // 2. Som medarbejder, vil jeg gerne kunne oprette et projekt, s� jeg kan have overblik over aktiviteter og medarbejdere.

	// Emil
	// Opret projekt korrekt.
	@Test
    public void testCreateProject(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	// Kun projName er obligatorisk. De andre er valgfri. ID er automatisk.
    	String projName = "Fedt Projekt";
    	// getEmployee skal give �n ansat ud fra navn, getEmployeeSet skal give dem alle sammen.
    	Employee projLead = projMan.getEmployee("hund");
    	// Projekt-tidslinjen skal v�re per uge. Der skal dog ogs� bruges datoer senere, men Calender kan konvertere i mellem dem.
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

    	// Vi beh�ver kun at finde projektet p� denne m�de i test, s� det g�r ikke s� meget.
    	// Det er besv�rligt i test, og kan g�res lidt anderledes, men normalt s� har man allerede projektet
    	// f.eks. fra en liste hvilket man s� sl�r op i Settet.
    	Project foundProj = projMan.getProjects().iterator().next();
    	assertSame(foundProj, newProj);
    	assertEquals(foundProj.getName(), projName);
    	assertSame(foundProj.getLead(), projLead);
    	assertEquals(foundProj.getStartDate(), projStart);
    	assertEquals(foundProj.getEndDate(), projEnd);

    	// Tjekker om ID er 10 cifre langt og om de f�rste 4 cifre er �rstallet.
    	int id = foundProj.getID();
    	
    	assertTrue(outContent.toString().contains("Project " + id + " " + projName + " created."));
    	
    	assertEquals(10, String.valueOf(id).length()) ;
    	assertEquals(year, Integer.parseInt(Integer.toString(id).substring(0, 4)));
    }

	// Emil
	// Opret et projekt med kun et navn.
    @Test
    public void testCreateProjectEmpty(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	// Kun projName er obligatorisk. De andre er valgfri. ID er automatisk.
    	String projName = "Fedt Projekt";
    	// getEmployee skal give �n ansat ud fra navn, getEmployeeSet skal give dem alle sammen.
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

    	// Vi beh�ver kun at finde projektet p� denne m�de i test, s� det g�r ikke s� meget.
    	// Det er besv�rligt i test, og kan g�res lidt anderledes, men normalt s� har man allerede projektet
    	// f.eks. fra en liste hvilket man s� sl�r op i Settet.
    	Project foundProj = projMan.getProjects().iterator().next();
    	assertSame(foundProj, newProj);
    	assertEquals(foundProj.getName(), projName);
    	assertNull(foundProj.getLead());
    	assertNull(foundProj.getStartDate());
    	assertNull(foundProj.getEndDate());

    	// Tjekker om ID er 10 cifre langt og om de f�rste 4 cifre er �rstallet.
    	int id = foundProj.getID();
    	
    	assertTrue(outContent.toString().contains("Project " + id + " " + projName + " created."));
    	
    	assertEquals(10, String.valueOf(id).length()) ;
    	assertEquals(year, Integer.parseInt(Integer.toString(id).substring(0, 4)));
    }

    // Emil
    // Opret projekt med kun projektleder og startdato men uden slutdato.
    @Test
    public void testCreateProjectTwoVar(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	// Kun projName er obligatorisk. De andre er valgfri. ID er automatisk.
    	String projName = "Fedt Projekt";
    	// getEmployee skal give �n ansat ud fra navn, getEmployeeSet skal give dem alle sammen.
    	Employee projLead = projMan.getEmployee("hund");
    	// Projekt-tidslinjen skal v�re per uge. Der skal dog ogs� bruges datoer senere, men Calender kan konvertere i mellem dem.
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

    	// Vi beh�ver kun at finde projektet p� denne m�de i test, s� det g�r ikke s� meget.
    	// Det er besv�rligt i test, og kan g�res lidt anderledes, men normalt s� har man allerede projektet
    	// f.eks. fra en liste hvilket man s� sl�r op i Settet.
    	Project foundProj = projMan.getProjects().iterator().next();
    	assertSame(foundProj, newProj);
    	assertEquals(foundProj.getName(), projName);
    	assertSame(foundProj.getLead(), projLead);
    	assertEquals(foundProj.getStartDate(), projStart);
    	assertNull(foundProj.getEndDate());

    	// Tjekker om ID er 10 cifre langt og om de f�rste 4 cifre er �rstallet.
    	int id = foundProj.getID();
    	
    	assertTrue(outContent.toString().contains("Project " + id + " " + projName + " created."));
    	
    	assertEquals(10, String.valueOf(id).length()) ;
    	assertEquals(year, Integer.parseInt(Integer.toString(id).substring(0, 4)));
    }

    // Emil
    // Opret projekt med en projektleder der ikke eksisterer.
    @Test
    public void testCreateProjectWrongLead(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	// Kun projName er obligatorisk. De andre er valgfri. ID er automatisk.
    	String projName = "Nederen Projekt";
    	// getEmployee skal give �n ansat ud fra navn, getEmployeeSet skal give dem alle sammen.
    	Employee projLead = projMan.getEmployee("forkert");
    	// Projekt-tidslinjen skal v�re per uge. Der skal dog ogs� bruges datoer senere, men Calender kan konvertere i mellem dem.
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

    // Emil
    // Opret projekt med ugyldig start-tidspunkt.
    @Test
    public void testCreateProjectWrongStart(){
    	ProjectManager projMan = new ProjectManager();
    	Login li = new Login(projMan);
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	// Kun projName er obligatorisk. De andre er valgfri. ID er automatisk.
    	String projName = "Nederen Projekt";
    	// getEmployee skal give �n ansat ud fra navn, getEmployeeSet skal give dem alle sammen.
    	Employee projLead = projMan.getEmployee("hund");
    	// Projekt-tidslinjen skal v�re per uge. Der skal dog ogs� bruges datoer senere, men Calender kan konvertere i mellem dem.
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

    // Emil
    // 3. Som medarbejder, vil jeg gerne kunne tildele en projektleder til et projekt, s� nogen kan lede projektet.
    
    // Tildel projektleder korrekt.
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
    
    // Emil
    // Tildel projektleder der ikke eksisterer.
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

    // 4. Som projektleder, vil jeg gerne opdele projekter i aktiviteter, s� jeg kan fordele opgaver til medarbejdere.

    // Michael
    // Opret aktivitet korrekt.
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

    // Michael
    // Opret aktivitet med en ansat der ikke eksisterer.
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

    // Michael
    // Opret aktivitet med to ansatte sat p�.
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
    
    // Michael
    // Hvis en aktivitet bliver oprettet med samme navn som en anden.
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
    
    // Tilf�j medarbejder til eksisterende aktivitet i projekt.
    
    // Michael
    // Alting korrekt.
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

    // Michael
    // Med en medarbejder der ikke eksisterer.
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

    // Som medarbejder vil gerne kunne f� en liste over alle employees.
    
    // Rikke
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

    // 6. Som medarbejder, vil jeg gerne kunne registrere hvor meget tid jeg bruger p� forskellige aktiviteter hver dag,
    //	  s� min projektleder og jeg kan holde �je med tiden.

    // Rikke
    // Registrer tid korrekt.
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
		assertTrue(outContent.toString().contains(time + " " + projActName));
		// "2016 08 20 14:30 17:00 employee"
		newAct.getWorkTime(date);
		assertTrue(outContent.toString().contains(time + " " + emp.getName()));
		
		// "2016 08 20"
		projMan.getEmployeeWorkDates(emp);
		assertTrue(outContent.toString().contains(time));
    }
    
    // Rikke
    // Tilf�j flere arbejdstider p� samme dag.
    @Test
    public void testRegisterTimeAdd(){
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
		assertTrue(outContent.toString().contains(time + " " + projActName));
		// "2016 08 20 14:30 17:00 employee"
		newAct.getWorkTime(date);
		assertTrue(outContent.toString().contains(time + " " + emp.getName()));
		
		timedate = "2016 08 20 09:00 13:00";
		
		String newTime = timedate.substring(11);
		
		emp.editWorkTime(timedate, newAct);
		newAct.editWorkTime(timedate, emp);
		
		// "2016 08 20 14:30 17:00 projName actName"
		emp.getWorkTime(date);
		System.out.println(time + " " + projActName);
		assertTrue(outContent.toString().contains(time + " " + projActName));
		assertTrue(outContent.toString().contains(newTime + " " + projActName));
		// "2016 08 20 14:30 17:00 employee"
		newAct.getWorkTime(date);
		assertTrue(outContent.toString().contains(time + " " + emp.getName()));	
		assertTrue(outContent.toString().contains(newTime + " " + emp.getName()));		
    }

    // 7. Afl�sning af arbejdshistorik.
    
    // Rikke
    // Hent arbejdshistorik korrekt.
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
		
		// "2016 08 20 14:30 17:00 projName actName"
		emp.getWorkTime(date);
    	assertTrue(outContent.toString().contains(time + " " + projActName));
		emp.getWorkTime(date1 + " " + date2);
    	assertTrue(outContent.toString().contains(timedate + " " + projActName));
    	
		newAct.getWorkTime(date);
    	assertTrue(outContent.toString().contains(time + " " + emp.getName()));
		newAct.getWorkTime(date1 + " " + date2);
    	assertTrue(outContent.toString().contains(timedate + " " + emp.getName()));
    }

    // Rikke
    // Hent arbejdshistorik med forkert dato.
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
		
		// "2016 08 20 14:30 17:00 projName actName"
		emp.getWorkTime(date1 + " " + date2);
    	assertFalse(outContent.toString().contains(time + " " + projActName));
		emp.getWorkTime(date);
    	assertTrue(outContent.toString().contains(time + " " + projActName));
    }
}
    
