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

import java.util.HashMap;
import java.util.Map;

/**
 * The class which contains the error information.
 * 
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Dec 12, 2011  
 */
public class ErrorInfo {
  
  public static int ERROR_TYPE_CLIENT = 1;
  public static int ERROR_TYPE_SERVER = 2;
  public static int ERROR_TYPE_MOBILE = 3;
  
  public static int SEVERITY_WARNING = 1;
  public static int SEVERITY_ERROR = 2;
  
  //The error cause, if an alien exception is caught and wrapped
  protected Throwable cause = null;
  /**
   * A unique id that identifies this error.
   * <p>
   * The errorId tells <b>what</b> went wrong, like FILE_LOAD_ERROR. The id only
   * has to be unique within the same context, meaning the combination of contextId 
   * and errorId should be unique through out your application
   */
  
  protected String errorId = null;
  /**
   * A unique id that identifies the context where the error occurred.
   * <p>
   * The contextId tells <b>where</b> the error occurred(in what class, component, layer etc.)
   * The contextId and errorId combination used at any specific exception 
   * handling point should be unique through out the application.
   */
  protected String contextId = null;
  
  /**
   * The errorType field tells whether the error was caused by errornous input 
   * to the application, an external service that failed, or an internal error.
   * The idea is to use this field to indicate to the exception catching code 
   * what to do with this error. Should only the user be notified, or should 
   * the application operators and developers be notified too? 
   */
  protected int errorType = -1;
  
  /**
   * Contains the severity of the error. E.g. WARNING, ERROR, FATAL etc. 
   * It is up to you to define the severity levels for your application. 
   */
  protected int severity = -1;
  
  /**
   * Also keep in mind that many errors will be reported to the user with 
   * the same standard text, like "An error occurred internall. 
   * It has been logged, and the application operators has been notified". 
   * Thus, you may want to use the same user error description or error key 
   * for many different errors.
   */
  protected String userErrorDescription = null;
  
  /**
   * Contains a description of the error with all 
   * the necessary details needed for the application operators, 
   * and possibly the application developers, to understand 
   * what error occurred.
   */
  protected String errorDescription = null;
  /**
   * Contains a description of how the error can be corrected, 
   * if you know how. For instance, if loading a configuration 
   * file fails, this text may say that the operator should check 
   * that the configuration file that failed to load is located in 
   * the correct directory. 
   */
  protected String errorCorrection = null;

  /**
   * A Map of any additional parameters needed to construct 
   * a meaningful error description, either for 
   * the users or the application operators and developers. 
   * For instance, if a file fails to load, 
   * the file name could be kept in this map. 
   * Or, if an operation fails which require 3 parameters to succeed, 
   * the names and values of each parameter could be kept in this Map. 
   */
  protected Map<String, Object> parameters = new HashMap<String, Object>();

  public Throwable getCause() {
    return cause;
  }

  public void setCause(Throwable cause) {
    this.cause = cause;
  }

  public String getErrorId() {
    return errorId;
  }

  public void setErrorId(String errorId) {
    this.errorId = errorId;
  }

  public String getContextId() {
    return contextId;
  }

  public void setContextId(String contextId) {
    this.contextId = contextId;
  }

  public int getErrorType() {
    return errorType;
  }

  public void setErrorType(int errorType) {
    this.errorType = errorType;
  }

  public int getSeverity() {
    return severity;
  }

  public void setSeverity(int severity) {
    this.severity = severity;
  }

  public String getUserErrorDescription() {
    return userErrorDescription;
  }

  public void setUserErrorDescription(String userErrorDescription) {
    this.userErrorDescription = userErrorDescription;
  }

  public String getErrorDescription() {
    return errorDescription;
  }

  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }

  public String getErrorCorrection() {
    return errorCorrection;
  }

  public void setErrorCorrection(String errorCorrection) {
    this.errorCorrection = errorCorrection;
  }

  public Map<String, Object> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, Object> parameters) {
    this.parameters = parameters;
  }
  
  
  
}
