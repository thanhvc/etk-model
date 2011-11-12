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
package org.exoplatform.social.core.storage.api;

import org.exoplatform.social.core.book.model.Book;
import org.exoplatform.social.core.storage.IdentityStorageException;

/**
 * Created by The eXo Platform SAS
 * Author : thanh_vucong
 *          thanhvucong.78@gmail.com
 * Nov 12, 2011  
 */
public interface BookStorage {
  
  /**
   * Saves book.
   *
   * @param book the book
   * @throws IdentityStorageException
   */
  public void saveBook(final Book book) throws IdentityStorageException;

  /**
   * Updates existing identity's properties.
   *
   * @param identity the identity to be updated.
   * @return the updated identity.
   * @throws IdentityStorageException
   * @since  1.2.0-GA
   */
  public Book updateBook(final Book book) throws IdentityStorageException;

  /**
   * Gets the book by his id.
   *
   * @param nodeId the id of identity
   * @return the identity
   * @throws IdentityStorageException
   */
  public Book findBookById(final String nodeId) throws IdentityStorageException;

  /**
   * Deletes an book from JCR
   *
   * @param book
   * @throws IdentityStorageException
   */
  public void deleteBook(final Book book) throws IdentityStorageException;
}
