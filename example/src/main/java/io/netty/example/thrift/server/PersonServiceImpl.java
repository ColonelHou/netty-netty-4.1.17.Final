package io.netty.example.thrift.server;

import io.netty.example.thrift.interfaces.DataException;
import io.netty.example.thrift.interfaces.Person;
import io.netty.example.thrift.interfaces.PersonService;
import org.apache.thrift.TException;

public class PersonServiceImpl implements PersonService.Iface {
    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("Get Client username " + username);
        Person p = new Person();
        p.setAge(20);
        p.setUsername(username);
        p.setMarried(false);
        return p;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("Get Client Param: ");
        System.out.println(person.getUsername() + ", " + person.getAge() + ", " + person.isMarried());
    }
}
