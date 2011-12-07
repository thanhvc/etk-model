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
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Nov 24, 2011  
 */
public interface Element {

  /**
   * Returns an arbitrary object containing information about the "place" where this element was
   * configured. Used by Guice in the production of descriptive error messages.
   *
   * <p>Tools might specially handle types they know about; {@code StackTraceElement} is a good
   * example. Tools should simply call {@code toString()} on the source object if the type is
   * unfamiliar.
   */
  Object getSource();

  /**
   * Accepts an element visitor. Invokes the visitor method specific to this element's type.
   *
   * @param visitor to call back on
   */
  <T> T acceptVisitor(ElementVisitor<T> visitor);
}
