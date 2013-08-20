package com.netflix.kayron.server.test.injector;

import com.google.inject.Injector;

/**
 * Created with IntelliJ IDEA.
 * User: jakub
 * Date: 19.08.13
 * Time: 21:32
 * To change this template use File | Settings | File Templates.
 */
public final class InjectorUtils {

    public static OverridableInjector createOverridableInjector(Injector injector) {

        return new OverridableInjectorImpl(injector);
    }
}
