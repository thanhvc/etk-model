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

import com.google.common.collect.ImmutableSet;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkState;

/**
 * Thrown when a programming error such as a misplaced annotation, illegal binding, or unsupported
 * scope is found. Clients should catch this exception, log it, and stop execution.
 *
 */
public final class AccessDeniedException extends RuntimeException {

  private final ImmutableSet<Message> messages;
  private Object partialValue = null;

  /** Creates a ConfigurationException containing {@code messages}. */
  public AccessDeniedException(Iterable<Message> messages) {
    this.messages = ImmutableSet.copyOf(messages); 
    initCause(Errors.getOnlyCause(this.messages));
  }

  /** Returns a copy of this configuration exception with the specified partial value. */
  public AccessDeniedException withPartialValue(Object partialValue) {
    checkState(this.partialValue == null, "Can't clobber existing partial value %s with %s", this.partialValue, partialValue);
    AccessDeniedException result = new AccessDeniedException(messages);
    result.partialValue = partialValue;
    return result;
  }

  /** Returns messages for the errors that caused this exception. */
  public Collection<Message> getErrorMessages() {
    return messages;
  }

  /**
   * Returns a value that was only partially computed due to this exception. The caller can use
   * this while collecting additional configuration problems.
   *
   * @return the partial value, or {@code null} if none was set. The type of the partial value is
   *      specified by the throwing method.
   */
  @SuppressWarnings("unchecked") // this is *extremely* unsafe. We trust the caller here.
  public <E> E getPartialValue() {
    return (E) partialValue;
  }

  @Override public String getMessage() {
    return Errors.format("eXo social client security errors", messages);
  }

  private static final long serialVersionUID = 0;
}
