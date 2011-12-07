/*
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.etk.model.sandbox.exception;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;

import org.etk.model.sandbox.exception.util.Classes;
import org.etk.model.sandbox.exception.util.SourceProvider;
import org.etk.model.sandbox.exception.util.StackTraceElements;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

/**
 * A collection of error messages. If this type is passed as a method parameter, the method is
 * considered to have executed successfully only if new errors were not added to this collection.
 *
 * <p>Errors can be chained to provide additional context. To add context, call {@link #withSource}
 * to create a new Errors instance that contains additional context. All messages added to the
 * returned instance will contain full context.
 *
 * <p>To avoid messages with redundant context, {@link #withSource} should be added sparingly. A
 * good rule of thumb is to assume a ethod's caller has already specified enough context to
 * identify that method. When calling a method that's defined in a different context, call that
 * method with an errors object that includes its context.
 *
 */
public final class Errors implements Serializable {


  /**
   * The root errors object. Used to access the list of error messages.
   */
  private final Errors root;

  /**
   * The parent errors object. Used to obtain the chain of source objects.
   */
  private final Errors parent;

  /**
   * The leaf source for errors added here.
   */
  private final Object source;

  /**
   * null unless (root == this) and error messages exist. Never an empty list.
   */
  private List<Message> errors; // lazy, use getErrorsForAdd()

  public Errors() {
    this.root = this;
    this.parent = null;
    this.source = SourceProvider.UNKNOWN_SOURCE;
  }

  public Errors(Object source) {
    this.root = this;
    this.parent = null;
    this.source = source;
  }

  private Errors(Errors parent, Object source) {
    this.root = parent.root;
    this.parent = parent;
    this.source = source;
  }

  /**
   * Returns an instance that uses {@code source} as a reference point for newly added errors.
   */
  public Errors withSource(Object source) {
    return source == SourceProvider.UNKNOWN_SOURCE ? this : new Errors(this, source);
  }
    
  public Errors bindingToProvider() {
    return addMessage("Binding to Provider is not allowed.");
  }

  public Errors notASubtype(Class<?> implementationType, Class<?> type) {
    return addMessage("%s doesn't extend %s.", implementationType, type);
  }

  public Errors recursiveImplementationType() {
    return addMessage("@ImplementedBy points to the same class it annotates.");
  }

  public Errors recursiveProviderType() {
    return addMessage("@ProvidedBy points to the same class it annotates.");
  }

  public Errors missingRuntimeRetention(Object source) {
    return addMessage("Please annotate with @Retention(RUNTIME).%n" + " Bound at %s.", convert(source));
  }

  public Errors missingScopeAnnotation() {
    return addMessage("Please annotate with @ScopeAnnotation.");
  }

  public Errors optionalConstructor(Constructor constructor) {
    return addMessage("%s is annotated @Inject(optional=true), "
        + "but constructors cannot be optional.", constructor);
  }

  public Errors cannotBindToGuiceType(String simpleName) {
    return addMessage("Binding to core guice framework type is not allowed: %s.", simpleName);
  }

  public Errors scopeNotFound(Class<? extends Annotation> scopeAnnotation) {
    return addMessage("No scope is bound to %s.", scopeAnnotation);
  }

  public Errors scopeAnnotationOnAbstractType(Class<? extends Annotation> scopeAnnotation, Class<?> type, Object source) {
    return addMessage("%s is annotated with %s, but scope annotations are not supported " 
                      + "for abstract types.%n Bound at %s.", type, scopeAnnotation, convert(source));
  }

  public Errors misplacedBindingAnnotation(Member member, Annotation bindingAnnotation) {
    return addMessage("%s is annotated with %s, but binding annotations should be applied " 
                      + "to its parameters instead.", member, bindingAnnotation);
  }

  private static final String CONSTRUCTOR_RULES = "Classes must have either one (and only one) constructor " 
    + "annotated with @Inject or a zero-argument constructor that is not private.";

  public Errors missingConstructor(Class<?> implementation) {
    return addMessage("Could not find a suitable constructor in %s. " + CONSTRUCTOR_RULES, implementation);
  }

  public Errors tooManyConstructors(Class<?> implementation) {
    return addMessage("%s has more than one constructor annotated with @Inject. "
        + CONSTRUCTOR_RULES, implementation);
  }

 
  public Errors voidProviderMethod() {
    return addMessage("Provider methods must return a value. Do not return void.");
  }

  public Errors missingConstantValues() {
    return addMessage("Missing constant value. Please call to(...).");
  }

  public Errors cannotInjectInnerClass(Class<?> type) {
    return addMessage("Injecting into inner classes is not supported.  "
        + "Please use a 'static' class (top-level or nested) instead of %s.", type);
  }

