package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root=null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}

	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
public void build() {    //////DONEDONEDONE
	
	root = builder();	
	
}

	private TagNode builder()
	{
		
		String tag = null;
		
		
		//checks if there are stills lines left in the input
		boolean lineCheck = sc.hasNextLine();
		
		
		if ( lineCheck == true)
		{
			tag = sc.nextLine();
		}
		else
		{
			return null;
		}
		
		boolean holder = false; 
		
		
		//checks if tag is a closing tag
		if (tag.charAt(0) == '<')
		{
			tag = tag.substring(1,tag.indexOf('>'));
			
			if (tag.charAt(0) == '/')
			{
				return null;
			}
			else
			{
				holder = true;
			}
		}
		
		TagNode tmp = new TagNode (tag, null, null);
		if (holder == true)
		{
			tmp.firstChild = builder();
					
			
		}
		tmp.sibling = builder();
		return tmp;
		
	}

	
	
	
	

	
	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * ex: Transforms the tree by replacing all occurrences of a tag with
	 *  another (e.g. replace b with em, or ol with ul). 
	 *  
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */



	public void replaceTag(String oldTag, String newTag) {         /////DONEDONEDONE
		root = replaceTag2(root, oldTag, newTag);
	}
	
	private TagNode replaceTag2(TagNode temp, String oldTag2, String newTag2) {
		
		
		TagNode curr = temp;
		
		//checks if node is null
		if (curr == null)
		{
			return null;
		}
		

		//checks for match
		if (curr.tag.equals(oldTag2)) {
			curr.tag = newTag2;
		}
		
		
		//recursive traversal
		curr.firstChild = replaceTag2(temp.firstChild, oldTag2, newTag2);
		curr.sibling = replaceTag2(temp.sibling, oldTag2, newTag2);
		
		root = curr;
		return root;
}


	


	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {   ////DONEDONEDONE
		boldRow2(root, row);
		
	}
	
	
	private TagNode boldRow2(TagNode tag, int row)
	{
		
		if (tag == null)
		{
			return null; 
		}
		
		tag.firstChild = boldRow2(tag.firstChild, row);
		if (tag.tag.equals("table"))
		{
			TagNode child = tag.firstChild;
			for ( int i =1; i<row; i++)
			{
				child = child.sibling;
			}
			TagNode col = child.firstChild;
			while ( col != null)
			{
				TagNode tmp = new TagNode("b", col.firstChild, null);
				col.firstChild=tmp;
				col = col.sibling;
			}
				
		}
		tag.sibling = boldRow2(tag.sibling, row);
		return tag;
	}


	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 *  
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
public void removeTag(String tag) {////////////////////DONEDONEDONE
		
		root= removeTag2(root, tag);
		
	}
	
private TagNode removeTag2(TagNode tag, String tmp){ ///done
		if (tag == null)
		{
			return null;
		}
		String tmp2 = tag.tag;
		tag.firstChild = removeTag2(tag.firstChild,tmp);
		
		
		
		if ( (tmp2.equals("p") && tmp2.contentEquals(tmp)) || (tmp2.equals("em") && tmp2.equals(tmp)) || (tmp2.equals("b") && tmp2.equals(tmp)))
				{ 
					findLast(tag.firstChild).sibling = tag.sibling;
					tag = tag.firstChild;
				}
		else if ((tmp2.equals("ol") && tmp2.equals(tmp)) || (tmp2.equals("ul") && tmp2.equals(tmp)))
		{
			TagNode child = tag.firstChild;
			while(child!= null)
			{
				if(child.tag.equals("li")) {
					child.tag = "p";
				}
				child = child.sibling;
			}
			child = tag.firstChild;
			findLast(child).sibling = tag.sibling;
			tag = tag.firstChild;
		}
		tag.sibling = removeTag2(tag.sibling,tmp);
		return tag;
}

	


	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	public void addTag(String word, String tag) {
		/** COMPLETE THIS METHOD **/
		
		//Stack for tokens
		ArrayList<String> toks = new ArrayList<String>();
		
		toks.add(".");
		toks.add("?");
		toks.add(":");
		toks.add(":");
		toks.add(",");
		toks.add("!");
		root = addTag2(root,word, tag, toks);
		
	}
	private TagNode addTag2(TagNode x, String word, String tag, ArrayList<String> a){
		
		
		//base case
			if (x == null)
			{
				return null;
			}
		
		
		//runs through first child	
			x.firstChild = this.addTag2(x.firstChild, word, tag, a);
		
		//runs through sibling 
		
			x.sibling = this.addTag2(x.sibling, word, tag, a);
		
			//records current tag
		
			String tmp = "";
			String m = "";
			String word2 = x.tag+" ";
		    TagNode newTag = null;
		
		    
		    while (word2.length() != 0)
		    {
			
		    	
		    	int index = word2.indexOf(" ");
		    	tmp = word2.substring(0, index).toLowerCase();
			
		    	//checks if word exists
		    	if (tmp.contains(word) == false) 
		    		{
		    		m += word2.substring(0,index) + " ";
		    		}
		    	
		    	// adds tag to desired location
		    	//recursively calls itself
		    	if (tmp.contains(word) && tmp.length() <= word.length()+1)
		    		{
		    		if(tmp.length()==word.length()+1 && a.contains(tmp.substring(tmp.length()-1)))
		    			{
		    				if(m.length()!=0) 
		    				{
		    					newTag=this.pushLast(newTag, m);
		    				}
		    				newTag = pushLast(newTag, tag);
					
		    				findLast(newTag).firstChild= new TagNode(word2.substring(0, index),null,null);
		    				m = "";
		    			}
				
		    		else if ( tmp.length() == word.length())
		    		{
					
		    			if(m.length() != 0)
		    			{
						newTag= pushLast(newTag, m);
		    			}
					
		    			newTag = pushLast(newTag, tag);
		    			findLast(newTag).firstChild = new TagNode(word2.substring(0, index),null,null);
		    			m = "";
		    		}
		    	}
			
		    	word2 = word2.substring(index+1);
			
		    }
		
		    if (newTag == null) 
		    {
			return x;
		    }
		    
		findLast(newTag).sibling=x.sibling;
		x = newTag;
		
		return x;
			
		
}//finds last tag then returns it to above method
	private TagNode findLast(TagNode tag){
		
		if(tag == null)
			return tag;
		
		while(tag.sibling!=null)
			tag = tag.sibling;
		return tag;
	}
	
	
	//adds last to above method
	private TagNode pushLast(TagNode node, String tag){
		if(node == null)
		{
			return new TagNode(tag,null,null);			
		}
		
		TagNode temp= node;
		
		while(temp.sibling!=null)
		{
			temp=temp.sibling;
		}
		
		temp.sibling=new TagNode(tag,null,null);
		
		return node;
}
	
	
	
	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|----");
			} else {
				System.out.print("     ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}
