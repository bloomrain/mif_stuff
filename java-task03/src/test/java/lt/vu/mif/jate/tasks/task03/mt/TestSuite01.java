package lt.vu.mif.jate.tasks.task03.mt;

import lt.vu.mif.jate.tasks.task03.mt.tool.ClientTest;
import static junit.framework.TestCase.fail;
import lt.vu.mif.jate.tasks.task03.mt.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Simple Client class test.
 * Single connection, single thread.
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite01 implements ClientTest {
    
    @Test
    public void ClientTest() {
        try (Client c = new Client(SERVER_ADDR)) {
            
            test("Simple test", c);
            
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
     }
    
}
