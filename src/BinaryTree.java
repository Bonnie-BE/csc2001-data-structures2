
// Hussein's Binary Tree
// 26 March 2017
// Hussein Suleman

public class BinaryTree {

   /**
    * A binary tree root node.
    */
   public BinaryTreeNode root;

   /**
    * Creates an empty binary tree.
    */
   public BinaryTree() {
      root = null;
   }

   public int getHeight() {
      return getHeight(root);
   }

   public int getHeight(BinaryTreeNode node) {
      if (node == null)
         return -1;
      else
         return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
   }

   public int getSize() {
      return getSize(root);
   }

   public int getSize(BinaryTreeNode node) {
      if (node == null)
         return 0;
      else
         return 1 + getSize(node.getLeft()) + getSize(node.getRight());
   }

   public void visit(BinaryTreeNode node) {
      System.out.println(node.generic.getTerm());
   }
}