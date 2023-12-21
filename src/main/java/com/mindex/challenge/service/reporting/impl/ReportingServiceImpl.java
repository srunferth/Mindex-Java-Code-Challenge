package com.mindex.challenge.service.reporting.impl;


import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.reporting.ReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class ReportingServiceImpl implements ReportingService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String employeeId) {
        LOG.debug("Reading employee with id [{}]", employeeId);

        //TODO when the version of mongodb is updated, this can be further optimized using a graph lookup based around the following parameters.
        // When coding this initially $graphLookup was an unrecognized pipeline stage. From the research done, this seems to be caused by an older version of MongoDb
        // We would then be able to count the distinct reports in the reports field rather than having to recursively get each employee.
        //HashMap<String, String> graphLookup = new HashMap<>();
        //graphLookup.put("from", "employee");
        //graphLookup.put("startWith", "$directReports.employeeId");
        //graphLookup.put("connectFromField", "directReports.employeeId");
        //graphLookup.put("connectToField", "employeeId");
        //graphLookup.put("as", "reports");


        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        //Check if the employee is null since the reporting structure cannot be generated if the employee is null
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        LOG.debug("Calculating distinct reports for employee with id [{}]", employeeId);

        //Generate the set of reports
        Set<String> reportIds = getReportsForEmployee(employeeId);

        return new ReportingStructure(employee, reportIds.size());
    }

    /**
     * Recursive function to generate a set of employee ids that are reports of a given employee.
     * @param employee The employee to generate the set of reports for
     * @return A set of employee ids that report to a given employee
     */
    private Set<String> getReportsForEmployee(String employeeId)
    {
        Set<String> reportIds = new HashSet<>();

        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        //Check if the employee is null since the reports of a null employee can not be checked
        if(employee == null)
        {
            throw new NullPointerException("Null Employee in getReportsForEmployee");
        }

        //Iterate through each employee add itself then all reports for their reports. Only check the direct reports if the list is not null
        if(employee.getDirectReports() != null) {
            for (Employee report : employee.getDirectReports()) {
                reportIds.add(report.getEmployeeId());
                reportIds.addAll(getReportsForEmployee(report.getEmployeeId()));
            }
        }

        return reportIds;
    }
}
