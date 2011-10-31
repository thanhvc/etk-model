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
package org.etk.model.sandbox.spi.utils;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Oct 31, 2011  
 */
import java.util.logging.Logger;

/**
 * Enables simple performance monitoring.
 *
 * @author crazybob@google.com (Bob Lee)
 */
public final class Stopwatch {
  private static final Logger logger = Logger.getLogger(Stopwatch.class.getName());

  private long start = System.currentTimeMillis();

  /**
   * Resets and returns elapsed time in milliseconds.
   */
  public long reset() {
    long now = System.currentTimeMillis();
    try {
      return now - start;
    } finally {
      start = now;
    }
  }

  /**
   * Resets and logs elapsed time in milliseconds.
   */
  public void resetAndLog(String label) {
    logger.fine(label + ": " + reset() + "ms");
  }
}

