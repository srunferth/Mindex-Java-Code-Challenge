package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.compensation.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/employee/{employeeId}/compensation")
    public Compensation create(@PathVariable String employeeId, @RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for employee with id [{}]", employeeId);

        return compensationService.create(employeeId, compensation);
    }

    @GetMapping("/employee/{employeeId}/compensation")
    public Compensation read(@PathVariable String employeeId) {
        LOG.debug("Received compensation read request for employee with id [{}]", employeeId);

        return compensationService.read(employeeId);
    }
}
