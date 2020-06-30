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

/**
 * Hello world!
 *
 */
public class App 
{
    private static People runPerson = new PeopleRep();
    private static ToDoItems runTodo = new ToDoItemsRep();

    public static void main( String[] args ) throws SQLException {
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

        //createTodo("Test to set null","Do something",LocalDate.parse("2020-01-01"),false, );

        //findAllTodo();
        //findByIdTodo(11);
        //findDoneTodo(true);

        //Person person = runPerson.findById(4);
        //findByAssignee(person);
        //findAllUnassigned();
        //updateToDo(4,"NEW TITLE","NOTHING NEW",LocalDate.parse("2020-01-01"),false,4);
        deleteToDo(21);
    }
    /** Methods for TODO start **/
    public static void createTodo(String title, String description, LocalDate date, boolean done, int person) throws SQLException {
        connection();
        ToDo todo = new ToDo(title,description,date,done, person);
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

        System.out.println(runTodo.upDate(todoId));
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

    public static void createPerson(String fName, String lName) throws SQLException {
        connection();
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

}
