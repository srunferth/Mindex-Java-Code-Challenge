package com.mindex.challenge.data;

import java.util.Objects;

/**
 * Used to represent the amount of distinct reports an employee has.
 */
public class ReportingStructure {
    /** The employee this reporting structure is for. */
    private Employee employee;

    /** The number of distinct reports under the employee. */
    private int numberOfReports;

    /** Default constructor */
    public ReportingStructure() {
    }

    /** Parameterized Constructor.
     * @param employee The employee this reporting structure is for.
     * @param count The number of distinct reports under the employee.
     */
    public ReportingStructure(Employee employee, int count) {
        this.employee = employee;
        this.numberOfReports = count;
    }

    /** Getter for the employee field. */
    public Employee getEmployee() {
        return employee;
    }

    /** Setter for the employee field. */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /** Getter for the numberOfReports field. */
    public int getNumberOfReports() {
        return numberOfReports;
    }

    /** Setter for the numberOfReports field. */
    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportingStructure that = (ReportingStructure) o;
        return numberOfReports == that.numberOfReports && Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, numberOfReports);
    }

    @Override
    public String toString() {
        return "ReportingStructure{" +
                "employee=" + employee +
                ", numberOfReports=" + numberOfReports +
                '}';
    }
}
