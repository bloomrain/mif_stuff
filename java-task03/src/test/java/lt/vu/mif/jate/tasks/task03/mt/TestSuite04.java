package lt.vu.mif.jate.tasks.task03.mt;

import lt.vu.mif.jate.tasks.task03.mt.tool.ClientTest;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.java.Log;
import lt.vu.mif.jate.tasks.task03.mt.client.Client;
import lt.vu.mif.jate.tasks.task03.mt.tool.MultiThreadedTestCase;
import lt.vu.mif.jate.tasks.task03.mt.tool.TestCaseRunnable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Multi-threaded test: connection must survive and multiple
 * clients should be able to send and receive correlated messages.
 * @author valdo
 */
@Log
@RunWith(JUnit4.class)
public class TestSuite04 extends MultiThreadedTestCase implements ClientTest {

    public TestSuite04() {
        super(TestSuite04.class.getName());
    }

    @Test
    public void doTests() throws Throwable {
        try (Client c = new Client(SERVER_ADDR)) {

            List<TestCaseRunnable> tests = new ArrayList<>();
            
            for (int i = 0; i < 10; i++) {
                final int j = i + 1;
                tests.add(new TestCaseRunnable(this) {

                    @Override
                    public void runTestCase() throws Throwable {
                        test(String.format("Test #%d", j), c);
                    }

                });
            }
            
            this.runTestCaseRunnables(tests);
            
        }
        
        if (result.errorCount() > 0) {
            throw result.errors().nextElement().thrownException();
        }
        
        if (result.failureCount() > 0) {
            throw result.failures().nextElement().thrownException();
        }
        
     }
    
}
