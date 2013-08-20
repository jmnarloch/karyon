package com.netflix.kayron.server.test.injector;

import com.google.inject.Injector;
import com.google.inject.Module;

/**
 *
 */
public interface OverridableInjector extends Injector {

    void override(Iterable<Module> modules);

    void override(Module... modules);
}
