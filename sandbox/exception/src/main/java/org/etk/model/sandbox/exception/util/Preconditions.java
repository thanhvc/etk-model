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
package org.etk.model.sandbox.exception.util;

import org.etk.model.sandbox.exception.ApplicationException;
import org.etk.model.sandbox.exception.ErrorInfoBuilder;


/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Dec 12, 2011  
 */
public final class Preconditions {
  private Preconditions() {}
  
  
  /**
   * Ensures that an object reference passed as a parameter to the calling
   * method is not null.
   *
   * @param reference an object reference
   * @return the non-null reference that was validated
   * @throws NullPointerException if {@code reference} is null
   */
  public static <T> T checkNotNull(T reference) throws ApplicationException {
    
    if (reference == null) {
      //wrapping the NullPointerException
      throw ErrorInfoBuilder.throwNullException(new NullPointerException());
    }
    return reference;
  }
  
  /**
   * Ensures that an object reference passed as a parameter to the calling
   * method is not null.
   *
   * @param reference an object reference
   * @param errorMessage the exception message to use if the check fails; will
   *     be converted to a string using {@link String#valueOf(Object)}
   * @return the non-null reference that was validated
   * @throws NullPointerException if {@code reference} is null
   */
  public static <T> T checkNotNull(T reference, Object errorMessage) {
    if (reference == null) {
      throw new NullPointerException(String.valueOf(errorMessage));
    }
    return reference;
  }
  
  /**
   * Ensures that {@code index} specifies a valid <i>element</i> in an array,
   * list or string of size {@code size}. An element index may range from zero,
   * inclusive, to {@code size}, exclusive.
   *
   * @param index a user-supplied index identifying an element of an array, list
   *     or string
   * @param size the size of that array, list or string
   * @return the value of {@code index}
   * @throws IndexOutOfBoundsException if {@code index} is negative or is not
   *     less than {@code size}
   * @throws IllegalArgumentException if {@code size} is negative
   */
  public static int checkElementIndex(int index, int size) {
    return checkElementIndex(index, size, "index");
  }

  /**
   * Ensures that {@code index} specifies a valid <i>element</i> in an array,
   * list or string of size {@code size}. An element index may range from zero,
   * inclusive, to {@code size}, exclusive.
   *
   * @param index a user-supplied index identifying an element of an array, list
   *     or string
   * @param size the size of that array, list or string
   * @param desc the text to use to describe this index in an error message
   * @return the value of {@code index}
   * @throws IndexOutOfBoundsException if {@code index} is negative or is not
   *     less than {@code size}
   * @throws IllegalArgumentException if {@code size} is negative
   */
  public static int checkElementIndex(
      int index, int size, String desc) {
    // Carefully optimized for execution by hotspot (explanatory comment above)
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
    }
    return index;
  }

  private static String badElementIndex(int index, int size, String desc) {
    if (index < 0) {
      return format("%s (%s) must not be negative", desc, index);
    } else if (size < 0) {
      throw new IllegalArgumentException("negative size: " + size);
    } else { // index >= size
      return format("%s (%s) must be less than size (%s)", desc, index, size);
    }
  }
  
  /**
   * Substitutes each {@code %s} in {@code template} with an argument. These
   * are matched by position - the first {@code %s} gets {@code args[0]}, etc.
   * If there are more arguments than placeholders, the unmatched arguments will
   * be appended to the end of the formatted message in square braces.
   *
   * @param template a non-null string containing 0 or more {@code %s}
   *     placeholders.
   * @param args the arguments to be substituted into the message
   *     template. Arguments are converted to strings using
   *     {@link String#valueOf(Object)}. Arguments can be null.
   */
  static String format(String template,Object... args) {
    template = String.valueOf(template); // null -> "null"

    // start substituting the arguments into the '%s' placeholders
    StringBuilder builder = new StringBuilder(
        template.length() + 16 * args.length);
    int templateStart = 0;
    int i = 0;
    while (i < args.length) {
      int placeholderStart = template.indexOf("%s", templateStart);
      if (placeholderStart == -1) {
        break;
      }
      builder.append(template.substring(templateStart, placeholderStart));
      builder.append(args[i++]);
      templateStart = placeholderStart + 2;
    }
    builder.append(template.substring(templateStart));

    // if we run out of placeholders, append the extra args in square braces
    if (i < args.length) {
      builder.append(" [");
      builder.append(args[i++]);
      while (i < args.length) {
        builder.append(", ");
        builder.append(args[i++]);
      }
      builder.append(']');
    }

    return builder.toString();
  }
}
