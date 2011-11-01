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
package org.etk.model.sandbox.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


import junit.framework.TestCase;

/**
 * Created by The eXo Platform SAS Author : eXoPlatform exo@exoplatform.com Oct
 * 31, 2011
 */
public class ListTreeTest extends TestCase {

  public static class IntegerTree extends ListTree<IntegerTree> {

    /** . */
    private final int value;

    public IntegerTree(int value) {
      this.value = value;
    }
  }

  private static IntegerTree tree(String name, int value, IntegerTree... trees) {
    IntegerTree tree = new IntegerTree(value);
    if (trees != null) {
      for (IntegerTree child : trees) {
        tree.insertAt(null, child);
      }
    }
    return tree;
  }

  private void assertChildren(IntegerTree tree, Integer... expected) {
    List<Integer> children = new ArrayList<Integer>();
    for (Iterator<IntegerTree> iterator = tree.listIterator(); iterator.hasNext();) {
      IntegerTree next = iterator.next();
      children.add(next.value);
    }
    assertEquals(Arrays.asList(expected), children);
  }

  private void assertAllChildren(IntegerTree tree, Integer... expected) {
    List<Integer> children = new ArrayList<Integer>();
    for (IntegerTree current = tree.getFirst(); current != null; current = current.getNext()) {
      children.add(current.value);
    }
    assertEquals(Arrays.asList(expected), children);
  }

  private void assertAllChildren(IntegerTree tree) {
    assertAllChildren(tree, new Integer[0]);
  }

  public void testInsert1() {
    IntegerTree root = tree("", 0);
    assertChildren(root);
    assertAllChildren(root);

    //
    root = tree("", 0);
    root.insertAt(0, tree("a", 1));
    assertChildren(root, 1);
    assertAllChildren(root, 1);

  }
  /**
   * Move b tree from last to At.
   */
  public void testInsertMove1() {
    //creates a is child
    IntegerTree a = tree("a", 1);
    //creates b is child
    IntegerTree b = tree("b", 2);
    //creates the roo1 with two children such as: a,b
    IntegerTree root1 = tree("", 0, a, b);
    
    assertAllChildren(root1, 1, 2);
    
    //change position of b
    root1.insertAt(0, b);
    
    assertAllChildren(root1, 2, 1);
    
  }
  
  public void testInsertMove2() {
    IntegerTree a = tree("a", 1);
    IntegerTree root1 = tree("", 0, a);
    
    //
    root1.insertAt(null, a);
    assertAllChildren(root1, 1);
    
    
    //
    root1.insertAt(0, a);
    assertAllChildren(root1, 1);
  }
  
  public void testInsertMove3() {
    IntegerTree a = tree("a", 1);
    IntegerTree root1 = tree("root", 0, a);
    IntegerTree root2 = tree("root", 0);
    //first a is child of root1.
    
    assertAllChildren(root1, 1);
    
    //move a from root1 to root2
    root2.insertAt(0, a);
    
    assertAllChildren(root2, 1);
    assertSame(root2, a.getParent());
    
  }
  
  public void testInsertReorder() {
    //
    {
      IntegerTree a = tree("a", 1);
      IntegerTree root1 = tree("root", 0, a);
      //
      root1.insertAt(0, a);
      assertAllChildren(root1, 1);
      assertSame(root1, a.getParent());
      
      root1.insertAt(1, a);
      assertSame(root1, a.getParent());
      
    }
    
  }
  
  
  public void testListIteratorRemove() {
    IntegerTree root = tree("",
                            0,
                            tree("1", 1),
                            tree("2", 2),
                            tree("3", 3),
                            tree("4", 4),
                            tree("5", 5));
    
    ListIterator<IntegerTree> childrenIt = root.listIterator();
    assertEquals(1, childrenIt.next().value);
    assertEquals(2, childrenIt.next().value);
    assertAllChildren(root, 1, 2, 3, 4, 5);
    
    //current pointer to 2 position.
    //remove element which is at 2 position.
    childrenIt.remove();
    assertAllChildren(root, 1, 3, 4, 5);
    assertEquals(3, childrenIt.next().value);
    assertEquals(3, childrenIt.previous().value);
    assertEquals(1, childrenIt.previous().value);
    //remove element which is at 1 position.
    childrenIt.remove();
    assertAllChildren(root, 3, 4, 5);
    assertTrue(!childrenIt.hasPrevious());
    assertEquals(3, childrenIt.next().value);
    childrenIt.remove();
    assertAllChildren(root, 4, 5);
    try {
      childrenIt.remove();
      fail();
    } catch (IllegalStateException e) {
      // expected
    }
    assertEquals(4, childrenIt.next().value);
    assertEquals(5, childrenIt.next().value);
    childrenIt.remove();
    assertAllChildren(root, 4);
    assertEquals(4, childrenIt.previous().value);
    childrenIt.remove();
    assertAllChildren(root);
  }
}
