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

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.etk.model.sandbox.exception.util.SourceProvider;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Nov 24, 2011  
 */
public final class Message implements Serializable, Element {
  private final String message;
  private final Throwable cause;
  private final List<Object> sources;

  /**
   * @since 2.0
   */
  public Message(List<Object> sources, String message, Throwable cause) {
    this.sources = ImmutableList.copyOf(sources);
    this.message = checkNotNull(message, "message");
    this.cause = cause;
  }

  public Message(Object source, String message) {
    this(ImmutableList.of(source), message, null);
  }

  public Message(String message) {
    this(ImmutableList.of(), message, null);
  }

  public String getSource() {
    return sources.isEmpty()
        ? SourceProvider.UNKNOWN_SOURCE.toString()
        : Errors.convert(sources.get(sources.size() - 1)).toString();
  }

  /** @since 2.0 */
  public List<Object> getSources() {
    return sources;
  }

  /**
   * Gets the error message text.
   */
  public String getMessage() {
    return message;
  }

  /** @since 2.0 */
  public <T> T acceptVisitor(ElementVisitor<T> visitor) {
    return visitor.visit(this);
  }

  /**
   * Returns the throwable that caused this message, or {@code null} if this
   * message was not caused by a throwable.
   *
   * @since 2.0
   */
  public Throwable getCause() {
    return cause;
  }

  @Override public String toString() {
    return message;
  }

  @Override public int hashCode() {
    return message.hashCode();
  }

  @Override public boolean equals(Object o) {
    if (!(o instanceof Message)) {
      return false;
    }
    Message e = (Message) o;
    return message.equals(e.message) && Objects.equal(cause, e.cause) && sources.equals(e.sources);
  }

   /**
   * When serialized, we eagerly convert sources to strings. This hurts our formatting, but it
   * guarantees that the receiving end will be able to read the message.
   */
  private Object writeReplace() throws ObjectStreamException {
    Object[] sourcesAsStrings = sources.toArray();
    for (int i = 0; i < sourcesAsStrings.length; i++) {
      sourcesAsStrings[i] = Errors.convert(sourcesAsStrings[i]).toString();
    }
    return new Message(ImmutableList.copyOf(sourcesAsStrings), message, cause);
  }

  private static final long serialVersionUID = 0;
}
