package com.minimart.service;

import com.minimart.model.Product;
import com.minimart.model.Sale;
import com.minimart.model.SaleItem;
import com.minimart.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SaleService {
    
    @Autowired
    private SaleRepository saleRepository;
    
    @Autowired
    private ProductService productService;
    
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }
    
    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }
    
    public Sale saveSale(Sale sale) {
        // Update stock for each item in the sale
        for (SaleItem item : sale.getSaleItems()) {
            productService.updateStock(item.getProduct().getId(), item.getQuantity());
        }
        return saleRepository.save(sale);
    }
    
    public List<Sale> getDailySales(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return saleRepository.findBySaleDateBetween(startOfDay, endOfDay);
    }
    
    public Double getDailyTotalSales(LocalDate date) {
        List<Sale> dailySales = getDailySales(date);
        return dailySales.stream()
                .mapToDouble(Sale::getTotalAmount)
                .sum();
    }
}
