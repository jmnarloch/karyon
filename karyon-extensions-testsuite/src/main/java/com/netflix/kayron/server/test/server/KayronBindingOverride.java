package com.netflix.kayron.server.test.server;

import com.google.inject.Module;
import com.netflix.kayron.server.test.KayronTestGuiceContextListener;
import com.netflix.kayron.server.test.OverrideBinding;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class KayronBindingOverride {

    /**
     * Handles the before test event.
     *
     * @param event the before test event
     */
    public void beforeTest(@Observes BeforeClass event) {

        Module module;
        List<Method> methods;
        List<Module> aggregatedModules;

        methods = SecurityActions.getStaticMethodsWithAnnotation(event.getTestClass().getJavaClass(),
                OverrideBinding.class);

        if (methods.size() > 0) {
            aggregatedModules = new ArrayList<Module>();
            for (Method method : methods) {
                module = invoke(method);

                if (module != null) {

                    aggregatedModules.add(module);
                }
            }

            KayronTestGuiceContextListener.overrideBinding(aggregatedModules);
        }
    }

    private Module invoke(Method method) {
        try {

            return (Module) method.invoke(null);
        } catch (ClassCastException e) {

            throw new RuntimeException("An error occurred when invoking an method.");
        } catch (IllegalAccessException e) {

            throw new RuntimeException("An error occurred when invoking an method.");
        } catch (InvocationTargetException e) {

            throw new RuntimeException("An error occurred when invoking an method.");
        }
    }
}
