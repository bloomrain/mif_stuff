package lt.vu.mif.jate.tasks.task03.mt.tool;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestResult;

public abstract class MultiThreadedTestCase extends TestCase {

    private ExecutorService executor;
    protected final TestResult result = new TestResult();

    public MultiThreadedTestCase(final String name) {
        super(name);
    }

    protected void runTestCaseRunnables(final Collection<TestCaseRunnable> jobs) throws InterruptedException {
        
        this.executor = Executors.newFixedThreadPool(jobs.size());
        jobs.stream().forEach(job -> executor.submit(job));
        
        try {
            
            Thread.sleep(5000);
            this.executor.shutdown();
            this.executor.awaitTermination(2, TimeUnit.MINUTES);
            
        } catch (InterruptedException e) { }
        
    }

    public synchronized void handleException(final Throwable t) {
        if (t instanceof AssertionFailedError) {
            result.addFailure(this, (AssertionFailedError) t);
        } else {
            result.addError(this, t);
        }
    }
    
    public void interruptThreads() {
        executor.shutdownNow();
    }

}
