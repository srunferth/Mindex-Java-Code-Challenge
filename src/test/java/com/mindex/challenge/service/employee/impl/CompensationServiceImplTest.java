package com.mindex.challenge.service.employee.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    private String compensationUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/employee/{employeeId}/compensation";
    }

    @Test
    public void testCreateReadUpdate() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        testEmployee.setEmployeeId(UUID.randomUUID().toString());

        employeeRepository.insert(testEmployee);

        Random rand = new Random();

        Compensation testCompensation = new Compensation(testEmployee, rand.nextDouble(), new Date(System.currentTimeMillis()));



        // Create checks
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class, testEmployee.getEmployeeId()).getBody();
        assertNotNull(createdCompensation);
        assertCompensationEquivalence(testCompensation, createdCompensation);


        // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compensationUrl, Compensation.class, testEmployee.getEmployeeId()).getBody();
        assertNotNull(readCompensation);
        assertCompensationEquivalence(createdCompensation, readCompensation);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals(expected.getSalary(), actual.getSalary(), 0);
        assertEmployeeEquivalence(expected.getEmployee(), actual.getEmployee());
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        assertDirectReportPartialEquivalence(expected.getDirectReports(), actual.getDirectReports());
    }

    /**
     * Assert that for each item in the expected list, there is an item in the actual list with the same id. Also assert that the lists are the same size.
     * @param expected The list of expected reports
     * @param actual The list of actual reports
     */
    private static void assertDirectReportPartialEquivalence(List<Employee> expected, List<Employee> actual)
    {
        if(expected == null)
        {
            assertNull(actual);
        }
        else {
            assertEquals(expected.size(), actual.size());
            //Convert the expected employees into a set of ids
            Set<String> expectedIds = new HashSet<>();
            for (Employee expectedEmployee : expected) {
                expectedIds.add(expectedEmployee.getEmployeeId());
            }
            //Convert the actual employees into a set of ids
            Set<String> actualIds = new HashSet<>();
            for (Employee actualEmployee : expected) {
                actualIds.add(actualEmployee.getEmployeeId());
            }

            assertTrue(actualIds.containsAll(expectedIds));
        }
    }
}
