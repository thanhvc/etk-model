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

import static org.etk.model.sandbox.exception.util.Preconditions.checkNotNull;


/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Nov 25, 2011  
 */
public class A {

  public void showMessage(String message) throws ApplicationException {

    checkNotNull(message);
    //error detection
    /**
    if (message == null) {
      throw ErrorInfoBuilder.throwNullException(A.class, "showMessage", "message", message);
    }*/
    
    System.out.println(message);
  }
  
  public void showMessage1(String message1) {
    
  }
  
  public void showMessage2() {
    
  }
}
