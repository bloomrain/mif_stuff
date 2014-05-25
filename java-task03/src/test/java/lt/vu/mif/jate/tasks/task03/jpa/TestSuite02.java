package lt.vu.mif.jate.tasks.task03.jpa;

import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import lt.vu.mif.jate.tasks.task03.jpa.model.Customer;
import lt.vu.mif.jate.tasks.task03.jpa.model.Gender;
import lt.vu.mif.jate.tasks.task03.jpa.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Testing DbManager class to retrieve list of entities.
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite02 {
    
    @Test
    public void customerTests() throws Exception {
        try (DbManager db = new DbManager()) {
            
            assertEquals(10263, db.getListOf(Customer.class).size());

            List<Customer> cl1 = db.getListOf(Customer.class, "firstName");
            assertEquals("Aaron", cl1.iterator().next().getFirstName());
            assertEquals("Zuzana", cl1.get(cl1.size() - 1).getFirstName());

            List<Customer> cl2 = db.getListOf(Customer.class, "city");
            assertEquals("Acapulco", cl2.iterator().next().getCity());
            assertEquals("Yakima", cl2.get(cl2.size() - 1).getCity());

            Customer c1 = cl2.stream()
                    .filter(c -> c.getId() == 123456)
                    .findFirst().orElse(null);
            TestCase.assertNull(c1);

            Customer c2 = cl2.stream()
                    .filter(c -> c.getId() == 1234)
                    .findFirst().orElse(null);
            TestCase.assertNotNull(c2);
            TestCase.assertEquals("Linda", c2.getFirstName());
            TestCase.assertEquals("Chavira", c2.getLastName());
            TestCase.assertEquals("San Gabriel", c2.getCity());
            TestCase.assertEquals("USA", c2.getCountry());
            TestCase.assertEquals(Gender.F, c2.getGender());
            TestCase.assertEquals(23, c2.getSales().size());

            Integer[] unitsPurchased = new Integer[] { 2, 3, 4 };
            TestCase.assertTrue(
                c2.getSales().stream()
                    .map(s -> s.getUnits())
                    .distinct()
                    .allMatch(u -> Arrays.stream(unitsPurchased)
                            .filter(i -> u.equals(i))
                            .findFirst()
                            .isPresent()));
            
        }
    }
    
    @Test
    public void productTests() throws Exception {
        try (DbManager db = new DbManager()) {
            
            assertEquals(1560, db.getListOf(Product.class).size());

            List<Product> pl1 = db.getListOf(Product.class, "brand");
            assertEquals("ADJ", pl1.iterator().next().getBrand());
            assertEquals("Washington", pl1.get(pl1.size() - 1).getBrand());

            List<Product> pl2 = db.getListOf(Product.class, "name");
            assertEquals("ADJ Rosy Sunglasses", pl2.iterator().next().getName());
            assertEquals("Washington Strawberry Drink", pl2.get(pl2.size() - 1).getName());

            Product p1 = pl2.stream().filter(c -> c.getId() == 0).findFirst().orElse(null);
            TestCase.assertNull(p1);

            Product p2 = pl2.stream().filter(c -> c.getId() == 1234).findFirst().orElse(null);
            TestCase.assertNotNull(p2);
            TestCase.assertEquals("Plato Columbian Coffee", p2.getName());
            TestCase.assertEquals("Plato", p2.getBrand());
            TestCase.assertEquals(145, p2.getSales().size());

            String[] countriesPurchased = new String[] { "Canada", "USA", "Mexico" };
            TestCase.assertTrue(
                p2.getSales().stream()
                    .map(s -> s.getCustomer().getCountry())
                    .distinct()
                    .allMatch(c -> Arrays.stream(countriesPurchased)
                            .filter(s -> s.equals(c))
                            .findFirst()
                            .isPresent()));
            
        }
    }
    
}
