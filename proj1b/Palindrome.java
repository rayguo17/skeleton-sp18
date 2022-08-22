public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> res = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            res.addLast(word.charAt(i));
        }

        return res;

    }
    public boolean isPalindrome(String word){
        Deque<Character> que = wordToDeque(word);
        while (!que.isEmpty()){
            Character left = que.removeFirst();
            Character right = que.removeLast();
            if(left ==null || right==null){
                break;
            }
            if (left != right){
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word,CharacterComparator cc){
        Deque<Character> que = wordToDeque(word);
        while(!que.isEmpty()){
            Character left = que.removeFirst();
            Character right = que.removeLast();
            if(left == null || right == null){
                break;
            }
            if (!cc.equalChars(left,right)){
                return false;
            }
        }
        return true;
    }
}
