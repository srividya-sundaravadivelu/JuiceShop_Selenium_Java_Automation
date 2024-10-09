package listeners;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class RetryLoginAfterRegistrationListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {    	
        if (method.getTestMethod().getMethodName().equals("testRegisterUser") && testResult.isSuccess()) {
        	System.out.println("Retry login");
            // Rerun testLogin after successful registration
            try {
                Object testInstance = testResult.getInstance();
                testInstance.getClass().getMethod("testLogin").invoke(testInstance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

