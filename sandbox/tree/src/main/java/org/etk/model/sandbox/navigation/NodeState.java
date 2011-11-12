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

import java.io.Serializable;
import java.util.Date;

import org.etk.model.sandbox.navigation.mop.Visibility;
import org.etk.model.sandbox.navigation.utils.Safe;

/**
 * <code>NodeState</code> represents the state of a <code>Node</code> in the
 * State Machine Created by The eXo Platform SAS Author : thanh_vucong
 * thanhvucong.78@gmail.com Oct 31, 2011
 */
public class NodeState implements Serializable {

  public static final NodeState INITIAL = new NodeState.Builder().build();

  /**
   * Builder class for utility builds the NodeState
   * 
   * @author thanh_vucong
   */
  public static class Builder {
    private String label;

    private String icon;

    private long startPublicationTime;

    private long endPublicationTime;

    private Visibility visibility;

    private String pageRef;

    public Builder() {
      this.icon = null;
      this.label = null;
      this.startPublicationTime = -1;
      this.endPublicationTime = -1;
      this.visibility = Visibility.DISPLAYED;
      this.pageRef = null;
    }


    public Builder(NodeState state) throws NullPointerException {
      if (state == null) {
        throw new NullPointerException();
      }
      this.label = state.label;
      this.icon = state.icon;
      this.startPublicationTime = state.startPublicationTime;
      this.endPublicationTime = state.endPublicationTime;
      this.visibility = state.visibility;
      this.pageRef = state.pageRef;
    }

    public Builder label(String label) {
      this.label = label;
      return this;
    }

    public Builder icon(String icon) {
      this.icon = icon;
      return this;
    }

    public Builder startPublicationTime(long startPublicationTime) {
      this.startPublicationTime = startPublicationTime;
      return this;
    }

    public Builder endPublicationTime(long endPublicationTime) {
      this.endPublicationTime = endPublicationTime;
      return this;
    }

    public Builder visibility(Visibility visibility) {
      this.visibility = visibility;
      return this;
    }

    public Builder pageRef(String pageRef) {
      this.pageRef = pageRef;
      return this;
    }

    public NodeState build() {
      return new NodeState(label,
                           icon,
                           startPublicationTime,
                           endPublicationTime,
                           visibility,
                           pageRef);
    }

  }

  private final String label;

  private final String icon;

  private final long startPublicationTime;

  private final long endPublicationTime;

  private final Visibility visibility;

  private final String pageRef;

  public NodeState(String label,
                   String icon,
                   long startPublicationTime,
                   long endPublicationTime,
                   Visibility visibility,
                   String pageRef) {
    this.label = label;
    this.icon = icon;
    this.startPublicationTime = startPublicationTime;
    this.endPublicationTime = endPublicationTime;
    this.visibility = visibility;
    this.pageRef = pageRef;
  }

  public String getLabel() {
    return label;
  }

  public String getIcon() {
    return icon;
  }

  public long getStartPublicationTime() {
    return startPublicationTime;
  }

  Date getStartPublicationDate() {
    return startPublicationTime != -1 ? new Date(startPublicationTime) : null;
  }

  public long getEndPublicationTime() {
    return endPublicationTime;
  }

  Date getEndPublicationDate() {
    return endPublicationTime != -1 ? new Date(endPublicationTime) : null;
  }

  public Visibility getVisibility() {
    return visibility;
  }

  public String getPageRef() {
    return pageRef;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof NodeState) {
      NodeState that = (NodeState) o;
      return Safe.equals(label, that.label) && Safe.equals(icon, that.icon)
          && Safe.equals(startPublicationTime, that.startPublicationTime)
          && Safe.equals(endPublicationTime, that.endPublicationTime)
          && Safe.equals(visibility, that.visibility) && Safe.equals(pageRef, that.pageRef);
    }
    return false;
  }

  @Override
  public String toString() {
    return "NodeState[label=" + label + ",icon=" + icon + ",startPublicationTime="
        + startPublicationTime + ",endPublicationTime=" + endPublicationTime + ",visibility="
        + visibility + ",pageRef=" + pageRef + "]";
  }

  public Builder builder() {
    return new Builder(this);
  }
}