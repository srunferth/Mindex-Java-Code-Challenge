package com.mindex.challenge.service.reporting;

import com.mindex.challenge.data.ReportingStructure;

public interface ReportingService {

    /**
     * Generates a reporting structure for an employee with the given id.
     * The numberOfReports is calculated by determining the number of distinct reports in the chain of command under that employee.
     * @param employeeId The id of the employee to generate the report structure for
     * @return The generated report structure
     */
    ReportingStructure read(String employeeId);
}
