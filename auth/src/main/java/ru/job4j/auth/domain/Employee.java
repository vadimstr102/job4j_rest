package ru.job4j.auth.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private int identNum;
    private Timestamp hiringDate;
    private List<Person> persons;

    public static Employee of(int id, String firstName, String lastName, int identNum, List<Person> persons) {
        Employee employee = new Employee();
        employee.id = id;
        employee.firstName = firstName;
        employee.lastName = lastName;
        employee.identNum = identNum;
        employee.hiringDate = new Timestamp(System.currentTimeMillis());
        employee.persons = persons;
        return employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getIdentNum() {
        return identNum;
    }

    public void setIdentNum(int identNum) {
        this.identNum = identNum;
    }

    public Timestamp getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(Timestamp hiringDate) {
        this.hiringDate = hiringDate;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
