package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.reporting.ReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** Reporting Structure service API controller. */
@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private ReportingService reportingService;

    @GetMapping("/employee/{id}/reporting-structure")
    public ReportingStructure read(@PathVariable String id) {
        LOG.debug("Received reporting structure read request for employee with id [{}]", id);

        return reportingService.read(id);
    }

}