  public Errors duplicateBindingAnnotations(Member member, Class<? extends Annotation> a, Class<? extends Annotation> b) {
    return addMessage("%s has more than one annotation annotated with @BindingAnnotation: " + "%s and %s", member, a, b);
  }

  public Errors staticInjectionOnInterface(Class<?> clazz) {
    return addMessage("%s is an interface, but interfaces have no static injection points.", clazz);
  }

  public Errors cannotInjectFinalField(Field field) {
    return addMessage("Injected field %s cannot be final.", field);
  }

  public Errors cannotInjectAbstractMethod(Method method) {
    return addMessage("Injected method %s cannot be abstract.", method);
  }

  public Errors cannotInjectNonVoidMethod(Method method) {
    return addMessage("Injected method %s must return void.", method);
  }

  public Errors cannotInjectMethodWithTypeParameters(Method method) {
    return addMessage("Injected method %s cannot declare type parameters of its own.", method);
  }

  public Errors duplicateScopeAnnotations(Class<? extends Annotation> a, Class<? extends Annotation> b) {
    return addMessage("More than one scope annotation was found: %s and %s.", a, b);
  }

  public Errors recursiveBinding() {
    return addMessage("Binding points to itself.");
  }

  public Errors errorInjectingMethod(Throwable cause) {
    return errorInUserCode(cause, "Error injecting method, %s", cause);
  }

  public Errors errorInjectingConstructor(Throwable cause) {
    return errorInUserCode(cause, "Error injecting constructor, %s", cause);
  }

  public Errors errorInProvider(RuntimeException runtimeException) {
    Throwable unwrapped = unwrap(runtimeException);
    return errorInUserCode(unwrapped, "Error in custom provider, %s", unwrapped);
  }

  public Errors errorEnhancingClass(Class<?> clazz, Throwable cause) {
    return errorInUserCode(cause, "Unable to method intercept: %s", clazz);
  }

  public static Collection<Message> getMessagesFromThrowable(Throwable throwable) {
    if (throwable instanceof AccessDeniedException) {
      return ((AccessDeniedException) throwable).getErrorMessages();
    } else if (throwable instanceof ServiceException) {
      return ((ServiceException) throwable).getErrorMessages();
    } else if (throwable instanceof NotFoundException) {
      return ((NotFoundException) throwable).getErrorMessages();
    } else {
      return ImmutableSet.of();
    }
  }

  public Errors errorInUserCode(Throwable cause, String messageFormat, Object... arguments) {
    Collection<Message> messages = getMessagesFromThrowable(cause);

    if (!messages.isEmpty()) {
      return merge(messages);
    } else {
      return addMessage(cause, messageFormat, arguments);
    }
  }

  private Throwable unwrap(RuntimeException runtimeException) {
   if(runtimeException instanceof Exceptions.UnhandledCheckedUserException) {
     return runtimeException.getCause();
   } else {
     return runtimeException;
   }
  }

  public Errors cannotInjectRawProvider() {
    return addMessage("Cannot inject a Provider that has no type parameter");
  }

  public Errors cannotInjectRawMembersInjector() {
    return addMessage("Cannot inject a MembersInjector that has no type parameter");
  }

  public Errors cannotInjectTypeLiteralOf(Type unsupportedType) {
    return addMessage("Cannot inject a TypeLiteral of %s", unsupportedType);
  }

  public Errors cannotInjectRawTypeLiteral() {
    return addMessage("Cannot inject a TypeLiteral that has no type parameter");
  }

  public Errors cannotSatisfyCircularDependency(Class<?> expectedType) {
    return addMessage("Tried proxying %s to support a circular dependency, but it is not an interface.", expectedType);
  }

  public Errors circularProxiesDisabled(Class<?> expectedType) {
    return addMessage("Tried proxying %s to support a circular dependency, but circular proxies are disabled.", expectedType);
  }

  public void throwAccessDeniedExceptionIfErrorsExist() {
    if (!hasErrors()) {
      return;
    }

    throw new AccessDeniedException(getMessages());
  }

  public void throwNotFoundExceptionIfErrorsExist() {
    if (!hasErrors()) {
      return;
    }

    throw new NotFoundException(getMessages());
  }

  public void throwServiceExceptionIfErrorsExist() {
    if (!hasErrors()) {
      return;
    }

    throw new ServiceException(getMessages());
  }

  private Message merge(Message message) {
    List<Object> sources = Lists.newArrayList();
    sources.addAll(getSources());
    sources.addAll(message.getSources());
    return new Message(sources, message.getMessage(), message.getCause());
  }

  public Errors merge(Collection<Message> messages) {
    for (Message message : messages) {
      addMessage(merge(message));
    }
    return this;
  }

