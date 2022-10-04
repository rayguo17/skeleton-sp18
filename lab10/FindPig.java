public class FindPig implements Action<String>{
    boolean found = false;
    @Override
    public void visit(Tree<String> T) {
        if("pig".equals(T.val)){
            found = true;
        }
    }
}
