// Hussein's Binary Tree
// 26 March 2017
// Hussein Suleman
/**
 * A class that creates a node of the Generic.
 * A binary search tree node.
 */
public class BinaryTreeNode
{
    Generic generic;
    BinaryTreeNode left;
    BinaryTreeNode right;
    int height;

   /**
    * Constructor to create node.
    * @param g the Generic object to initialise a node, l and r to assign left and right nodes.
    */
   public BinaryTreeNode ( Generic g, BinaryTreeNode l, BinaryTreeNode r )
   {
      generic = g;
      left = l;
      right = r;
      height = 0;
   }

   /**
    * to get the left node.
    * @return the left node.
    */
   BinaryTreeNode getLeft () { return left; }

   /**
    * to get the right node.
    * @return the right node.
    */
   BinaryTreeNode getRight () { return right; }
}
