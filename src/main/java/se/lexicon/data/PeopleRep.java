package se.lexicon.data;

import se.lexicon.entity.DbSource;
import se.lexicon.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeopleRep implements People{

    // SQL Command lines queries
    private static final String FIND_ALL     = "SELECT * FROM person";
    private static final String FIND_BY_ID   = "SELECT * FROM person WHERE person_id = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM person WHERE first_name LIKE ?";
    private static final String UPDATE       = "UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ?";
    private static final String DELETE       = "DELETE FROM person WHERE person_id = ?";

    @Override
    public Person create(Person person) {

        if (person.getId() != 0){
            throw new IllegalArgumentException("Use update instead");
        }

        Person persons = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet keySet = null;

        //SQL Command Line
        String command = "INSERT INTO person (first_name, last_name) VALUES (?,?)";

        try {
            connection = DbSource.getConnection();
            statement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.execute();

            keySet = statement.getGeneratedKeys();
            while (keySet.next()) {
                persons = new Person(
                        keySet.getInt(1),
                        person.getFirstName(),
                        person.getLastName()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, statement, keySet);
        }
        return persons == null ? person : persons;
    }

    @Override
    public List<Person> findAll() {

        List<Person> personList = new ArrayList<>();

        try(Connection connection = DbSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                personList.add(createPersonResultSet(resultSet));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    @Override
    public Person findById(int id) {

        Person person = null;

        try(Connection connection = DbSource.getConnection();
            PreparedStatement statement = createFindIdStat(connection, id);
            ResultSet resultSet = statement.executeQuery()){

            while(resultSet.next()){
               person = createPersonResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return person;
    }

    @Override
    public List<Person> findByName(String name) {
        List<Person> personList = new ArrayList<>();

        try(Connection connection = DbSource.getConnection();
            PreparedStatement statement = createFindByName(connection, name);
            ResultSet resultSet = statement.executeQuery()) {

            while(resultSet.next()){
                personList.add(createPersonResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    @Override
    public Person upDate(Person person) {
        if(person.getId() == 0){
            throw new IllegalArgumentException("Cannot update, there is no person by that ID");
        }
        try(Connection connection = DbSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE)){

            statement.setString(1,person.getFirstName());
            statement.setString(2,person.getLastName());
            statement.setInt(3,person.getId());


            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public boolean deleteById(int id) {

        boolean deleted = false;

        try(Connection connection = DbSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE)){
                statement.setInt(1,id);
                if(statement.executeUpdate() != 0){
                    System.out.println("Person deleted from database");
                    deleted = true;
                } else {
                    System.out.println("There is no one with that ID");
                    deleted = false;
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    /** Create sets **/
    public Person createPersonResultSet(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getInt("person_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name")
        );
    }
    public PreparedStatement createFindIdStat(Connection connection, int id) throws SQLException {
        PreparedStatement statement =connection.prepareStatement(FIND_BY_ID);
        statement.setInt(1,id);
        return statement;
    }
    public PreparedStatement createFindByName(Connection connection, String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME);
        statement.setString(1,name.concat("%"));
        return statement;
    }

    /**  Close all connections to DB  **/
    public static void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet){
    try{
        if(resultSet != null){
            resultSet.close();
        }
        if(statement != null){
            statement.close();
        }
        if(connection != null){
            connection.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
}
