package se.lexicon;

import se.lexicon.data.People;
import se.lexicon.data.PeopleRep;
import se.lexicon.data.ToDoItems;
import se.lexicon.data.ToDoItemsRep;
import se.lexicon.entity.DbSource;
import se.lexicon.model.Person;
import se.lexicon.model.ToDo;

import java.sql.SQLException;

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
        findById(1);
        //findAlles();
        //findByName("Ma");
        delete(8);
        /** Test person end **/




    }
    public static void delete(int id){

        if(runPerson.deleteById(id)){
            System.out.println("Person deleted");
        } else {
            System.out.println("Cannot delete");
        }

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

}
