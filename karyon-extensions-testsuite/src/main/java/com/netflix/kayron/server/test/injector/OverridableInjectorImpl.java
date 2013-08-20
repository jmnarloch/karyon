package com.netflix.kayron.server.test.injector;

import com.google.common.base.Preconditions;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.Errors;
import com.google.inject.spi.DefaultElementVisitor;
import com.google.inject.spi.Element;
import com.google.inject.spi.Elements;
import com.google.inject.spi.Message;
import com.google.inject.spi.TypeConverterBinding;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
final class OverridableInjectorImpl implements OverridableInjector {

    /**
     * The instance parent of injector to which all of the execution is being delegated.
     */
    private final Injector parent;

    private final Map<Key<?>, Binding<?>> bindingsMap = new HashMap<Key<?>, Binding<?>>();

    /**
     * Creates new instance of {@link OverridableInjectorImpl} with given parent injector.
     *
     * @param parent the parent injector
     */
    public OverridableInjectorImpl(Injector parent) {
        Preconditions.checkNotNull(parent, "Parameter 'parent' can not be null.");

        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void injectMembers(Object instance) {

        // delegates to parent
        parent.injectMembers(instance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> typeLiteral) {

        // delegates to parent
        return parent.getMembersInjector(typeLiteral);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> MembersInjector<T> getMembersInjector(Class<T> type) {

        // delegates to parent
        return parent.getMembersInjector(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Key<?>, Binding<?>> getBindings() {

        // delegates to parent
        return parent.getBindings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Key<?>, Binding<?>> getAllBindings() {

        // delegates to parent
        return parent.getAllBindings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Binding<T> getBinding(Key<T> key) {

        // delegates to parent
        return parent.getBinding(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Binding<T> getBinding(Class<T> type) {

        // delegates to parent
        return parent.getBinding(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Binding<T> getExistingBinding(Key<T> key) {

        // delegates to parent
        return parent.getExistingBinding(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<Binding<T>> findBindingsByType(TypeLiteral<T> type) {

        // delegates to parent
        return parent.findBindingsByType(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Provider<T> getProvider(Key<T> key) {

        // delegates to parent
        return parent.getProvider(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Provider<T> getProvider(Class<T> type) {

        // delegates to parent
        return parent.getProvider(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getInstance(Key<T> key) {

        // delegates to parent
        return parent.getInstance(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getInstance(Class<T> type) {

        // delegates to parent
        return parent.getInstance(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Injector getParent() {

        return parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Injector createChildInjector(Iterable<? extends Module> modules) {

        throw new UnsupportedOperationException("The child injector of this instance can not be created.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Injector createChildInjector(Module... modules) {

        throw new UnsupportedOperationException("The child injector of this instance can not be created.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Class<? extends Annotation>, Scope> getScopeBindings() {

        // delegates to parent
        return parent.getScopeBindings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<TypeConverterBinding> getTypeConverterBindings() {

        // delegates to parent
        return parent.getTypeConverterBindings();
    }

    @Override
    public void override(Iterable<Module> modules) {

        // TODO implement

        // retrieves the bindings in
        List<Element> elements = Elements.getElements(modules);

        // iterates over all elements and visits them, listing all the bindings
        BindingVisitor visitor = new BindingVisitor();
        for (Element element : elements) {

            element.acceptVisitor(visitor);
        }
    }

    @Override
    public void override(Module... modules) {

        override(Arrays.asList(modules));
    }

    public static class BindingVisitor extends DefaultElementVisitor<Void> {

        private final Errors errors = new Errors();

        @Override
        public Void visit(Message message) {

            errors.addMessage(message);

            return null;
        }
    }
}
