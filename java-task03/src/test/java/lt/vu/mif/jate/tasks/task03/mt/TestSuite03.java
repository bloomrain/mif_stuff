package lt.vu.mif.jate.tasks.task03.mt;

import lt.vu.mif.jate.tasks.task03.mt.tool.ClientTest;
import static junit.framework.TestCase.fail;
import lt.vu.mif.jate.tasks.task03.mt.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Test that connection survives long sleeps...
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite03 implements ClientTest {
    
    @Test
    public void ClientTest() {
        
        try (Client c = new Client(SERVER_ADDR)) {
            
            test("First immediate run", c);

            Thread.sleep(5000);
            
            test("Second run after 5 secs", c);
            
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
     }
    
}
