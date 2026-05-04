package com.miniProject.controller;

import com.miniProject.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    private final DashboardService dashboardService;

    public AdminDashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalRevenue", dashboardService.getTotalRevenue());
        model.addAttribute("topProducts", dashboardService.getTopProducts(5));
        return "admin/dashboard";
    }
}

