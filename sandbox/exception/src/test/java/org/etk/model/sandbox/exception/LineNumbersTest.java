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

import java.lang.reflect.Method;

import org.etk.model.sandbox.exception.util.LineNumbers;

import junit.framework.TestCase;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Nov 25, 2011  
 */
public class LineNumbersTest extends TestCase {
  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }
  
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testAClass() throws Exception {
    LineNumbers lineNumbers = new LineNumbers(A.class);
    
    Method[] methods = A.class.getDeclaredMethods();
    for(Method method : methods) {
      System.out.println("Method::" + method.getName() + " at line:" + lineNumbers.getLineNumber(method));
    }
    
  }
  
}
