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
package org.exoplatform.social.core.storage.impl;

import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.social.core.book.model.Book;
import org.exoplatform.social.core.chromattic.entity.BookEntity;
import org.exoplatform.social.core.chromattic.entity.ProviderEntity;
import org.exoplatform.social.core.storage.IdentityStorageException;
import org.exoplatform.social.core.storage.api.BookStorage;
import org.exoplatform.social.core.storage.exception.NodeAlreadyExistsException;
import org.exoplatform.social.core.storage.exception.NodeNotFoundException;

/**
 * Created by The eXo Platform SAS
 * Author : thanh_vucong
 *          thanhvucong.78@gmail.com
 * Nov 12, 2011  
 */
public class BookStorageImpl extends AbstractStorage implements BookStorage {

  /** Logger */
  private static final Log LOG = ExoLogger.getLogger(IdentityStorageImpl.class);

  private BookStorage bookStorage;
  
  private BookStorage getStorage() {
    return (bookStorage != null ? bookStorage : this);
  }

  @Override
  public void saveBook(Book book) throws IdentityStorageException {
    try {
      try {
        _findById(BookEntity.class, book.getId());
        _saveBook(book);
      }
      catch (NodeNotFoundException e) {
        _createBook(book);
        _saveBook(book);
      }
    }
    catch (NodeAlreadyExistsException e1) {
      throw new IdentityStorageException(IdentityStorageException.Type.FAIL_TO_SAVE_IDENTITY, e1.getMessage(), e1);
    }
    catch (NodeNotFoundException e1) {
      throw new IdentityStorageException(IdentityStorageException.Type.FAIL_TO_SAVE_IDENTITY, e1.getMessage(), e1);
    }
  }
  
  protected BookEntity _createBook(final Book book) throws NodeAlreadyExistsException {

    // Get provider
    ProviderEntity providerEntity = getProviderRoot().getProvider(book.getProviderId());

    // Create Identity
    if (providerEntity.getIdentities().containsKey(book.getRemoteId())) {
      throw new NodeAlreadyExistsException("Identity " + book.getRemoteId() + " already exists");
    }

    BookEntity bookEntity = providerEntity.createBook();
    providerEntity.getBooks().put(book.getRemoteId(), bookEntity);
    bookEntity.setProviderId(book.getProviderId());
    bookEntity.setRemoteId(book.getRemoteId());
    bookEntity.setName(book.getName());
    book.setId(bookEntity.getId());

    //
    getSession().save();

    //
    LOG.debug(String.format(
        "Book %s:%s (%s) created",
        book.getProviderId(),
        book.getRemoteId(),
        book.getId()
    ));

    //
    return bookEntity;
  }
  
  protected void _saveBook(final Book book) throws NodeAlreadyExistsException, NodeNotFoundException {

    BookEntity bookEntity;

    bookEntity = _findById(BookEntity.class, book.getId());

    // change name
    if (!bookEntity.getName().equals(book.getRemoteId())) {
      bookEntity.setName(book.getRemoteId());
    }

    if (!bookEntity.getProviderId().equals(book.getProviderId())) {

      // Get provider
      ProviderEntity providerEntity = getProviderRoot().getProvider(book.getProviderId());

      // Move identity
      providerEntity.getBooks().put(book.getRemoteId(), bookEntity);
    }

    //
    bookEntity.setProviderId(book.getProviderId());
    bookEntity.setRemoteId(book.getRemoteId());
    bookEntity.setName(book.getName());
    book.setId(bookEntity.getId());

    //
    getSession().save();


    //
    LOG.debug(String.format(
        "Book %s:%s (%s) saved",
        book.getProviderId(),
        book.getRemoteId(),
        book.getId()
    ));
  }

  @Override
  public Book updateBook(final Book book) throws IdentityStorageException {
    //
    saveBook(book);

    //
    return findBookById(book.getId());
  }

  @Override
  public Book findBookById(String nodeId) throws IdentityStorageException {
    try {

      //
      BookEntity bookEntity = _findById(BookEntity.class, nodeId);
      Book book = new Book(nodeId);
      book.setRemoteId(bookEntity.getRemoteId());
      book.setProviderId(bookEntity.getProviderId());
      book.setProviderId(bookEntity.getName());

      //
      return book;
    }
    catch (NodeNotFoundException e) {
      return null;
    }
  }

  @Override
  public void deleteBook(Book book) throws IdentityStorageException {
    try {
      _deleteBook(book);
    }
    catch (NodeNotFoundException e) {
      throw new IdentityStorageException(IdentityStorageException.Type.FAIL_TO_DELETE_IDENTITY, e.getMessage(), e);
    }
    
  }
  
  protected void _deleteBook(final Book book) throws NodeNotFoundException {

    //
    if (book == null || book.getId() == null) {
      throw new IllegalArgumentException();
    }

    //
    BookEntity bookEntity = _findById(BookEntity.class, book.getId());
    book.setProviderId(bookEntity.getProviderId());
    book.setRemoteId(bookEntity.getRemoteId());

    //
    getSession().remove(bookEntity);

    //
    getSession().save();

    //
    LOG.debug(String.format(
        "Book %s:%s (%s) deleted",
        book.getProviderId(),
        book.getRemoteId(),
        book.getId()
    ));
  }
  
  
}
