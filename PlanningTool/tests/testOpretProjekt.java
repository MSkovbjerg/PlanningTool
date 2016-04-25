import static org.junit.Assert.*;

import org.junit.Test;


//Skal nok deles op i flere klasser. Dog i starten med happy path vil dette nok v�re bedst.
public class testOpretProjekt{
/*
	// 1. Som medarbejer, vil jeg gerne kunne logge ind i systemet, s� jeg kan oprette et projekt.
	@Test
	public void testLogin(){
		ProjectManager projMan = new ProjectManager();
		Login li = new Login(projMan);

		// Alle er lige i login, man er kun manager per projekt.
		assertNull(li.loggedIn());

		Employee emp = li.signIn("hund");

		assertTrue(projMan.getEmployeeMap().get("hund") == emp);
		// Hvis man er logget ind er emp ikke Null, ellers er emp Employee objektet for den medarbejder.
		assertNotNull(emp);
		assertSame(li.loggedIn(), emp);
	}
*/
    // 2. Som medarbejder, vil jeg gerne kunne oprette et projekt, s� jeg kan have overblik over aktiviteter og medarbejdere.
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

    	Project newProj = projMan.createProject(projName, projLead, projStart, projEnd);

    	assertEquals(newProj.getName(), projName);
    	assertSame(newProj.getLead(), projLead);

    	assertEquals(newProj.getStartDate(), projStart);
    	assertEquals(newProj.getEndDate(), projEnd);

    	// Vi skal lave en funktion i ProjectManager der returner alle projekter her kaldet getProjects()
    	assertEquals(projMan.getProjects().size(), 1);

    	/* Vi beh�ver kun at finde projektet p� denne m�de i test, s� det g�r ikke s� meget.
    	Det er besv�rligt i test, og kan g�res lidt anderledes, men normalt s� har man allerede projektet
    	f.eks. fra en liste hvilket man s� sl�r op i Settet. */
    	Project foundProj = projMan.getProjects().iterator().next();
    	assertSame(foundProj, newProj);
    	assertEquals(foundProj.getName(), projName);
    	assertSame(foundProj.getLead(), projLead);
    	assertEquals(foundProj.getStartDate(), projStart);
    	assertEquals(foundProj.getEndDate(), projEnd);

    	// Tjekker om ID er 10 cifre langt og om de f�rste 4 cifre er �rstallet.
    	int id = foundProj.getID();
    	assertEquals(10, String.valueOf(id).length()) ;
    	assertEquals(year, Integer.parseInt(Integer.toString(id).substring(0, 4)));
    }
/*
    // 3. Som medarbejder, vil jeg gerne kunne tildele en projektleder til et projekt, s� nogen kan lede projektet.
    @Test
    public void testAssignProjLead(){
    	ProjectManager projMan = new ProjectManager();

    	Login li = new Login();
    	Employee emp = li.signIn("hund");

    	assertNotNull(emp);

    	String projName = "Nederen Projekt";
    	Employee projLead = projMan.getEmployee("hund");

    	// Opret projekt uden project lead.
    	Project newProj = projMan.createProject(projName);

    	assertEquals(newProj.getName(), projName);

    	// Ingen lead er bare en tom string.
    	assertEquals(newProj.getLead(), "");

    	newProj.setLead(projLead);

    	assertSame(newProj.getLead(), projLead);

    	assertSame(projMan.getProjects().iterator().next().getLead(), projLead);
    }
*//*
    // 4. Som projektleder, vil jeg gerne opdele projekter i aktiviteter, s� jeg kan fordele opgaver til medarbejdere.
    @Test
    public void testCreateActivity(){
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
    	actEmployees.add(empOne);

		Activity newAct = projMan.createActivity(actName, actEmployees);

		assertSame(newAct.getEmployees(), actEmployees);
		assertSame(newAct.getEmployees().iterator().next(), empOne);

    	assertEquals(newProj.getActivities().size(), 0);

    	newProj.addActivity(newAct);

    	assertEquals(newProj.getActivities.size(), 1);

    	Activity foundAct = newProj.getActivities.iterator().next();

    	assertSame(newAct, foundAct);
    	assertSame(foundAct.getEmployees(), actEmployees);
    	assertEquals(foundAct.getName(), actName);
    }
*/
    /* 5. Som projektleder, vil jeg gerne kunne tilf�je mere end �n medarbejder p� et projekt og dets aktiviteter,
    	  s� flere medarbejdere kan arbejde p� samme projekt. *//*
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
    /* 6. Som medarbejder, vil jeg gerne kunne registrere hvor meget tid jeg bruger p� forskellige aktiviteter hver dag,
    	  s� min projektleder og jeg kan holde �je med tiden. *//*
    @Test
    public void testRegisterTime(){

    }
*//*
    // 7. Som projektleder, vil jeg gerne kunne se, hvilke medarbejdere som er tilg�ngelige for et projekt, s� jeg kan tildele det rette antal.
    @Test
    public void testCheckEmployeeAvailable(){

    }
*//*
    // 8. Som medarbejder, vil jeg gerne kunne arbejde p� op til 20 aktiviteter p� �n uge, s� jeg arbejdsbyrden ikke bliver for stor. 
    @Test
    public void testActivityLimit(){

    }
*//*
    // 9. Som medarbejder, vil jeg gerne registrere fremtidige aktiviteter som fx. ferie, s� min projektleder kan planl�gge projekter uden mig.
    @Test
    public void testRegisterFutureTime(){

    }
*//*
    // 10. Som medarbejder, vil jeg gerne kunne angive start- og sluttid for aktiviteter i ugenumre, s� jeg kan planl�gge lang tid inden projektet starter.
    @Test
    public void testPlanActivities(){

    }
*//*
    // 11. Som projektleder, vil jeg gerne se hvordan tiden udvikler sig pr. aktivitet og for hele projektet, s� jeg kan bruge systemet til at f�lge op. 
    @Test
    public void testFollowUp(){

    }
*/
    /* 12. Som medarbejder, vil jeg gerne have muligheden for at f� assistance til en aktivitet fra en anden medarbejder,
    s� projektets deadline bliver overholdt i forhold til den estimerede tid. *//*
    @Test
    public void testAssistance(){

    }
*//*
    // 13. Som projektleder, vil jeg gerne kunne �ndre planer n�r jeg vil, s� et projekt kan planl�gges l�nge f�r projektets start.
    @Test
    public void testAlterProject(){

    }
*//*
    // 14. Som medarbejder, vil jeg gerne have muligheden for at redigere i allerede registreret data, s� jeg kan rette eventuelle fejl.
    @Test
    public void testEditTimeData(){

    }
*//*
    // 15. Som projektleder, vil jeg gerne angive et estimeret antal arbejdstimer for hver aktivitet, s� jeg kan f� et overblik over tiden for et projekt.
    @Test
    public void testActivityTimeEstimate(){

    }
*/
}
    
