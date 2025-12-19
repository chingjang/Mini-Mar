package com.minimart.controller;

import com.minimart.service.ProductService;
import com.minimart.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/reports")
public class ReportController {
    
    @Autowired
    private SaleService saleService;
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public String showReports(
            @RequestParam(required = false) String date,
            Model model) {
        
        LocalDate reportDate = (date != null && !date.isEmpty()) 
                ? LocalDate.parse(date) 
                : LocalDate.now();
        
        model.addAttribute("reportDate", reportDate);
        model.addAttribute("dailySales", saleService.getDailySales(reportDate));
        model.addAttribute("dailyTotal", saleService.getDailyTotalSales(reportDate));
        model.addAttribute("products", productService.getAllProducts());
        
        return "reports/dashboard";
    }
}
