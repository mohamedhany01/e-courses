package byow.tests.core;

import java.lang.reflect.Method;

public class Utilities {
    public <T> Method getPrivateMethod(Class<T> className, String methodName, Class<?>... parameters) {
        Method method = null;
        try {
            method = className.getDeclaredMethod(methodName, parameters);
            method.setAccessible(true);
        } catch (Exception e) {
            System.out.println(e);
        }
        return method;
    }
}
