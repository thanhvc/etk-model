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
 * Dec 12, 2011  
 */
public final class ErrorInfoBuilder {

  private ErrorInfoBuilder() {}
  
  
  public static ApplicationException throwNullException(Throwable throwable) {
    ApplicationException exception = new ApplicationException();
    return exception;
  }
  /**
   * Gathering Information when you want to throw NullPointerException with 
   * provided argument value is NULL.
   * 
   * @param source
   * @param methodName
   * @param argName
   * @param argValue
   * @return
   */
  public static ApplicationException throwNullException(Class<?> source,
                                         String methodName,
                                         String argName,
                                         Object argValue) {
    ApplicationException exception = new ApplicationException();
    
    ErrorInfo info = exception.addInfo();
    info.setErrorId("ArgumentNotNull");
    info.setContextId("ComponentA");

    info.setErrorType(ErrorInfo.ERROR_TYPE_CLIENT);
    info.setSeverity(ErrorInfo.SEVERITY_ERROR);

    info.setErrorDescription(argName + " argument is null!");
    info.setErrorCorrection(argName + " argument must not be null!");

    info.getParameters().put(argName, argValue);
    
    exception.addInfo(info);
    
    return exception;
  }
}
