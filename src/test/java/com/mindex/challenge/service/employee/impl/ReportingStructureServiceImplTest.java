package com.mindex.challenge.service.employee.impl;


import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
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
public class ReportingStructureServiceImplTest {

    //Cases to Test
    //1 No direct reports
    //2 Single level direct reports
    //3 Multiple reports
    //4 Multilevel reports
    //4 Shared reports

    private String reportingStructureUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/employee/{id}/reporting-structure";
    }

    @Test
    public void testNoDirectReports() {
        //Generate the employees to be used for the test
        Employee testEmployee1 = generateEmployee();

        //Insert the employees into the repository
        employeeRepository.insert(testEmployee1);

        //Generate structure to test against
        ReportingStructure testReportingStructure = new ReportingStructure(testEmployee1, 0);


        // Read checks
        ReportingStructure readReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, testEmployee1.getEmployeeId()).getBody();
        assertNotNull(readReportingStructure);
        assertNotNull(readReportingStructure.getEmployee());
        assertReportingStructureEquivalence(testReportingStructure, readReportingStructure);
    }

    @Test
    public void singleLevelDirectReports() {
        //Generate the employees to be used for the test
        Employee testEmployee1 = generateEmployee();
        Employee testEmployee2 = generateEmployee();

        testEmployee1.setDirectReports(Collections.singletonList(testEmployee2));

        //Insert the employees into the repository
        employeeRepository.insert(testEmployee1);
        employeeRepository.insert(testEmployee2);

        //Generate structure to test against
        ReportingStructure testReportingStructure = new ReportingStructure(testEmployee1, 1);


        // Read checks
        ReportingStructure readReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, testEmployee1.getEmployeeId()).getBody();
        assertNotNull(readReportingStructure);
        assertNotNull(readReportingStructure.getEmployee());
        assertReportingStructureEquivalence(testReportingStructure, readReportingStructure);
    }

    @Test
    public void multipleSingleLevelDirectReports() {
        //Generate the employees to be used for the test
        Employee testEmployee1 = generateEmployee();
        Employee testEmployee2 = generateEmployee();
        Employee testEmployee3 = generateEmployee();
        Employee testEmployee4 = generateEmployee();

        testEmployee1.setDirectReports(Arrays.asList(testEmployee2, testEmployee3));

        //Insert the employees into the repository
        employeeRepository.insert(testEmployee1);
        employeeRepository.insert(testEmployee2);
        employeeRepository.insert(testEmployee3);
        employeeRepository.insert(testEmployee4);

        //Generate structures to test against
        ReportingStructure testReportingStructureForEmployee1 = new ReportingStructure(testEmployee1, 2);
        ReportingStructure testReportingStructureForEmployee4 = new ReportingStructure(testEmployee4, 0);

        // Read checks
        ReportingStructure readReportingStructureForEmployee1 = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, testEmployee1.getEmployeeId()).getBody();
        assertNotNull(readReportingStructureForEmployee1);
        assertNotNull(readReportingStructureForEmployee1.getEmployee());
        assertReportingStructureEquivalence(testReportingStructureForEmployee1, readReportingStructureForEmployee1);

        ReportingStructure readReportingStructureForEmployee4 = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, testEmployee4.getEmployeeId()).getBody();
        assertNotNull(readReportingStructureForEmployee4);
        assertNotNull(readReportingStructureForEmployee4.getEmployee());
        assertReportingStructureEquivalence(testReportingStructureForEmployee4, readReportingStructureForEmployee4);
    }

    @Test
    public void multipleLevelDirectReports() {
        //Generate the employees to be used for the test
        Employee testEmployee1 = generateEmployee();
        Employee testEmployee2 = generateEmployee();
        Employee testEmployee3 = generateEmployee();
        Employee testEmployee4 = generateEmployee();

        testEmployee2.setDirectReports(Collections.singletonList(testEmployee3));
        testEmployee1.setDirectReports(Collections.singletonList(testEmployee2));

        //Insert the employees into the repository
        employeeRepository.insert(testEmployee1);
        employeeRepository.insert(testEmployee2);
        employeeRepository.insert(testEmployee3);
        employeeRepository.insert(testEmployee4);

        //Generate structures to test against
        ReportingStructure testReportingStructureForEmployee1 = new ReportingStructure(testEmployee1, 2);
        ReportingStructure testReportingStructureForEmployee4 = new ReportingStructure(testEmployee4, 0);

        // Read checks
        ReportingStructure readReportingStructureForEmployee1 = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, testEmployee1.getEmployeeId()).getBody();
        assertNotNull(readReportingStructureForEmployee1);
        assertNotNull(readReportingStructureForEmployee1.getEmployee());
        assertReportingStructureEquivalence(testReportingStructureForEmployee1, readReportingStructureForEmployee1);

        ReportingStructure readReportingStructureForEmployee4 = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, testEmployee4.getEmployeeId()).getBody();
        assertNotNull(readReportingStructureForEmployee4);
        assertNotNull(readReportingStructureForEmployee4.getEmployee());
        assertReportingStructureEquivalence(testReportingStructureForEmployee4, readReportingStructureForEmployee4);
    }

    @Test
    public void sharedDirectReports() {
        //Generate the employees to be used for the test
        Employee testEmployee1 = generateEmployee();
        Employee testEmployee2 = generateEmployee();
        Employee testEmployee3 = generateEmployee();
        Employee testEmployee4 = generateEmployee();

        testEmployee1.setDirectReports(Arrays.asList(testEmployee2, testEmployee3));
        testEmployee2.setDirectReports(Collections.singletonList(testEmployee4));
        testEmployee3.setDirectReports(Collections.singletonList(testEmployee4));

        //Insert the employees into the repository
        employeeRepository.insert(testEmployee1);
        employeeRepository.insert(testEmployee2);
        employeeRepository.insert(testEmployee3);
        employeeRepository.insert(testEmployee4);

        //Generate structures to test against
        ReportingStructure testReportingStructureForEmployee1 = new ReportingStructure(testEmployee1, 3);
        ReportingStructure testReportingStructureForEmployee4 = new ReportingStructure(testEmployee4, 0);

        // Read checks
        ReportingStructure readReportingStructureForEmployee1 = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, testEmployee1.getEmployeeId()).getBody();
        assertNotNull(readReportingStructureForEmployee1);
        assertNotNull(readReportingStructureForEmployee1.getEmployee());
        assertReportingStructureEquivalence(testReportingStructureForEmployee1, readReportingStructureForEmployee1);

        ReportingStructure readReportingStructureForEmployee4 = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, testEmployee4.getEmployeeId()).getBody();
        assertNotNull(readReportingStructureForEmployee4);
        assertNotNull(readReportingStructureForEmployee4.getEmployee());
        assertReportingStructureEquivalence(testReportingStructureForEmployee4, readReportingStructureForEmployee4);
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        assertDirectReportPartialEquivalence(expected.getDirectReports(), actual.getDirectReports());
    }

    private static void assertReportingStructureEquivalence(ReportingStructure expected, ReportingStructure actual) {
        assertEmployeeEquivalence(expected.getEmployee(), actual.getEmployee());
        assertEquals(expected.getNumberOfReports(), actual.getNumberOfReports());
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
            assertEquals(expected, actual);
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

    private static Employee generateEmployee()
    {
        Random rand = new Random();

        Employee employee = new Employee();
        employee.setFirstName("first" + rand.nextInt(1000));
        employee.setLastName("last" + rand.nextInt(1000));
        employee.setDepartment("Engineering");
        employee.setPosition("Developer");
        employee.setEmployeeId(UUID.randomUUID().toString());

        return employee;
    }
}
