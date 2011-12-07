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

/**
 * Rethrows user-code exceptions in wrapped exceptions so that Errors can target the correct
 * exception
 * 
 * @author thanh_vucong
 *
 */
class Exceptions {
  /**
   * Rethrows the exception (or it's cause, if it has one) directly if possible.
   * If it was a checked exception, this wraps the exception in a stack trace
   * with no frames, so that the exception is shown immediately with no frames
   * above it.
   */
  public static RuntimeException rethrowCause(Throwable throwable) {
    Throwable cause = throwable;
    if(cause.getCause() != null) {
      cause = cause.getCause();
    }
    return rethrow(cause);
  }
  
  /** Rethrows the exception. */
  public static RuntimeException rethrow(Throwable throwable) {    
    if(throwable instanceof RuntimeException) {
      throw (RuntimeException)throwable;
    } else if(throwable instanceof Error) {
      throw (Error)throwable;
    } else {
      throw new UnhandledCheckedUserException(throwable);
    }
  }

  /**
   * A marker exception class that we look for in order to unwrap the exception
   * into the user exception, to provide a cleaner stack trace.
   */
  static class UnhandledCheckedUserException extends RuntimeException {
    public UnhandledCheckedUserException(Throwable cause) {
      super(cause);
    }
  }
}