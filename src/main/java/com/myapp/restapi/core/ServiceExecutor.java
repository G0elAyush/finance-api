package com.myapp.restapi.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.myapp.restapi.exception.InternalException;

@Component
public class ServiceExecutor {
	
	@Autowired
    private ApplicationContext applicationContext;  // Automatically injected by Spring

    
	
	public Object executeService(String className, String methodName, Object[] args) throws Throwable{
		
		try {
			Object service = applicationContext.getBean(Class.forName(className));
			
			// Find the method with the matching name and parameter types
			Method method = findMethod(service.getClass(), methodName, args);

        // Invoke the method on the instance with the given arguments
			return method.invoke(service, args);
		}
		catch(Throwable e) {
			if(e instanceof InvocationTargetException) {
				throw ((InvocationTargetException) e).getTargetException();
			}
			  if (e instanceof IllegalAccessException || e instanceof NoSuchMethodException
					  || e instanceof BeansException || e instanceof ClassNotFoundException) {
				  e.printStackTrace();
				  throw new InternalException("Exception.internal");
			  }
			  else {
				  throw e;
			  }
		}
		
	}
	
	
	// Helper method to find a method by its name and parameter types
	private Method findMethod(Class<?> serviceClass, String methodName, Object[] args) throws NoSuchMethodException {
        // Get the parameter types from the arguments
        Class<?>[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }

        // Find the method by name and parameter types
        return serviceClass.getDeclaredMethod(methodName, argTypes);
    }

}
