package org.launchcode.techjobs.persistent;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by LaunchCode
 */
public class TestTaskOne extends AbstractTest{

    /*
    * Check application.properties for the correct db connection data
    * */
    @Test
    public void testDbConnectionProperties () throws IOException {
        final String propsFileContents = getFileContents("src/main/resources/application.properties");

        final Pattern urlPattern = Pattern.compile("spring.datasource.url=jdbc:mysql://localhost:3306/techjobs");
        final Matcher urlMatcher = urlPattern.matcher(propsFileContents);
        final boolean urlFound = urlMatcher.find();
        assertTrue(urlFound, "Database connection URL not found or is incorrect");

        final Pattern usernamePattern = Pattern.compile("spring.datasource.username=techjobs");
        final Matcher usernameMatcher= usernamePattern.matcher(propsFileContents);
        final boolean usernameFound = usernameMatcher.find();
        assertTrue(usernameFound, "Database username not found or is incorrect");

        final Pattern passwordPattern = Pattern.compile("spring.datasource.password=techjobs");
        final Matcher passwordMatcher= passwordPattern.matcher(propsFileContents);
        final boolean passwordFound = passwordMatcher.find();
        assertTrue(passwordFound, "Database password not found or is incorrect");
    }

    /*
    * Check build.gradle for the required database dependencies
    * */
    @Test
    public void testDbGradleDependencies () throws IOException {
        final String gradleFileContents = getFileContents("build.gradle");

        final Pattern jpaPattern = Pattern.compile("org.springframework.boot:spring-boot-starter-data-jpa");
        final Matcher jpaMatcher = jpaPattern.matcher(gradleFileContents);
        final boolean jpaFound = jpaMatcher.find();
        assertTrue(jpaFound, "JPA dependency not found or is incorrect");

        final Pattern mysqlPattern = Pattern.compile("mysql:mysql-connector-java");
        final Matcher mysqlMatcher = mysqlPattern.matcher(gradleFileContents);
        final boolean mysqlFound = mysqlMatcher.find();
        assertTrue(mysqlFound, "MySQL dependency not found or is incorrect");

    }

}
