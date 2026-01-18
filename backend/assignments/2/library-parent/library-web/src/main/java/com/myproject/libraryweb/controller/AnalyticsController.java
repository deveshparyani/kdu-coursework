package com.myproject.libraryweb.controller;

import com.myproject.libraryapi.dtos.enums.BookStatus;
import com.myproject.libraryservice.service.AnalyticsService;
import com.myproject.libraryweb.utils.ConstantsUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(ConstantsUtils.API_BASE_URL + "/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    /**
     * Returns audit analytics of books grouped by status.
     */
    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/audit")
    public Map<BookStatus, Long> audit() {
        return analyticsService.getBookStatusCounts();
    }
}