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
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by The eXo Platform SAS Author : eXoPlatform exo@exoplatform.com Nov
 * 23, 2011
 */
public class ListenerThread extends Thread {
  ServerSocket server;

  public ListenerThread(ServerSocket socket) {
    this.server = socket;
  }

  public void run() {
    try {
      while (true) {
        System.out.println("about to wait");
        Socket socket = server.accept();
        System.out.println("opened socket from client");
        InputStream iStream = socket.getInputStream();
        int length = iStream.read();
        byte[] bytes = new byte[length];
        iStream.read(bytes);
        String className = new String(bytes);
        Launcher.go(className);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Failed to start");
    }
  }
}
