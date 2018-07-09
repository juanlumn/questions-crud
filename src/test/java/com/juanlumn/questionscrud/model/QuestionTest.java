package com.juanlumn.questionscrud.model;

import org.junit.Test;
import org.meanbean.test.BeanTester;

public class QuestionTest {

    @Test
    public void getterAndSetterCorrectness() {
        new BeanTester().testBean(Question.class);
    }
}