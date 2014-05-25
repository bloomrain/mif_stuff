package lt.vu.mif.jate.tasks.task03.jpa;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import junit.framework.TestCase;
import lt.vu.mif.jate.tasks.task03.jpa.model.Customer;
import lt.vu.mif.jate.tasks.task03.jpa.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Testing Shop class to retrieve info.
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite03 {

    @Test
    public void testFilter() throws Exception {
        try (DbManager db = new DbManager()) {
            
            Shop shop = new Shop(db);

            Set<Customer> mexicans = shop.filter(Customer.class, 
                    c -> c.getCountry().equals("Mexico"));
            TestCase.assertEquals(1203, mexicans.size());

            Set<Product> goldenWaffles = shop.filter(Product.class, 
                    p -> p.getBrand().equals("Golden") 
                            && p.getName().contains("Waffles"));
            TestCase.assertEquals(4, goldenWaffles.size());
            
        }
    }
    
    @Test
    public void testFilterAndMap() throws Exception {
        try (DbManager db = new DbManager()) {
            
            Shop shop = new Shop(db);

            Set<String> usaCities = shop.filterAndMap(Customer.class, 
                            c -> c.getCountry().equals("USA"), c -> c.getCity());
            TestCase.assertEquals(78, usaCities.size());
            TestCase.assertTrue(usaCities.contains("Redmond"));
            TestCase.assertTrue(usaCities.contains("Berkeley"));

            Set<String> canadaCities = shop.filterAndMap(Customer.class, 
                            c -> c.getCountry().equals("Canada"), c -> c.getCity());
            TestCase.assertEquals(18, canadaCities.size());
            TestCase.assertTrue(canadaCities.contains("Burnaby"));
            TestCase.assertTrue(canadaCities.contains("Richmond"));
            
        }
    }
    
    @Test
    public void testCustomersAtLocationsBySale() throws Exception {
        try (DbManager db = new DbManager()) {
            Shop shop = new Shop(db);

            BigDecimal threshold = new BigDecimal(9.7);
            Map<String, Map<String, List<Customer>>> richCustomers 
                    = shop.customersAtLocationsBySale(s -> s.getCost().compareTo(threshold) > 0);
            System.out.println(richCustomers);

            Map<String, List<Customer>> richCanadians = richCustomers.get("Canada");
            TestCase.assertEquals(1, richCanadians.size());

            List<Customer> richLangleyans = richCanadians.get("Langley");
            TestCase.assertEquals(1, richLangleyans.size());
            TestCase.assertEquals((Long) 60L, richLangleyans.iterator().next().getId());

            Map<String, List<Customer>> richMexicans = richCustomers.get("Mexico");
            TestCase.assertEquals(2, richMexicans.size());

            List<Customer> richCamachians = richMexicans.get("Camacho");
            TestCase.assertEquals(2, richCamachians.size());
            TestCase.assertEquals((Long) 2175L, richCamachians.iterator().next().getId());
        }
    }
    
}