  public Errors merge(Errors moreErrors) {
    if (moreErrors.root == root || moreErrors.root.errors == null) {
      return this;
    }

    merge(moreErrors.root.errors);
    return this;
  }

  public List<Object> getSources() {
    List<Object> sources = Lists.newArrayList();
    for (Errors e = this; e != null; e = e.parent) {
      if (e.source != SourceProvider.UNKNOWN_SOURCE) {
        sources.add(0, e.source);
      }
    }
    return sources;
  }

  public void throwIfNewErrors(int expectedSize) throws ErrorsException {
    if (size() == expectedSize) {
      return;
    }

    throw toException();
  }

  public ErrorsException toException() {
    return new ErrorsException(this);
  }

  public boolean hasErrors() {
    return root.errors != null;
  }

  public Errors addMessage(String messageFormat, Object... arguments) {
    return addMessage(null, messageFormat, arguments);
  }

  private Errors addMessage(Throwable cause, String messageFormat, Object... arguments) {
    String message = format(messageFormat, arguments);
    addMessage(new Message(getSources(), message, cause));
    return this;
  }

  public Errors addMessage(Message message) {
    if (root.errors == null) {
      root.errors = Lists.newArrayList();
    }
    root.errors.add(message);
    return this;
  }

  public static String format(String messageFormat, Object... arguments) {
    for (int i = 0; i < arguments.length; i++) {
      arguments[i] = Errors.convert(arguments[i]);
    }
    return String.format(messageFormat, arguments);
  }

  public List<Message> getMessages() {
    if (root.errors == null) {
      return ImmutableList.of();
    }

    List<Message> result = Lists.newArrayList(root.errors);
    Collections.sort(result, new Comparator<Message>() {
      public int compare(Message a, Message b) {
        return a.getSource().compareTo(b.getSource());
      }
    });

    return result;
  }

  /** Returns the formatted message for an exception with the specified messages. */
  public static String format(String heading, Collection<Message> errorMessages) {
    Formatter fmt = new Formatter().format(heading).format(":%n%n");
    int index = 1;
    boolean displayCauses = getOnlyCause(errorMessages) == null;

    for (Message errorMessage : errorMessages) {
      fmt.format("%s) %s%n", index++, errorMessage.getMessage());

      List<Object> dependencies = errorMessage.getSources();
      for (int i = dependencies.size() - 1; i >= 0; i--) {
        Object source = dependencies.get(i);
        formatSource(fmt, source);
      }

      Throwable cause = errorMessage.getCause();
      if (displayCauses && cause != null) {
        StringWriter writer = new StringWriter();
        cause.printStackTrace(new PrintWriter(writer));
        fmt.format("Caused by: %s", writer.getBuffer());
      }

      fmt.format("%n");
    }

    if (errorMessages.size() == 1) {
      fmt.format("1 error");
    } else {
      fmt.format("%s errors", errorMessages.size());
    }

    return fmt.toString();
  }
  
  /**
   * Returns the cause throwable if there is exactly one cause in {@code messages}. If there are
   * zero or multiple messages with causes, null is returned.
   */
  public static Throwable getOnlyCause(Collection<Message> messages) {
    Throwable onlyCause = null;
    for (Message message : messages) {
      Throwable messageCause = message.getCause();
      if (messageCause == null) {
        continue;
      }

      if (onlyCause != null) {
        return null;
      }

      onlyCause = messageCause;
    }

    return onlyCause;
  }

  public int size() {
    return root.errors == null ? 0 : root.errors.size();
  }

  private static abstract class Converter<T> {

    final Class<T> type;

    Converter(Class<T> type) {
      this.type = type;
    }

    boolean appliesTo(Object o) {
      return o != null && type.isAssignableFrom(o.getClass());
    }

    String convert(Object o) {
      return toString(type.cast(o));
    }

    abstract String toString(T t);
  }

  private static final Collection<Converter<?>> converters = ImmutableList.of(
      new Converter<Class>(Class.class) {
        public String toString(Class c) {
          return c.getName();
        }
      },
      new Converter<Member>(Member.class) {
        public String toString(Member member) {
          return Classes.toString(member);
        }
      });

  public static Object convert(Object o) {
    for (Converter<?> converter : converters) {
      if (converter.appliesTo(o)) {
        return converter.convert(o);
      }
    }
    return o;
  }

  public static void formatSource(Formatter formatter, Object source) {
    if (source instanceof Class) {
      formatter.format("  at %s%n", StackTraceElements.forType((Class<?>) source));
    } else if (source instanceof Member) {
      formatter.format("  at %s%n", StackTraceElements.forMember((Member) source));
    } else {
      formatter.format("  at %s%n", source);
    }
  }

  
}
