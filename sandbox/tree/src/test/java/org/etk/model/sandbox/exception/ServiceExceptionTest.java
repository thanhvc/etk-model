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

import junit.framework.TestCase;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Nov 25, 2011  
 */
public class ServiceExceptionTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }
  
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  private static class Activity {
    private String id;
    private String content;
    
    public String getId() {
      return id;
    }
    public void setId(String id) {
      this.id = id;
    }
    public String getContent() {
      return content;
    }
    public void setContent(String content) {
      this.content = content;
    }
  }
  
  private static class ActivityUI {
    
  }
  
  private static class ActivityManager {
    ActivityStorage storage = null;
    public void insert(Errors errors, Activity activity) {
      if (activity == null) {
        errors.withSource(this).addMessage("Activity must not be null!");
      }
      
      storage = new ActivityStorage();
      storage.insert(errors, activity);
      
    }
  }
  
  private static class ActivityStorage {
    public void insert(Errors errors, Activity activity) {
      
    }
  }
}
