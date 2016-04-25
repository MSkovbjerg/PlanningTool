import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
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
    	Employee projLead = projMan.getEmployee("forkert");
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
    	}catch (Exception e){
    		assertTrue(e.getMessage() == "Error: Project not created. Wrong Input.");
    	}
    	
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
    	
    	assertTrue(outContent.toString().contains(projLead + " assigned as project leader for project " + id + " " + projName));
    	
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
    	
    	assertEquals(newProj.getActivities().size(), 0);
    	
		Activity newAct = newProj.createActivity(actName, actEmployees, actStart, actEnd, actBudget);

		assertEquals(newProj.getActivities().size(), 1);
		
		assertSame(newAct.getEmployees(), actEmployees);
		assertSame(newAct.getEmployees().iterator().next(), empOne);

    	Activity foundAct = newProj.getActivities().iterator().next();

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
    	
    	assertEquals(newProj.getActivities().size(), 0);
    	
		Activity newAct = newProj.createActivity(actName, actEmployees, actStart, actEnd, actBudget);

		assertTrue(errContent.toString().contains("Error: Invalid employee found."));
		
		assertEquals(1, newProj.getActivities().size());
		assertSame(newAct.getEmployees(), actEmployees);
		assertEquals(0, newAct.getEmployees().size());
		
    	Activity foundAct = newProj.getActivities().iterator().next();

    	assertSame(newAct, foundAct);
    	assertSame(foundAct.getEmployees(), actEmployees);
    	assertEquals(foundAct.getName(), actName);
    }

    /* 5. Som projektleder, vil jeg gerne kunne tilføje mere end én medarbejder på et projekt og dets aktiviteter,
    	  så flere medarbejdere kan arbejde på samme projekt. *//*
    @Test
    public void testAddEmployee(){
    	ProjectManager projMan = new ProjectManager();

    	Login li = new Login();
    	Employee emp = li.signIn("hund");
		assertNotNull(emp);

    	String projName = "Et eller andet projekt";
    	Employee projLead = projMan.getEmployee("hund");

    	Project newProj = projMan.createProject(projName, projLead);

    	assertEquals(newProj.getName(), projName);

    	String actName = "En Aktivitet";

    	Set<Employee> actEmployees = new HashSet<Employee>();
    	Employee empOne = projMan.getEmployee("hund");
    	Employee empTwo = projMan.getEmployee("kat");
    	actEmployees.add(empOne);
    	actEmployees.add(empTwo);

		Activity newAct = projMan.createActivity(actName, actEmployees);

		assertSame(newAct.getEmployees(), actEmployees);
		assertTrue(newAct.getEmployees().contains(empOne));
		assertTrue(newAct.getEmployees().contains(empTwo));

    	assertEquals(newProj.getActivities().size(), 0);

    	newProj.addActivity(newAct);

    	assertEquals(newProj.getActivities.size(), 1);

    	Activity foundAct = newProj.getActivities().iterator().next();

    	assertSame(newAct, foundAct);
    	assertTrue(foundAct.getEmployees().contains(empOne));
    	assertTrue(foundAct.getEmployees().contains(empTwo));
    	assertEquals(foundAct.getName(), actName);

    	Employee empThree = projMan.getEmployee("fritte");

    	foundAct.addEmployee(empThree);

    	assertTrue(foundAct.getEmployees().contains(empThree));
    }
*/
    /* 6. Som medarbejder, vil jeg gerne kunne registrere hvor meget tid jeg bruger på forskellige aktiviteter hver dag,
    	  så min projektleder og jeg kan holde øje med tiden. *//*
    @Test
    public void testRegisterTime(){

    }
*//*
    // 7. Som projektleder, vil jeg gerne kunne se, hvilke medarbejdere som er tilgængelige for et projekt, så jeg kan tildele det rette antal.
    @Test
    public void testCheckEmployeeAvailable(){

    }
*//*
    // 8. Som medarbejder, vil jeg gerne kunne arbejde på op til 20 aktiviteter på én uge, så jeg arbejdsbyrden ikke bliver for stor. 
    @Test
    public void testActivityLimit(){

    }
*//*
    // 9. Som medarbejder, vil jeg gerne registrere fremtidige aktiviteter som fx. ferie, så min projektleder kan planlægge projekter uden mig.
    @Test
    public void testRegisterFutureTime(){

    }
*//*
    // 10. Som medarbejder, vil jeg gerne kunne angive start- og sluttid for aktiviteter i ugenumre, så jeg kan planlægge lang tid inden projektet starter.
    @Test
    public void testPlanActivities(){

    }
*//*
    // 11. Som projektleder, vil jeg gerne se hvordan tiden udvikler sig pr. aktivitet og for hele projektet, så jeg kan bruge systemet til at følge op. 
    @Test
    public void testFollowUp(){

    }
*/
    /* 12. Som medarbejder, vil jeg gerne have muligheden for at få assistance til en aktivitet fra en anden medarbejder,
    så projektets deadline bliver overholdt i forhold til den estimerede tid. *//*
    @Test
    public void testAssistance(){

    }
*//*
    // 13. Som projektleder, vil jeg gerne kunne ændre planer når jeg vil, så et projekt kan planlægges længe før projektets start.
    @Test
    public void testAlterProject(){

    }
*//*
    // 14. Som medarbejder, vil jeg gerne have muligheden for at redigere i allerede registreret data, så jeg kan rette eventuelle fejl.
    @Test
    public void testEditTimeData(){

    }
*//*
    // 15. Som projektleder, vil jeg gerne angive et estimeret antal arbejdstimer for hver aktivitet, så jeg kan få et overblik over tiden for et projekt.
    @Test
    public void testActivityTimeEstimate(){

    }
*/
}
    
