package spelling;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author 
 *
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    
    public AutoCompleteDictionaryTrie()
	{
		this.root = new TrieNode();
		this.size=0;
	}
		
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		
		String wordAdd = word.toLowerCase();
		TrieNode current = this.root;
		
		if (isWord(word)){
			return false;
		}
		
		String text = "";
		
		for (int i = 0; i< wordAdd.length(); i++){
			
			current.insert(wordAdd.charAt(i));
			current = current.getChild(wordAdd.charAt(i));
			text = text + wordAdd.charAt(i);
			current.setText(text);
			
			// end of the word
			if (i==wordAdd.length()-1 && !current.endsWord()){
				current.setEndsWord(true);
				this.size +=1;
				return true;
			}
		}
	    return false;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return this.size;
	}
	
	/** find and return specific TrieNode that exists in trie 
	 * add by me
	 * */	
	public TrieNode getNode(TrieNode node) 
	{
		String text = node.getText();
		TrieNode current = this.root;
		
		for (int i = 0; i< text.length(); i++){
			
			current = current.getChild(text.charAt(i));		
			
			if (current == null){
				break;
			}		
			// end of the word
			if (i==text.length()-1){
				return current;		
			}
		}
		return current;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		String wordAdd = s.toLowerCase();
		TrieNode current = this.root;
		
		for (int i = 0; i< wordAdd.length(); i++){
			
			if (current.getChild(wordAdd.charAt(i))==null){
				return false;
			}else{
				current = current.getChild(wordAdd.charAt(i));	
			}
		
			// end of the word
			if (i==wordAdd.length()-1){
				return current.endsWord();		
			}
		}
	    return false;
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 
    	 List<String>result = new ArrayList<String>();
    	 result = this.Bfs_trie(new TrieNode(prefix));
    	 
    	 if (result.size()<1){
    		 return result;
    	 }
    	 
    	 if (result.size() > numCompletions){
    		 return result.subList(0, numCompletions);
    	 }
    	  	 
    	 return result;
    	 
     }
     
     public List<String> Bfs_trie(TrieNode node){
    	 
    	 List<String>result = new ArrayList<String>();
    	 
    	 TrieNode current = getNode(node);    
    	 
    	 if (current==null){
    		 return result;
    	 }
    	 	 
    	 // list of TrieNodes
    	 List<TrieNode>queue = new LinkedList<TrieNode>();
    	 queue.add(current);
    		    	 
    	 while(!queue.isEmpty()){
			 current = queue.get(0);

	    	 for (char i : current.getValidNextCharacters()){
	    		 
	    		 // add all valid characters to queue
	    		 queue.add(current.getChild(i));    		 
	    	 }
	    	
	    	 // if stem is a word, add word to result
	    	 if (current.endsWord()){
				 result.add(current.getText());
			 }
			 queue.remove(0);
    	} 	    	   	 
    	 return result;    	 
     }
     

     
 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	
 	public static void main(String[] args) {
 		AutoCompleteDictionaryTrie test = new AutoCompleteDictionaryTrie();
 		test.addWord("pandora");
 		test.addWord("panama");
 		test.addWord("panda");
 		
 		test.addWord("pen");
 		test.addWord("peninsula");
 		test.addWord("pensive");
 		test.addWord("pendant");
 		test.addWord("penicillin");
 		
 		//test.addWord("st");
 		test.addWord("step");
 		test.addWord("stem");
 		test.addWord("stew");
 		test.addWord("steer");
 		test.addWord("steep");
 		
 		System.out.println(test.predictCompletions("x", 4).size());
 		System.out.println(test.predictCompletions("x", 4));
		//System.out.println(test.size()); 
		//test.printTree();
 	}
	
}