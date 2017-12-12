package com.vsvet.example.onlinebanking;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public abstract class AbstractTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
}
