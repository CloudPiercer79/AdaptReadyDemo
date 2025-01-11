package wipo.patinformed.tests.ui;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.log4j.Logger;

public class TestListener implements ITestListener {

    private static final Logger logger = Logger.getLogger(TestListener.class);

    // This will be executed if the test passes
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test Passed: " + result.getName());
    }

    // This will be executed if the test fails
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test Failed: " + result.getName());
        logger.error("Failure Reason: " + result.getThrowable());  // Logs the exception that caused the failure
    }

    // This will be executed if the test is skipped
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test Skipped: " + result.getName());
    }

    // Other methods are not required for logging purposes but can be overridden if needed
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test Started: " + result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test execution finished.");
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test execution started.");
    }
}

