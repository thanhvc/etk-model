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
package org.exoplatform.social.core.book.model;

import org.exoplatform.social.core.identity.model.GlobalId;

/**
 * Created by The eXo Platform SAS
 * Author : thanh_vucong
 *          thanhvucong.78@gmail.com
 * Nov 12, 2011  
 */
public class Book {

  /** The id. */
  String  id;

  /** The remote id. */
  String  remoteId;

  /** The provider id. */
  String  providerId;
  
  /** The name. */
  String  name;

  /** The global id. */
  GlobalId globalId;

  /**
   * Instantiates a new identity.
   *
   * @param id the id
   */
  public Book(String id) {
    this.id = id;
  }

  /**
   * Instantiates a new identity
   *
   * @param providerId the provider id of identity
   * @param remoteId the remote id of identity
   */
  public Book(String providerId, String remoteId) {
    this.remoteId = remoteId;
    this.providerId = providerId;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(String id) {
    this.id = id;
  }

   /**
   * Gets the remote id.
   *
   * @return the remote id
   */
  public String getRemoteId() {
    return remoteId;
  }

  /**
   * Sets the remote id.
   *
   * @param remoteId the new remote id
   */
  public void setRemoteId(String remoteId) {
    this.remoteId = remoteId;
  }

  /**
   * Gets the provider id.
   *
   * @return the provider id
   */
  public String getProviderId() {
    return providerId;
  }

  /**
   * Sets the provider id.
   *
   * @param providerId the new provider id
   */
  public void setProviderId(String providerId) {
    this.providerId = providerId;
  }

  
  /** Gets name of this book */
  public String getName() {
    return name;
  }

  /** Sets name of this book.*/
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the global id string of identity
   */
  @Override
  public String toString() {
    return getGlobalId().toString();
  }

  /**
   * @return global id of identity
   */
  public GlobalId getGlobalId() {
    if(globalId == null)
      globalId = GlobalId.create(providerId, remoteId);
    return globalId;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Book) {
        return getId().equals(((Book)obj).getId());
    }
    return super.equals(obj);
  }
}
