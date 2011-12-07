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
package org.etk.model.sandbox.jvm;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by The eXo Platform SAS Author : eXoPlatform exo@exoplatform.com Nov
 * 23, 2011
 */
public class Launcher {

  static final int socketPort = 9876;

  public void launch(String className) {
    boolean launched = false;
    while (!launched) {
      System.out.println("Trying to launch:" + className);
      Socket s = findService();
      if (s != null) {
        System.out.println("found service");
        try {
          OutputStream oStream = s.getOutputStream();
          byte[] bytes = className.getBytes();
          oStream.write(bytes.length);
          oStream.write(bytes);
          oStream.close();
          launched = true;
          System.out.println(className);
        } catch (IOException e) {
          System.out.println("Couldn't talk to service");
        }
      } else {
        try {
          System.out.println("Starting new service");
          ServerSocket server = new ServerSocket(socketPort);
          Launcher.go(className);
          Thread listener = new ListenerThread(server);
          listener.start();
          launched = true;
          System.out.println("started service listener");
        } catch (IOException e) {
          System.out.println("Socket contended, will try again");
        }
      }
    }
  }

  protected Socket findService() {
    try {
      Socket s = new Socket(InetAddress.getLocalHost(), socketPort);
      return s;
    } catch (IOException e) {
      // couldn't find a service provider
      return null;
    }
  }

  public static void go(final String className) {
    System.out.println("running a " + className);
    Thread thread = new Thread() {
      public void run() {
        try {
          Class clazz = Class.forName(className);
          Class[] argsTypes = { String[].class };
          Object[] args = { new String[0] };
          Method method = clazz.getMethod("main", argsTypes);
          method.invoke(clazz, args);
        } catch (Exception e) {
          System.out.println("coudn't run the " + className);
        }
      }
    }; // end thread sub-class
    thread.start();
  }

  public static void main(String[] args) {
    Launcher l = new Launcher();
    l.launch(args[0]);
  }
}
