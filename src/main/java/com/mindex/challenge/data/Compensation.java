package com.mindex.challenge.data;

import java.util.Date;
import java.util.Objects;

/**
 * Used to represent an employee's compensation.
 * Has a reference to an employee, their monetary compensation, and the effective date of the compensation.
 */
public class Compensation {

    /** The employee this compensation is for. */
    private Employee employee;

    /** The salary of the employee in USD. */
    private double salary;

    /** The date the salary change started. */
    private Date effectiveDate;

    /** Default constructor */
    public Compensation() {
    }

    /** Parameterized Constructor.
     * @param employee The employee this reporting structure is for.
     * @param salary The salary of the employee in USD.
     * @param effectiveDate The date the salary change started.
     */
    public Compensation(Employee employee, double salary, Date effectiveDate) {
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    /** Getter for the effectiveDate field. */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /** Setter for the effectiveDate field. */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /** Getter for the employee field. */
    public Employee getEmployee() {
        return employee;
    }

    /** Setter for the employee field. */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /** Getter for the salary field. */
    public double getSalary() {
        return salary;
    }

    /** Setter for the salary field. */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compensation that = (Compensation) o;
        return Double.compare(salary, that.salary) == 0 && Objects.equals(employee, that.employee) && Objects.equals(effectiveDate, that.effectiveDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, salary, effectiveDate);
    }

    @Override
    public String toString() {
        return "Compensation{" +
                "employee=" + employee +
                ", salary=" + salary +
                ", effectiveDate=" + effectiveDate +
                '}';
    }
}
