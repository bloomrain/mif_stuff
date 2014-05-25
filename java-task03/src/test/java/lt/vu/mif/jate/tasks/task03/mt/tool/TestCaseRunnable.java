package lt.vu.mif.jate.tasks.task03.mt.tool;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class TestCaseRunnable implements Runnable {

    private final MultiThreadedTestCase testCase;
    
    public abstract void runTestCase() throws Throwable;

    @Override
    public void run() {
        try {
            runTestCase();
        } catch (Throwable t) {
            testCase.handleException(t);
            testCase.interruptThreads();
        }
    }

}