package com.example.springreactprac.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.emitter.Emitable;

class EmailTest {

    @Test
    public void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            Email email = new Email("acccc");
        }) ;
    }

    @Test
    public void testValidEmail() {
        Email email = new Email("acccc@gmail.com");
        assertTrue(email.getAddress().equals("acccc@gmail.com"));

    }

    @Test
    public void testEqualEmail() {
        Email email1 = new Email("acccc@gmail.com");
        Email email2 = new Email("acccc@gmail.com");

        assertEquals(email1,email2);

    } 

}