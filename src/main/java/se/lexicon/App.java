package se.lexicon;

import se.lexicon.data.People;
import se.lexicon.data.PeopleRep;
import se.lexicon.data.ToDoItems;
import se.lexicon.data.ToDoItemsRep;
import se.lexicon.entity.DbSource;
import se.lexicon.model.Person;
import se.lexicon.model.ToDo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    private static People runPerson = new PeopleRep();
    private static ToDoItems runTodo = new ToDoItemsRep();

    public static void main( String[] args ) throws SQLException {

        connection();
        boolean runUntil = true;
        String selection ="";
        do{
            System.out.println("--------------------------- Menu ---------------------------");
            System.out.println("1: Create Person             |  6: Change todo to done or not\n"+
                               "2: Create Todo               |  7: Show all Persons\n"+
                               "3: Add assignee              |  8: Show all todos\n"+
                               "4: Change assignee           |  9: Find (todos or persons)\n"+
                               "5: Update (todos or persons  |  0: Delete (todos or persons\n"+
                               "Q: quit");
            System.out.print("Selection:>");
            selection = input();
            switch (selection){
                case "1":
                    createPerson();
                    break;
                case "2":
                    createTodo();
                    break;
                case "3":
                    addPersonToDo();
                    break;
                case "4":
                    changePersonId();
                    break;
                case "Q":
                    System.out.println("Application Terminated");
                    runUntil = false;
                    break;
                default:
                    System.out.println("Unknown selection, please try again");
            }

        }while(runUntil);

    }

    /** Functions to have
     * Change to done or not
     * Change assignee or add assignee
     * Create person
     * Create todo
     * **/

    /** Functions **/
    public static String input(){
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    public static void menu(){

    }

    /** END Functions **/

    public static ToDo getToDo(int id){
        return runTodo.findById(id);
    }

    public static void setToDoneOrNot(int id, boolean done){
        getToDo(id).setDone(done);

        String status = "";
            if(done != false){
                status = "done";
            } else {
                status = "not done";
            }
            System.out.println("Todo ID: "+id+" is set to ["+status+"]");
    }

    public static void addPersonToDo(){
        System.out.print("Get Todo, enter ID:");
            int id = Integer.parseInt(input());
        System.out.print("Set assignee, enter ID:");
            int personId = Integer.parseInt(input());

        if(getToDo(id) != null){
            getToDo(id).setAssigneeId(personId);
            System.out.println("Person ID: "+personId+" added to todo ID: "+id);
        } else {
            System.out.println("Cannot find that todo ID: "+id);
        }
    }
    public static void changePersonId(){
        java.lang.Integer personIn = null;

        System.out.print("Get Todo, enter ID:");
            int id = Integer.parseInt(input());
        System.out.println("Set assignee [ Leave empty = no assignee ] [ enter number person ID ]");
        System.out.print("Set assignee :");
            String person = input();

        if(person.isEmpty()){
            personIn = null;
        } else {
            int i = Integer.parseInt(person);
            personIn = i;
        }

        ToDo todoId = runTodo.findById(id);
        todoId.setAssigneeId(personIn);
        runTodo.upDate(todoId);
        System.out.println("Person changed");
    }


    /** Methods for TODO start **/
    public static void createTodo() throws SQLException {
        java.lang.Integer personIn = null;

        System.out.print("Enter title:");
            String title = input();
        System.out.print("Enter description:");
            String desc = input();
        System.out.print("Enter date (0000-00-00):");
            String date = input();
        System.out.print("Enter done or not (true or false):");
            String doneIn = input();
        System.out.print("Enter assignee (ID or leave empty):");
            String person = input();

            if(person.isEmpty()){
                personIn = null;
            } else {
                int i = Integer.parseInt(person);
                personIn = i;
            }

        // Convert from string to boolean, integer and localdate
        boolean done = Boolean.parseBoolean(doneIn);
        LocalDate newDate = LocalDate.parse(date);

        ToDo todo = new ToDo(title, desc, newDate, done, personIn);
        runTodo.create(todo);
        System.out.println("Todo created to database");
    }
    public static void findAllTodo() throws SQLException {
        connection();
        runTodo.findAll().forEach(System.out::println);
    }
    public static void findByIdTodo(int id) throws SQLException {
        connection();
        if(runTodo.findById(id) != null){
            System.out.println(runTodo.findById(id));
        } else {
            System.out.println("Cannot find person by id");
        }
    }
    public static void findDoneTodo(boolean done) throws SQLException {
        connection();
        runTodo.findByDoneStatus(done).forEach(System.out::println);
    }
    public static void findByAssignee(Person person) throws SQLException {
        connection();
        runTodo.findByAssignee(person).forEach(System.out::println);
    }
    public static void findAllUnassigned() throws SQLException {
        connection();
        runTodo.findByUnassignedToDoItems().forEach(System.out::println);
    }
    public static void updateToDo(int findId, String title, String desc, LocalDate date, boolean done, int id) throws SQLException {
        connection();
        ToDo todoId = runTodo.findById(findId);

        todoId.setTitle(title);
        todoId.setDescription(desc);
        todoId.setDeadLine(date);
        todoId.setDone(done);
        todoId.setAssigneeId(id);

        runTodo.upDate(todoId);
        System.out.println("ToDo updated");
    }
    public static void deleteToDo(int id) throws SQLException {
        connection();
        runTodo.deleteById(id);
    }
    /** Methods for TODO end **/


    /** Methods for person start **/
    public static void delete(int id) throws SQLException {
        connection();
        runPerson.deleteById(id);
    }
    public static void connection() throws SQLException {
        System.out.println("Database connection established");
        DbSource.getConnection();
    }
    public static void createPerson() throws SQLException {
        System.out.print("Enter first name:");
            String fName = input();
        System.out.print("Enter last name:");
            String lName = input();

        Person person = new Person(fName,lName);
        runPerson.create(person);
        System.out.println("Person created to database");
    }
    public static void update(String fName, String lName) throws SQLException {
        connection();
        Person person = runPerson.findById(4);

        person.setFirstName(fName);
        person.setLastName(lName);

        System.out.println(runPerson.upDate(person));
        System.out.println("Person updated");
    }
    public static void findAlles() throws SQLException {
        connection();
        runPerson.findAll().forEach(System.out::println);
    }
    public static void findById(int id) throws SQLException {
        connection();
        if(runPerson.findById(id) != null){
            System.out.println(runPerson.findById(id));
        } else {
            System.out.println("Cannot find person by id");
        }

    }
    public static void findByName(String name) throws SQLException {
        connection();
        runPerson.findByName(name).forEach(System.out::println);
    }
    /** Methods for person end **/

    // Still todos is delete what to return as true or false

    /** Tests person **/
    //createPerson("Sofia2", "Sonesson2");
    //update("Emma", "Svensson");
    //findById(1);
    //findAlles();
    //findByName("Ma");
    //delete(10);
    /** Test person end **/

    /** Test ToDo **/
    //createTodo("Test > to set null","Do something",LocalDate.parse("2020-01-01"),false, null);

    //findAllTodo();
    //findByIdTodo(111);
    //findDoneTodo(true);

    //Person person = runPerson.findById(4);
    //findByAssignee(person);

    //findAllUnassigned();
    //updateToDo(4,"NEW TITLE","NOTHING NEW",LocalDate.parse("2020-01-01"),false,4);
    //deleteToDo(21);

    //addPersonToDo(7,9);
    //setToDoneOrNot(5,false);
    /**  Test ToDo END  **/


}
