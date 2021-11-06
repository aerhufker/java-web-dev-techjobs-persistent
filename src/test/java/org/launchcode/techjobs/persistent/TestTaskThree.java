package org.launchcode.techjobs.persistent;

import org.junit.jupiter.api.Test;
import org.launchcode.persistent.models.AbstractEntity;
import org.launchcode.persistent.models.Employer;
import org.launchcode.persistent.models.Job;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by LaunchCode
 */
public class TestTaskThree extends AbstractTest {

    /*
     * Verifies the Employer.jobs field is properly defined
     * */
    @Test
    public void testJobFieldIsProperlyDefined() throws ClassNotFoundException, IllegalAccessException {
        final Class employerClass = getClassByName("models.Employer");
        Field jobsField = null;

        // verify Employer.jobs exists
        try {
            jobsField = employerClass.getDeclaredField("jobs");
        } catch (final NoSuchFieldException e) {
            fail("Employer does not have a jobs field");
        }

        // verify jobs is of type List
        final Type type = jobsField.getType();
        assertEquals(List.class, type);

        // verify jobs is initially an empty ArrayList
        final Employer employer = new Employer();
        jobsField.setAccessible(true);
        final Iterable<Job> initializedList = (ArrayList<Job>) jobsField.get(employer);

        for (final Job item : initializedList) {
            fail("jobs should be initialized to an empty ArrayList");
        }
    }

    /*
     * Verifies that jobs has the correct persistence annotations
     * */
    @Test
    public void testJobsHasCorrectPersistenceAnnotations() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Class employerClass = getClassByName("models.Employer");
        final Field jobsField = employerClass.getDeclaredField("jobs");

        assertNotNull(jobsField.getAnnotation(OneToMany.class));
        final Annotation joinColAnnotation = jobsField.getAnnotation(JoinColumn.class);
        final Method nameMethod = joinColAnnotation.getClass().getMethod("name");
        assertEquals("employer_id", nameMethod.invoke(joinColAnnotation));
    }

    /*
     * Verifies that Job extends AbstractEntity and has redundant code removed
     * */
    @Test
    public void testJobExtendsAbstractEntity() throws ClassNotFoundException {
        final Class jobClass = getClassByName("models.Job");
        assertEquals(AbstractEntity.class, jobClass.getSuperclass());

        // to verify that the class has name and id removed, we try to access them
        // verify that an exception is thrown
        try {
            jobClass.getDeclaredField("name");
            fail("Job should not have a name field");
        } catch (final NoSuchFieldException e) {
            // Do nothing - we expect this to be thrown
        }

        try {
            jobClass.getDeclaredField("id");
            fail("Job should not have an id field");
        } catch (final NoSuchFieldException e) {
            // Do nothing - we expect this to be thrown
        }
    }

    /*
     * Verifies that Job.employer has been refactored correctly to be of type Employer
     * and use @ManyToOne
     * */
    @Test
    public void testEmployerField() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {
        final Class jobClass = getClassByName("models.Job");
        final Field employerField = jobClass.getDeclaredField("employer");

        // verify that employer is of type Employer
        assertEquals(Employer.class, employerField.getType());

        // verify that getEmployer and setEmployer have been refactored properly
        final Method getEmployerMethod = jobClass.getMethod("getEmployer");
        assertEquals(Employer.class, getEmployerMethod.getReturnType());

        try {
            final Method setEmployerMethod = jobClass.getMethod("setEmployer", Employer.class);
        } catch (final NoSuchMethodException e) {
            fail("Employer should have a setEmployer method that returns an instance of Employer");
        }

        // verify that employer has @ManyToOne
        assertNotNull(employerField.getAnnotation(ManyToOne.class));
    }

    /*
     * Verifies that HomeController has an autowired EmployerRepository
     * */
    @Test
    public void testHomeControllerUsesEmployerRepository() throws ClassNotFoundException {
        final Class homeControllerClass = getClassByName("controllers.HomeController");
        Field employerRepositoryField = null;

        // verify that employerRepository field exists
        try {
            employerRepositoryField = homeControllerClass.getDeclaredField("employerRepository");
        } catch (final NoSuchFieldException e) {
            fail("HomeController should have an employerRepository field");
        }

        // verify that employerRepository has the correct type
        final Class employerRepositoryClass = getClassByName("models.data.EmployerRepository");
        assertEquals(employerRepositoryClass, employerRepositoryField.getType());

        // verify that the field is autowired
        assertNotNull(employerRepositoryField.getAnnotation(Autowired.class));
    }

    /*
     * Verifies that HomeController.displayAddJobForm calls employerRepository.findAll()
     * */
//    @Test
//    public void testHomeControllerFetchesEmployers(@Mocked EmployerRepository employerRepository, @Mocked SkillRepository skillRepository) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
//        Class homeControllerClass = getClassByName("controllers.HomeController");
//        HomeController homeController = new HomeController();
//
//        Field employerRepositoryField = homeControllerClass.getDeclaredField("employerRepository");
//        employerRepositoryField.setAccessible(true);
//        employerRepositoryField.set(homeController, employerRepository);
//
//        // not needed for verification, but necessary to make sure calling the controller
//        // method doesn't throw a NullPointerException
//        Field skillRepositoryField = null;
//        try {
//            skillRepositoryField = homeControllerClass.getDeclaredField("skillRepository");
//            skillRepositoryField.setAccessible(true);
//            skillRepositoryField.set(homeController, skillRepository);
//        } catch (NoSuchFieldException e) {
//            // do nothing
//        }
//
//        Model model = new ExtendedModelMap();
//
//        new Expectations() {{
//            employerRepository.findAll();
//        }};
//
//        homeController.displayAddJobForm(model);
//    }

    /*
     * Tests SQL query for task 3
     * */
    @Test
    public void testSqlQuery() throws IOException {
        final String queryFileContents = getFileContents("queries.sql");

        final Pattern queryPattern = Pattern.compile("DROP\\s+TABLE\\s+job;", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        final Matcher queryMatcher = queryPattern.matcher(queryFileContents);
        final boolean queryFound = queryMatcher.find();
        assertTrue(queryFound, "Task 3 SQL query is incorrect. Test your query against your database to find the error.");
    }

}
