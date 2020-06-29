package se.lexicon.data;

import se.lexicon.model.Person;

import java.util.Collection;

public class PeopleRep implements People{
    @Override
    public People create(Person person) {
        return null;
    }

    @Override
    public Collection<Person> findAll() {
        return null;
    }

    @Override
    public Person findById(int id) {
        return null;
    }

    @Override
    public Collection<Person> findByName(String name) {
        return null;
    }

    @Override
    public Person upDate(Person person) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
