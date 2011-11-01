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
package org.etk.model.sandbox.navigation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by The eXo Platform SAS
 * Author : thanh_vucong
 *          thanhvucong.78@gmail.com
 * Oct 31, 2011  
 */
public class NodeChangeQueue<N> extends LinkedList<NodeChange<N>> implements NodeChangeListener<N> {

  public NodeChangeQueue() {
    
  }
  
  public NodeChangeQueue(Collection<? extends NodeChange<N>> c) {
    super(c);
  }
  
  public void broadcast(NodeChangeListener<N> listener) {
    for(NodeChange<N> change : this) {
      change.dispatch(listener);
    }
  }
  
  @Override
  public void onAdd(N target, N parent, N previous) {
    add(new NodeChange.Added<N>(parent, previous, target));    
  }

  @Override
  public void onCreate(N target, N parent, N previous, String name) {
    add(new NodeChange.Created<N>(parent, previous, target, name));
    
  }

  @Override
  public void onRemove(N target, N parent) {
    add(new NodeChange.Removed<N>(parent, target));
    
  }

  @Override
  public void onDestroy(N target, N parent) {
    add(new NodeChange.Destroyed<N>(parent, target));
    
  }

  @Override
  public void onRename(N target, N parent, String name) {
    add(new NodeChange.Renamed<N>(parent, target, name));
    
  }

  @Override
  public void onUpdate(N target, NodeState state) {
    add(new NodeChange.Updated<N>(target, state));
    
  }

  @Override
  public void onMove(N target, N from, N to, N previous) {
    add(new NodeChange.Moved<N>(from, to, previous, target));
    
  }

}
