package interviews;

import java.util.ArrayList;
import java.util.List;

public class Text_Justification {
	/**
	 * Given an array of words and a length L, format the text such that each
	 * line has exactly L characters and is fully (left and right) justified.
	 * 
	 * You should pack your words in a greedy approach; that is, pack as many
	 * words as you can in each line. Pad extra spaces ' ' when necessary so
	 * that each line has exactly L characters.
	 * 
	 * Extra spaces between words should be distributed as evenly as possible.
	 * If the number of spaces on a line do not divide evenly between words, the
	 * empty slots on the left will be assigned more spaces than the slots on
	 * the right.
	 * 
	 * For the last line of text, it should be left justified and no extra space
	 * is inserted between words.
	 * 
	 * For example, words: ["This", "is", "an", "example", "of", "text", "justification."] L: 16.
	 * 
	 * Return the formatted lines as: 
	 * [ "This    is    an", 
	 * 	 "example  of text",
	 *   "justification.  " ] 
	 *   
	 *   Note: Each word is guaranteed not to exceed L in length.
	 *   
	 *   Corner case: A line other than the last line might contain only one word. What should you do in this case?
	 *   In this case, that line should be left-justified.
	 *   
	 * @analysis save each row into a list, and then convert each row to a line in the text
	 */
	public ArrayList<String> fullJustify(String[] words, int L) {
		ArrayList<String> res = new ArrayList<String>();
		if (words == null || words.length == 0 || L == 0) {
			res.add("");
			return res;
		}
		//save each satisfiable list for each line 
		ArrayList<ArrayList<String>> buffer = new ArrayList<ArrayList<String>>();
		buffer.add(new ArrayList<String>());
		int count = 0;
		for (int i = 0; i < words.length; i++) {
			int curLen = words[i].length();
			ArrayList<String> curList = buffer.get(buffer.size() - 1);
			// the current line can still hold
			if (count == 0 || (count + 1 + curLen <= L)) {
				curList.add(words[i]);
				if (count == 0)
					count += curLen;
				else
					count += (1 + curLen);
			} else {
				// open a new list to store the next line
				ArrayList<String> list = new ArrayList<String>();
				buffer.add(list);
				list.add(words[i]);
				count = curLen;
			}
		}
		// convert the lists into strings
		for (int index = 0; index < buffer.size(); index++) {
			ArrayList<String> list = buffer.get(index);
			StringBuffer sb = new StringBuffer();
			int wordCount = list.size();
			// deal with the only one word scenario
			if (wordCount <= 1) {
				sb.append(list.get(0));
				while (sb.length() < L)
					sb.append(" ");
				// the last line scenario
			} else if (index == buffer.size() - 1) {
				for (int i = 0; i < list.size(); i++) {
					sb.append(list.get(i));
					if (sb.length() < L)
						sb.append(" ");
				}
				while (sb.length() < L)
					sb.append(" ");
				// normal situation
			} else {
				int len = 0;
				for (int i = 0; i < wordCount; i++) {
					len += (list.get(i).length());
				}
				// find the number of spaces we should allocate for 
				int spaces = (L - len) / (wordCount - 1);
				// find if we need to allocate more whitespace to the left strings
				int lefts = (L - len) % (wordCount - 1);
				for (int i = 0; i < wordCount; i++) {
					sb.append(list.get(i));
					if (i < wordCount - 1) {
						for (int j = 0; j < spaces; j++)
							sb.append(" ");
						if (i < lefts) {
							sb.append(" ");
						}
					}
				}
			}
			res.add(sb.toString());
		}
		return res;
	}
	
	
	// without the list<list<String>> buffer
	public List<String> fullJustify2(String[] words, int maxWidth) {
        ArrayList<String> res = new ArrayList<String>();
		if (words == null || words.length == 0 || maxWidth == 0) {
			res.add("");
			return res;
		}
		//save each satisfiable list for each line 
		ArrayList<String> curList = new ArrayList<>();
		int count = 0;
		int wordLen = 0;
		for (int i = 0; i <= words.length; i++) {
			// the current line can still hold
			if (i < words.length && (count == 0 || (count + 1 + words[i].length() <= maxWidth))) {
				int curLen = words[i].length();
				curList.add(words[i]);
				wordLen += curLen;
				if (count == 0)
					count += curLen;
				else
					count += (1 + curLen);
			} else {
			    res.add(addList(res, curList, wordLen, maxWidth, i==words.length));
			    
				// open a new list to store the next line
				if (i < words.length) {
				    curList = new ArrayList<String>();
	    			curList.add(words[i]);
    				count = words[i].length();
    				wordLen = count;
				}
			}
		}
		return res;
    }
    public String addList(List<String> res, List<String> curList, int wordLen, int maxWidth, boolean isLastLine) {
        StringBuilder sb = new StringBuilder();
        int wordCount = curList.size();
	    if (wordCount <= 1) {
		    sb.append(curList.get(0));
		    while (sb.length() < maxWidth)
			    sb.append(" ");
	    } else if (isLastLine) { // the last line scenario
	        for (int j = 0; j < curList.size(); j++) {
  				sb.append(curList.get(j));
    			if (sb.length() < maxWidth)
			    	sb.append(" ");
		    }
		    while (sb.length() < maxWidth) sb.append(" ");
	    } else {
		    // find the number of spaces we should allocate for 
		    int spaces = (maxWidth - wordLen) / (wordCount - 1);
		    // find if we need to allocate more whitespace to the left strings
		    int lefts = (maxWidth - wordLen) % (wordCount - 1);
		    for (int j = 0; j < wordCount; j++) {
			    sb.append(curList.get(j));
			    if (j < wordCount - 1) {
				    for (int k = 0; k < spaces; k++)
					    sb.append(" ");
				    if (j < lefts) {
				    	sb.append(" ");
				    }
			    }
		    }
	    }
	    return sb.toString();
    }
}
