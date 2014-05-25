package lt.vu.mif.jate.tasks.task03.jpa;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;
import lt.vu.mif.jate.tasks.task03.jpa.model.Customer;
import lt.vu.mif.jate.tasks.task03.jpa.model.Gender;
import lt.vu.mif.jate.tasks.task03.jpa.model.Product;
import lt.vu.mif.jate.tasks.task03.jpa.model.Sale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Testing model classes.
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite01 {
    
    private static Customer createCustomer(Long id) {
        Customer c = new Customer();
        
        c.setId(id);
        c.setFirstName("Vardenis");
        c.setLastName("Pavardenis");
        c.setGender(Gender.M);
        c.setCity("Vilnius");
        c.setCountry("Lithuania");
        
        return c;
    }
    
    private static Product createProduct(Long id) {
        Product c = new Product();
        
        c.setId(id);
        c.setName("Bottle of Pepsi");
        c.setBrand("Pepsi");
        
        return c;
    }
    
    private static Sale createSale(Long id) {
        Sale s = new Sale();
        
        s.setId(id);
        s.setCost(BigDecimal.ONE);
        s.setUnits(1);
        s.setTime(Calendar.getInstance());
        
        return s;
    }
    
    @Test
    public void customerTests() throws Exception {
        
        Customer c = createCustomer(Long.MAX_VALUE);
        
        assertEquals((Long) Long.MAX_VALUE, c.getId());
        assertEquals("Vardenis", c.getFirstName());
        assertEquals("Pavardenis", c.getLastName());
        assertEquals(Gender.M, c.getGender());
        assertEquals("Vilnius", c.getCity());
        assertEquals("Lithuania", c.getCountry());
        
        assertEquals(c, createCustomer(Long.MAX_VALUE));
        assertFalse(c == createCustomer(1L));
        Set<Customer> cs = new HashSet<>();
        cs.add(c);
        cs.add(createCustomer(Long.MAX_VALUE));
        cs.add(createCustomer(1L));
        assertEquals(2, cs.size());
        
    }
    
    @Test
    public void productTests() throws Exception {
        
        Product p = createProduct(Long.MIN_VALUE);
        
        assertEquals((Long) Long.MIN_VALUE, p.getId());
        assertEquals("Bottle of Pepsi", p.getName());
        assertEquals("Pepsi", p.getBrand());

        assertEquals(p, createProduct(Long.MIN_VALUE));
        assertFalse(p == createProduct(1L));
        Set<Product> ps = new HashSet<>();
        ps.add(p);
        ps.add(createProduct(Long.MIN_VALUE));
        ps.add(createProduct(1L));
        assertEquals(2, ps.size());
        
    }
    
    @Test
    public void salesTests() throws Exception {
        
        Sale s = createSale(Long.MIN_VALUE);
        
        assertEquals((Long) Long.MIN_VALUE, s.getId());
        assertEquals(BigDecimal.ONE, s.getCost());
        assertEquals((Integer) 1, s.getUnits());
        assertTrue(s.getTime().before(Calendar.getInstance()));

        assertEquals(s, createSale(Long.MIN_VALUE));
        assertFalse(s == createSale(1L));
        Set<Sale> ps = new HashSet<>();
        ps.add(s);
        ps.add(createSale(Long.MIN_VALUE));
        ps.add(createSale(1L));
        assertEquals(2, ps.size());
        
    }
    
    @Test
    public void modelTests() throws Exception {
        
        Customer c = createCustomer(Long.MAX_VALUE);
        Product p = createProduct(Long.MIN_VALUE);
        Sale s = createSale(Long.MIN_VALUE);
        
        s.setCustomer(c);
        s.setProduct(p);
        
        c.getSales().add(s);
        p.getSales().add(s);
        
        assertTrue(c.getSales() instanceof List);
        assertTrue(p.getSales() instanceof List);

    }
    
}
