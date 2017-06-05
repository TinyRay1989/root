package com.git.yanlei.jpa;

import org.junit.Test;

import com.git.yanlei.jpa.Person;

import static org.junit.Assert.*;

public class PersonTest {
    @Test
    public void canConstructAPersonWithAName() {
        Person person = new Person("Larry");
        assertEquals("Larry", person.getName());
    }
}
