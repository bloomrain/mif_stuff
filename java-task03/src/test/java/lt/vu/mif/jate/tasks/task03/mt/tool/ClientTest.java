package lt.vu.mif.jate.tasks.task03.mt.tool;

import java.io.IOException;
import java.net.InetSocketAddress;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import lt.vu.mif.jate.tasks.task03.mt.client.Client;
import lt.vu.mif.jate.tasks.task03.mt.client.ServerFunctionException;
import org.junit.Test;

/**
 * FunctionTests class
 * @author valdo
 */
public interface ClientTest {

    public static final InetSocketAddress SERVER_ADDR = new InetSocketAddress("193.219.42.49", 8042);
    
    @Test
    public default void test(String name, Client c) throws IOException, ServerFunctionException {
        long r;

        System.out.format("# Start [%s] on %s%n", name, Thread.currentThread());
        
        r = c.addition(10L, 20L);
        assertEquals(30L, r);
        
        r = c.substraction(330L, 213L);
        assertEquals(117L, r);

        r = c.multiplication(330L, 213L);
        assertEquals(70290L, r);
        
        r = c.division(330L, 11L);
        assertEquals(30L, r);

        try {
            c.addition(Long.MAX_VALUE, 20L);
            fail("Must fail with Overflow here");
        } catch (ServerFunctionException ex) {
            String msg = ex.getMessage();
            assertEquals("Overflow", msg);
        }
        
        r = c.multiplication(Long.MAX_VALUE, 1L);
        assertEquals(Long.MAX_VALUE, r);

        try {
            c.addition(Long.MAX_VALUE, 1L);
            fail("Must fail with Overflow here");
        } catch (ServerFunctionException ex) {
            String msg = ex.getMessage();
            assertEquals("Overflow", msg);
        }

        try {
            c.multiplication(Long.MAX_VALUE / 2, 3L);
            fail("Must fail with Overflow here");
        } catch (ServerFunctionException ex) {
            String msg = ex.getMessage();
            assertEquals("Overflow", msg);
        }

        System.out.format("# End [%s] on %s%n", name, Thread.currentThread());
        
    }
    
}
