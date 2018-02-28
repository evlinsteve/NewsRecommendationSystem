package com.java.pack;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class RankedSearch {
	String[] myDocs;
	ArrayList<String> termList;
	ArrayList<ArrayList<Doc>> docLists;
	double[] docLength;
	File[] listOfFiles;
	ArrayList<String> StopWord;
	HashMap<Integer,String> Index;
	String [] FileNames;
	
	/**
	 * Construct an inverted index using tf-idf weighting 
	 * @param docs List of input strings or file names
	 * @throws FileNotFoundException 
	 * 
	 */
	public RankedSearch() throws FileNotFoundException
	{
		//myDocs = docs;
		termList = new ArrayList<String>();
		docLists = new ArrayList<ArrayList<Doc>>();
		ArrayList<Doc> docList;
		StopWord = new ArrayList<String>();
		
		Index = new HashMap<Integer,String>();
		
		File file;
		//Extract all the file from the given directory
	    File folder = new File("/Users/sushma/Desktop/WorkJava/Recomender/src/data/Search");
	    listOfFiles = folder.listFiles();
	    
	    Scanner sc1 = new Scanner(new File ("/Users/sushma/Desktop/WorkJava/Recomender/src/data/stopwords.txt"));
 	   while (sc1.hasNextLine()) 
 	   {
 		   String j = sc1.nextLine();
 		    StopWord.add(j);
 	   }	
	    
    	  
	       for (int i = 0; i < listOfFiles.length; i++) {

	       if (listOfFiles[i].isFile())  {
	    	   
	    	   file = listOfFiles[i];
	    	   Scanner sc = new Scanner(file);
	    	//   System.out.println(i+ "   " + file.getName());
	    	   Index.put(i,file.getName());
	    	   while (sc.hasNextLine()) 
	    	   {
	    		   String j1 = sc.nextLine();
	               String delims = "[ ?.&%$#!/+-/();:\"]+";
	               String[] tokens = j1.split(delims);
	               String token;
			    for(int j=0;j<tokens.length;j++){
				token = tokens[j].toLowerCase();
				if(!StopWord.contains(token))
         	   {
         		   //Stemming process
         		   Porter p1 = new Porter();
         		 
         		   if(token.equals(""))
         			   token = " ";
         		//   token = p1.stripAffixes(token);

				
				if(!termList.contains(token))
				{
					termList.add(token);
					docList = new ArrayList<Doc>();
					Doc doc = new Doc(i,1); //initial raw frequency is 1
					docList.add(doc);
					docLists.add(docList);
				}
				else
				{
					int index = termList.indexOf(token);
					docList = docLists.get(index);
					int k=0;
					boolean match = false;
					
					//search the postings list for a document id, if match, insert a new position number to the document id
					for(Doc doc:docList)
					{
						if (doc.docId == i)
						{
							doc.tw++; //increase word count
							match = true;
							break;
						}
						k++;
					}
					//if no match, add a new document id along with the position number
					if(!match)
					{
						Doc doc = new Doc(i,1); 
						docList.add(doc);
					}
				}
			}
	    	   }
			    
	    	   }  	   
	    	   sc.close();   
	            }
	      
		}//end with parsing
		
		//LBE07: compute the tf-idf term weights and the doc lengths 
		int N = 1001;
		docLength = new double[N];
		for(int i=0;i<termList.size();i++){
			docList = docLists.get(i);
			int df = docList.size();
			Doc doc;
			for(int j=0;j<docList.size();j++){
				doc = docList.get(j);
				double tfidf = (1+Math.log(doc.tw))*Math.log(N/(df*1.0));
				docLength[doc.docId] += Math.pow(tfidf, 2);
				doc.tw = tfidf;
				docList.set(j, doc);
			}
		}
		//update the length
		for(int i=0;i<N;i++){
			docLength[i] = Math.sqrt(docLength[i]);
		}
			
	}

	
	public void rankSearch(String[] query){
		
		HashMap<Integer, Double> docs = new HashMap<Integer, Double>();

		ArrayList<Doc> docList;
		for(String term: query){
			int index = termList.indexOf(term);
			if(index<0)
				continue;
			docList = docLists.get(index);
			
			
			double w_t = Math.log(1001*1.0/docList.size());
			Doc doc;
			for(int j=0;j<docList.size();j++){
				doc = docList.get(j);
				double score = w_t * doc.tw;
				if(!docs.containsKey(doc.docId)){
					docs.put(doc.docId, score);
				}
				else{
					score += docs.get(doc.docId);
					docs.put(doc.docId, score);
				}
			}
		}
	     ArrayList<String> str = new ArrayList<String>();
		 docs.entrySet().stream()
		   .sorted(Map.Entry.comparingByValue())
		   .forEach(item->{
				  str.add(Index.get(item.getKey()));
				//	System.out.println(Index.get(item.getKey()));
				
			});
        int m = 1;
        if(str.size()!=0)
        {
        FileNames = new String[10];
        int k=0;
		System.out.println("Documents in ranked order:");
		for(int i= str.size()-1; i>str.size()-11 && i>0;i-- )
		{
			System.out.println("Rank:" + m++ + "  "+ str.get(i));
			FileNames[k++] = str.get(i);
		}
        }
			
	}
	
	public String[] getFileName() throws FileNotFoundException
	{
		String[] Filecontent =null;
		if(FileNames!= null)
		{
		Filecontent = new String[FileNames.length];
		String j = "";
		for(int i=0;i<FileNames.length-1;i++)
		{
			if(FileNames[i]!= null)
			{
		Scanner sc1 = new Scanner(new File ("/Users/sushma/Desktop/WorkJava/Recomender/src/data/Search/"+FileNames[i]));
		 while (sc1.hasNextLine()) 
	 	   {
	 		   j = j+ " "+sc1.nextLine();
	 		    
	 	   }
		 Filecontent[i] = j;
		 j="";
		}
		}
		}
		  return Filecontent;
	}
	/**
	 * Return the string representation of the index
	 */
	public String toString()
	{
		String matrixString = new String();
		ArrayList<Doc> docList;
		for(int i=0;i<termList.size();i++){
				matrixString += String.format("%-15s", termList.get(i));
				docList = docLists.get(i);
				for(int j=0;j<docList.size();j++)
				{
					matrixString += docList.get(j)+ "\t";
				}
				matrixString += "\n";
			}
		return matrixString;
	}
	
}
class Doc{
	int docId;
	double tw;
	public Doc(int did, double weight)
	{
		docId = did;
		tw = weight;
	}
	public String toString()
	{
		String docIdString = docId + ":" + tw;
		return docIdString;		
	}
}
