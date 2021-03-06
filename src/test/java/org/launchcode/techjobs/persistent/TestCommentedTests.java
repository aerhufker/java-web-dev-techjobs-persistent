package org.launchcode.techjobs.persistent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by LaunchCode
 */
public class TestCommentedTests extends AbstractTest {

    @Test
    public void testTaskTwoTestNumber () throws ClassNotFoundException {
        final Class testTaskTwoClass = getClassByName("TestTaskTwo");
        final int numTests = testTaskTwoClass.getMethods().length;
        assertEquals(36, numTests);
    }

    @Test
    public void testTaskThreeTestNumber () throws ClassNotFoundException {
        final Class testTaskThreeClass = getClassByName("TestTaskThree");
        final int numTests = testTaskThreeClass.getMethods().length;
        assertEquals(16, numTests);
    }

    @Test
    public void testTaskFourTestNumber () throws ClassNotFoundException {
        final Class testTaskTwoClass = getClassByName("TestTaskFour");
        final int numTests = testTaskTwoClass.getMethods().length;
        assertEquals(19, numTests);
    }
}
