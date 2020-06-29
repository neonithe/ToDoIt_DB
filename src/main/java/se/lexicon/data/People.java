package se.lexicon.data;

import se.lexicon.model.Person;
import se.lexicon.model.ToDo;

import java.util.Collection;

public interface People {

    People create(Person person);
    Collection<Person> findAll();
    Person findById(int id);
    Collection<Person> findByName(String name);
    Person upDate(Person person);
    boolean deleteById(int id);

}
