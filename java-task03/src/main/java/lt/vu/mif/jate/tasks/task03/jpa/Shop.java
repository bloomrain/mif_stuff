/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lt.vu.mif.jate.tasks.task03.jpa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lt.vu.mif.jate.tasks.task03.jpa.model.Customer;
import lt.vu.mif.jate.tasks.task03.jpa.model.Product;
import lt.vu.mif.jate.tasks.task03.jpa.model.Sale;
import lt.vu.mif.jate.tasks.task03.jpa.model.SaleEntry;

/**
 *
 * @author gege
 */
class Shop {
    private final DbManager db;
    
    Shop(DbManager dbManager) {
        this.db = dbManager;
    }
    
    public Set filter(Class aClass, Predicate<SaleEntry> predicate) {
        List entries;
        entries = this.db.getListOf(aClass);
        
        Set results;
        results = new HashSet<>();
        
        for (Object entry: entries.toArray()) {
            if (aClass == Customer.class) {
                if (predicate.test((Customer)entry)) {
                    results.add((Customer)entry);
                }
            } else {
                if (predicate.test((Product)entry)) {
                    results.add((Product)entry);
                }
            }
        }
        return results;
    }
    
    public Set filterAndMap(Class aClass, Predicate<SaleEntry> predicate, Function<SaleEntry, String> fn) {
        Set<SaleEntry> entries;
        entries = this.filter(aClass, predicate);
        
        return entries.stream().map(fn).collect(Collectors.toSet());
    }
    
    public Map<String, Map<String, List<Customer>>> customersAtLocationsBySale(Predicate<Sale> predicate) {
        
        List<Customer> customers;
        customers = this.db.getListOf(Customer.class);
        List<Customer> filteredCustomers;
        Integer i = 0;
        filteredCustomers = customers.parallelStream().filter(c -> {
            return c.getSales().stream().anyMatch(predicate);
        } ).collect(Collectors.toList());
        
        
        return filteredCustomers.stream().collect(
            Collectors.groupingBy(Customer::getCountry, Collectors.groupingBy(Customer::getCity))
        );
    }
}
