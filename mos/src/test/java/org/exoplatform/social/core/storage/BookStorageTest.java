package org.exoplatform.social.core.storage;

import java.util.ArrayList;
import java.util.List;

import org.exoplatform.social.core.book.model.Book;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.identity.provider.OrganizationIdentityProvider;
import org.exoplatform.social.core.storage.api.BookStorage;
import org.exoplatform.social.core.storage.cache.CachedIdentityStorage;
import org.exoplatform.social.core.test.AbstractCoreTest;

/**
 * Created by IntelliJ IDEA.
 * User: zun
 * Date: Jun 17, 2010
 * Time: 9:34:56 AM
 */
public class BookStorageTest extends AbstractCoreTest {
  private BookStorage bookStorage;
  private List<Book> tearDownBookList;

  public void setUp() throws Exception {
    super.setUp();
    bookStorage = (BookStorage) getContainer().getComponentInstanceOfType(BookStorage.class);
    assertNotNull("bookStorage must not be null", bookStorage);
    tearDownBookList = new ArrayList<Book>();
  }

  public void tearDown() throws Exception {
    for (Book book : tearDownBookList) {
      bookStorage.deleteBook(book);
    }
    super.tearDown();
  }

  /**
   * Tests {@link IdenityStorage#saveIdentity(Identity)}
   *
   */
  public void testSaveBook() {
    Book tobeSavedBook = new Book(OrganizationIdentityProvider.NAME, "book1");
    tobeSavedBook.setName("My story");
    bookStorage.saveBook(tobeSavedBook);

    
    assertNotNull(tobeSavedBook.getId());
    
    //update
    final String updatedRemoteId = "book-updated";

    tobeSavedBook.setRemoteId(updatedRemoteId);

    bookStorage.saveBook(tobeSavedBook);
    
    //delete
    
    Book gotBook = bookStorage.findBookById(tobeSavedBook.getId());

    assertEquals(updatedRemoteId, gotBook.getRemoteId());
    tearDownBookList.add(gotBook);
  }
}