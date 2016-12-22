package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	private static final int NOT_IN_LIST = -1;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		String [] words  = sourceText.split("\\s+");
		this.starter = words[0];

		ListNode preWord = new ListNode(this.starter);
		
		
		for(int i=1; i <= words.length; i++){

			// check if "preWord" is already a node in the list
			int word_in_list = findWordList(preWord.getWord(), wordList);
			
			// if preWord not in wordList
			if(word_in_list==NOT_IN_LIST){
				if(i < words.length){
					// add word[i] to NextWord
					preWord.addNextWord(words[i]);
					wordList.add(preWord);
				}else{
					// last word, add starter to NextWord
					preWord.addNextWord(this.starter);
					wordList.add(preWord);
				}		
				
			// if preWord is in wordList	
			}else{
				if(i < words.length){
					// add word[i] to NextWord
					this.wordList.get(word_in_list).addNextWord(words[i]);				
				}else{
					// last word, add starter to NextWord
					this.wordList.get(word_in_list).addNextWord(this.starter);
				}
			}
			
			if(i < words.length){
				// set words[i] as the next preWord
				preWord = new ListNode(words[i]);
			}
			
		}

	}
	
	// determine if wordList contain a node with String "a", if so return index
	
	public int findWordList(String a, List<ListNode>wordList){
		
		for (int i=0; i < wordList.size(); i++){
			if (wordList.get(i).getWord().equals(a)){
				return i;			
			}
		}			
		return NOT_IN_LIST;
	}
	
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    
		StringBuilder output = new StringBuilder(this.starter);
		String currWord = this.starter;
		
		int count = 1;
		
		while(count <= numWords){
			int word_in_list = findWordList(currWord, this.wordList);
			currWord = this.wordList.get(word_in_list).getRandomNextWord(this.rnGenerator);
			output.append(" "+currWord);
			count +=1;
		}
				
		if (output.charAt(output.length()-1)!='.'){
			output.append(".");
		}
					
		return output.toString();
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		this.wordList = new LinkedList<ListNode>();
		this.starter = "";
		this.train(sourceText);
	}

	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		return nextWords.get(generator.nextInt(nextWords.size()));
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


