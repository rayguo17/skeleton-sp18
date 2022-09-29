public class Tree<T> {
    public Tree left;
    public Tree right;
    public T val;
    public static void preOrderTraverse(Tree node,Action whatToDo){
        if(node==null){
            return;
        }
        whatToDo.visit(node);
        preOrderTraverse(node.left,whatToDo);
        preOrderTraverse(node.right,whatToDo);
    }
    public int height(){

    }
    public static void levelOrder(Tree node,Action toDo){
        for(int i=0;i<node.height();i+=1){
            
        }
    }

    public static void main(String[] args) {
        Tree<String> test = new Tree<>();
        //use find pig to store data traversed in tree.
        test.preOrderTraverse(test,new FindPig());
    }
}
