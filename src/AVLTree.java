// Hussein's AVL Tree
// 2 April 2017
// Hussein Suleman & Bonani Tshwane
// reference: kukuruku.co/post/avl-trees/
/**
 * An AVLTree class that extends BinaryTree class.
 */
public class AVLTree extends BinaryTree
{
   private int opCountInsert; /**The insert counter*/
   private int opCountSearch; /**The search counter*/

   /**
    * Constructor.
    * initialise the counters to 0.
    */
   public AVLTree() {
      opCountInsert = 0;
      opCountSearch = 0;
   }
   public int height ( BinaryTreeNode node )
   {
      if (node != null)
         return node.height;
      return -1;
   }
   
   public int balanceFactor ( BinaryTreeNode node )
   {
      return height (node.right) - height (node.left);
   }
   
   public void fixHeight ( BinaryTreeNode node )
   {
      node.height = Math.max (height (node.left), height (node.right)) + 1;
   }
   
   public BinaryTreeNode rotateRight ( BinaryTreeNode p )
   {
      BinaryTreeNode q = p.left;
      p.left = q.right;
      q.right = p;
      fixHeight (p);
      fixHeight (q);
      return q;
   }

   public BinaryTreeNode rotateLeft ( BinaryTreeNode q )
   {
      BinaryTreeNode p = q.right;
      q.right = p.left;
      p.left = q;
      fixHeight (q);
      fixHeight (p);
      return p;
   }
   
   public BinaryTreeNode balance ( BinaryTreeNode p )
   {
      fixHeight (p);
      if (balanceFactor (p) == 2)
      {
         if (balanceFactor (p.right) < 0)
            p.right = rotateRight (p.right);
         return rotateLeft (p);
      }
      if (balanceFactor (p) == -2)
      {
         if (balanceFactor (p.left) > 0)
            p.left = rotateLeft (p.left);
         return rotateRight (p);
      }
      return p;
   }

   /**
    * A new Generic object is inserted into avl tree.
    * @param generic the Generic object to inserted.
    */
   public void insert ( Generic generic )
   {
      root = insert (generic, root);
   }

   /**
    * A Generic object is inserted in the avl tree, beginning at the given node.
    * @param node the node to begin inserting at.
    * @param generic the Generic object to insert.
    * @return the balanced tree by balancing the nodes when needed.
    */
   public BinaryTreeNode insert ( Generic generic, BinaryTreeNode node ) {
      if (node == null) {
         return new BinaryTreeNode(generic, null, null);
      }
      opCountInsert++;
      if (generic.getTerm().compareTo(node.generic.getTerm()) <= 0) {
         node.left = insert(generic, node.left);
      }
      else {
         node.right = insert(generic, node.right);
      }
      return balance(node);
   }

   /**
    * Searches for a Generic object(term) in the avl tree
    * @param term the term of the Generic object to search for
    * @return the generic with the given term
    */
   public Generic find ( String term )
   {
      return find (term, root);
   }

   /**
    * Searches for a node with the given term in the avl tree that has a root at the given node.
    * @param node the root of the avl tree to search
    * @param term the term of the generic to search for
    * @return null if a node does not exist, else the node that has the generic with the given term
    */
   public Generic find ( String term, BinaryTreeNode node )
   {
      if (node == null) {
         return null;
      }
      opCountSearch++;
      if (term.compareTo (node.generic.getTerm()) == 0) {
         return node.generic;
      }
      else if (term.compareTo (node.generic.getTerm()) < 0) {
         return (node.left == null) ? null : find(term, node.left);
      }
      else {
         return (node.right == null) ? null : find(term, node.right);
      }
   }

   /** Returns number of insert operations.
    * @return the number of insert operations.
    */
   public int getOpCountInsert() {
      return opCountInsert;
   }

   /** Returns the number of search operations.
    * @return the number of search operations.
    */
   public int getOpCountSearch() {
      return opCountSearch;
   }
}

