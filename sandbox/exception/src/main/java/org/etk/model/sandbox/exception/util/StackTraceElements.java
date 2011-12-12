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

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.util.Map;

/**
 * Creates stack trace elements for members.
 *
 */
public class StackTraceElements {

  /*if[AOP]*/
  static final Map<Class<?>, LineNumbers> lineNumbersCache = new MapMaker().weakKeys().softValues()
      .makeComputingMap(new Function<Class<?>, LineNumbers>() {
        public LineNumbers apply(Class<?> key) {
          try {
            return new LineNumbers(key);
          }
          catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      });
  /*end[AOP]*/

  public static Object forMember(Member member) {
    if (member == null) {
      return SourceProvider.UNKNOWN_SOURCE;
    }

    Class declaringClass = member.getDeclaringClass();

    /*if[AOP]*/
    LineNumbers lineNumbers = lineNumbersCache.get(declaringClass);
    String fileName = lineNumbers.getSource();
    Integer lineNumberOrNull = lineNumbers.getLineNumber(member);
    int lineNumber = lineNumberOrNull == null ? lineNumbers.getFirstLine() : lineNumberOrNull;
    /*end[AOP]*/
    /*if[NO_AOP]
    String fileName = null;
    int lineNumber = -1;
    end[NO_AOP]*/

    Class<? extends Member> memberType = Classes.memberType(member);
    String memberName = memberType == Constructor.class ? "<init>" : member.getName();
    return new StackTraceElement(declaringClass.getName(), memberName, fileName, lineNumber);
  }

  public static Object forType(Class<?> implementation) {
    /*if[AOP]*/
    LineNumbers lineNumbers = lineNumbersCache.get(implementation);
    int lineNumber = lineNumbers.getFirstLine();
    String fileName = lineNumbers.getSource();
    /*end[AOP]*/
    /*if[NO_AOP]
    String fileName = null;
    int lineNumber = -1;
    end[NO_AOP]*/

    return new StackTraceElement(implementation.getName(), "class", fileName, lineNumber);
  }
}
