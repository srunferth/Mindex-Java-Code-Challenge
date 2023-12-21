package com.mindex.challenge.service.compensation;

import com.mindex.challenge.data.Compensation;


public interface CompensationService {
    Compensation create(String employeeId, Compensation employee);
    Compensation read(String id);
}
