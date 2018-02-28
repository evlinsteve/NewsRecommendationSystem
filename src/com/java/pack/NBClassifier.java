package com.java.pack;
import java.io.*;
import java.util.*;
public class NBClassifier {
	String[] MyDocs;
	String[] mydocs1;
	String[] mydocs2;
	String[] mydocs3;
	String[] mydocs4;
	String[] mydocs5;
	String[] MytestDocs;
	String[] testdocpositive;
	String[] testdocnegative;
	ArrayList<String> documents;
	ArrayList<String> business;
    ArrayList<String> entertaintment;
	ArrayList<String> politics;
	ArrayList<String> sports;
	ArrayList<String> tech;
	String[] businesstest;
	String[] entertaintmenttest;	
	String[] politicstest;
	String[] sportstest;
	String[] techtest;
	int[] trainLabels;
	String[] trainingdocuments;
	String[] testingdocuments;
	String[] stopList;
	int numClasses;
	int[] classCounts; 
	String[] classStrings; 
	int[] classTokenCounts; 
	HashMap<String,Double>[] condProb;
	HashSet<String> vocabulary; 

	
	public NBClassifier(String trainDataFolder)
	{   
		documents=new ArrayList<String>();
		File folder=new File (trainDataFolder);
		File[] listoffiles= folder.listFiles();
		MyDocs= new String[listoffiles.length];
		for(int i=0;i<listoffiles.length;i++)
		{
			MyDocs[i] = listoffiles[i].getName();
		}
		
		File folder1=new File (trainDataFolder+"/"+MyDocs[0]);
		File[] listoffiles1= folder1.listFiles();
		File folder2=new File (trainDataFolder+"/"+MyDocs[1]);
		File[] listoffiles2= folder2.listFiles();
		File folder3=new File (trainDataFolder+"/"+MyDocs[2]);
		File[] listoffiles3= folder3.listFiles();
		File folder4=new File (trainDataFolder+"/"+MyDocs[3]);
		File[] listoffiles4= folder4.listFiles();
		File folder5=new File (trainDataFolder+"/"+MyDocs[4]);
		File[] listoffiles5= folder5.listFiles();
		
		mydocs1= new String[listoffiles1.length];
		mydocs2= new String[listoffiles2.length];
		
		mydocs1= new String[listoffiles1.length];
		mydocs2= new String[listoffiles2.length];
		
		mydocs3= new String[listoffiles3.length];
		mydocs4= new String[listoffiles4.length];
		mydocs5= new String[listoffiles5.length];
		
		
		for(int i=0;i<listoffiles1.length;i++)
		{
			mydocs1[i] = listoffiles1[i].getName();
		}
		for(int i=0;i<listoffiles2.length;i++)
		{
			mydocs2[i] = listoffiles2[i].getName();
		}
		for(int i=0;i<listoffiles3.length;i++)
		{
			mydocs3[i] = listoffiles3[i].getName();
		}
		for(int i=0;i<listoffiles4.length;i++)
		{
			mydocs4[i] = listoffiles4[i].getName();
		
		}
		for(int i=0;i<listoffiles5.length;i++)
		{
			mydocs5[i] = listoffiles5[i].getName();
		
		}
	
	
		String inter;
		for(int k=0;k<mydocs1.length;k++)
		{	
			 inter= (parse(trainDataFolder + "/" + MyDocs[0]+"/"+mydocs1[k]));
			
				documents.add(inter);
		}
		for(int k=0;k<mydocs2.length;k++)
		{	
		inter= (parse(trainDataFolder + "/" + MyDocs[1]+"/"+mydocs2[k]));
		documents.add(inter);	
		}
		for(int k=0;k<mydocs3.length;k++)
		{	
		inter= (parse(trainDataFolder + "/" + MyDocs[2]+"/"+mydocs3[k]));
		documents.add(inter);	
		}
		for(int k=0;k<mydocs4.length;k++)
		{	
		inter= (parse(trainDataFolder + "/" + MyDocs[3]+"/"+mydocs4[k]));
		documents.add(inter);	
		}
		for(int k=0;k<mydocs5.length;k++)
		{	
		inter= (parse(trainDataFolder + "/" + MyDocs[4]+"/"+mydocs5[k]));
		documents.add(inter);	
		}
		
		trainLabels= new int[listoffiles1.length+listoffiles2.length+listoffiles3.length+listoffiles4.length+listoffiles5.length];
		int m;
		for(m=0;m<listoffiles1.length;m++)
		{
			trainLabels[m]=0;
		}
		int n=m;
		for( int j=0;j<listoffiles2.length;j++)
		{
			trainLabels[n]=1;
			n++;
		}
		int p=n;
		for( int j=0;j<listoffiles3.length;j++)
		{
			trainLabels[p]=2;
			p++;
		}
		int q=p;
		for( int j=0;j<listoffiles4.length;j++)
		{
			trainLabels[q]=3;
			q++;
		}
		int r=q;
		for( int j=0;j<listoffiles5.length;j++)
		{
			trainLabels[r]=4;
			r++;
		}
		
		
		try
		{Scanner scan = new Scanner(new File("/Users/sushma/Desktop/WorkJava/Recomender/src/data/stopwords.txt"));
		List<String> lines = new ArrayList<String>();
		while (scan.hasNextLine()) {
		  lines.add(scan.nextLine());
		}
	    stopList = lines.toArray(new String[0]);
		Arrays.sort(stopList);}
		catch(IOException ioe)
		{
			
		}
		int k=0;
		trainingdocuments= new String[documents.size()];
		for(String s :documents)
		{  
				trainingdocuments[k]=s;
				k++;
				
		}

		numClasses = 5;
		classCounts = new int[numClasses];
		classStrings = new String[numClasses];
		classTokenCounts = new int[numClasses];
		condProb = new HashMap[numClasses];
		vocabulary = new HashSet<String>();
		for(int i=0;i<numClasses;i++){
			classStrings[i] = "";
			condProb[i] = new HashMap<String,Double>();
		}

		for(int i=0;i<trainLabels.length;i++){
			classCounts[trainLabels[i]]++;
			classStrings[trainLabels[i]] += (trainingdocuments[i] + " ");
		}
		ArrayList<String> tokens;
		for(int i=0;i<numClasses;i++){
			String[] tokens2 = classStrings[i].split(" ");
			classTokenCounts[i] = tokens2.length;

			tokens=new ArrayList<String>();
			for(String t :tokens2)
			{
				if(searchStopword(t)==-1) 
				{
				   //tokens.add(t);
				tokens.add(addtotermlist(t));
				}
				
			}

			for(String token:tokens){
				
					vocabulary.add(token);
				if(condProb[i].containsKey(token)){
					double count = condProb[i].get(token);
					condProb[i].put(token, count+1);
				}
				else
					condProb[i].put(token, 1.0);
			}
		}
		
		for(int i=0;i<numClasses;i++){
			Iterator<Map.Entry<String, Double>> iterator = condProb[i].entrySet().iterator();
			int vSize = vocabulary.size();
			while(iterator.hasNext())
			{
				Map.Entry<String, Double> entry = iterator.next();
				String token = entry.getKey();
				Double count = entry.getValue();
				count = (count+1)/(classTokenCounts[i]+vSize);
				condProb[i].put(token, count);
			}
		}
	}
	public String parse(String fileName)
	{
	   
		String[] token = null;
		String documentwords=" ";
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String allLines = new String();
			String line = null;
			while((line=reader.readLine())!=null){
				allLines += line.toLowerCase();
			}
		
			token = allLines.split("[ .,?!:;$%]+");
			for(String s:token)
			{
				documentwords=documentwords+" " +s;
			}		
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		return documentwords;
	}
	/**
	 * Classify a test doc
	 * @param doc test doc
	 * @return class label
	 */
	public int classify(String doc){
		
		int label = 0;
		int vSize = vocabulary.size();
		double[] score = new double[numClasses];
		for(int i=0;i<score.length;i++){
			score[i] = Math.log(classCounts[i]*1.0/trainingdocuments.length);
		}
		String[] tokens;
		
	    tokens = doc.split(" ");
		String token1;
		for(int i=0;i<numClasses;i++){
			for(String token: tokens){
			
				if(searchStopword(token)==-1)
				{
				//token1=token;
				token1=addtotermlist(token);
				if(condProb[i].containsKey(token1))
					score[i] += Math.log(condProb[i].get(token1));
				else
					score[i] += Math.log(1.0/(classTokenCounts[i]+vSize));
				}
				
			}
		}
		double maxScore = score[0];
		double secScore = score[0];
		double e;
		label=0;
		int label1=label;
		for(int i=0;i<score.length;i++){
			if(i==1){
				e=(double)((score[i]-maxScore)
						/score[i])*100;	
				if(e<0)
				{
					label1=label;
					secScore=maxScore ;
					label = i;
				    maxScore=score[i];
				    break;
				}
				}
			if(score[i]>=maxScore)
				{
				
				label1=label;
				secScore=maxScore ;
				label = i;
			    maxScore=score[i];}
		}
		double s= (double)(maxScore-secScore/maxScore)*100;
			 if((s<-170000)&&(label!=0)&&(label!=1)&&(label!=2))
			 {
				 label=label1;
			 }
			 if(label==0)
			 {
				 if(s<-104960)
				 {
					 label=label1;
				 } 
			 }
		 
		return label;
	}
	public int searchStopword(String key)
	{
		int lo = 0;
		int hi = stopList.length-1;
		while(lo<=hi)
		{
			int mid = lo + (hi-lo)/2;
			int result = key.compareTo(stopList[mid]);
			if(result <0) hi = mid - 1;
			else if(result >0) lo = mid+1;
			else return mid;
		}
		return -1;
	}

	public String addtotermlist(String key)
	{
		Stemmer st = new Stemmer();
		st.add(key.toCharArray(),key.length());
		st.stem();
		return st.toString();
	}

	
	public void classifyAll(String testDataFolder)
	{
		business=new ArrayList<String>();
		tech=new ArrayList<String>();
		sports=new ArrayList<String>();
		entertaintment=new ArrayList<String>();
		politics=new ArrayList<String>();
		File folder=new File (testDataFolder);
		File[] listoffiles= folder.listFiles();
		MytestDocs= new String[listoffiles.length];
		
		for(int i=0;i<listoffiles.length;i++)
		{
			MytestDocs[i] = listoffiles[i].getName();
			
		}
		
		File folder1=new File (testDataFolder+"/"+MytestDocs[0]);
		File[] listoffiles1= folder1.listFiles();
		File folder2=new File (testDataFolder+"/"+MytestDocs[1]);
		File[] listoffiles2= folder2.listFiles();
		File folder3=new File (testDataFolder+"/"+MytestDocs[2]);
		File[] listoffiles3= folder3.listFiles();
		File folder4=new File (testDataFolder+"/"+MytestDocs[3]);
		File[] listoffiles4= folder4.listFiles();
		File folder5=new File (testDataFolder+"/"+MytestDocs[4]);
		File[] listoffiles5= folder5.listFiles();
		mydocs1= new String[listoffiles1.length];
		mydocs2= new String[listoffiles2.length];
		mydocs3= new String[listoffiles3.length];
		mydocs4= new String[listoffiles4.length];
		mydocs5= new String[listoffiles5.length];
		for(int i=0;i<listoffiles1.length;i++)
		{
			mydocs1[i] = listoffiles1[i].getName();
		}
		for(int i=0;i<listoffiles2.length;i++)
		{
			mydocs2[i] = listoffiles2[i].getName();
			
		}
		for(int i=0;i<listoffiles3.length;i++)
		{
			mydocs3[i] = listoffiles3[i].getName();
		}
		for(int i=0;i<listoffiles4.length;i++)
		{
			mydocs4[i] = listoffiles4[i].getName();
			
		}
		for(int i=0;i<listoffiles5.length;i++)
		{
			mydocs5[i] = listoffiles5[i].getName();
			
		}
		
		String inter;
		for(int k=0;k<mydocs1.length;k++)
		{	
			 inter= (parse(testDataFolder + "/" + MytestDocs[0]+"/"+mydocs1[k]));
			
			 business.add(inter);
		
		}
		for(int k=0;k<mydocs2.length;k++)
		{	
		inter= (parse(testDataFolder + "/" + MytestDocs[1]+"/"+mydocs2[k]));
		entertaintment.add(inter);	
		}
	
		for(int k=0;k<mydocs3.length;k++)
		{	
		inter= (parse(testDataFolder + "/" + MytestDocs[2]+"/"+mydocs3[k]));
		politics.add(inter);	
		}
		
		for(int k=0;k<mydocs4.length;k++)
		{	
		inter= (parse(testDataFolder + "/" + MytestDocs[3]+"/"+mydocs4[k]));
		sports.add(inter);	
		}
		for(int k=0;k<mydocs5.length;k++)
		{	
		inter= (parse(testDataFolder + "/" + MytestDocs[4]+"/"+mydocs5[k]));
		tech.add(inter);	
		}
		
		int k=0;
		String stemmed;
		businesstest= new String[business.size()];
		for(String s :business)
		{   
				businesstest[k]=s;
				k++;
				
		}
		int l=0;
		entertaintmenttest= new String[entertaintment.size()];
		for(String s :entertaintment)
		{   
			entertaintmenttest[l]=s;
				l++;
		
		}
		int m=0;
		politicstest= new String[politics.size()];
		for(String s :politics)
		{   
				politicstest[m]=s;
				m++;
		
		}
		int n=0;
		sportstest= new String[sports.size()];
		for(String s :sports)
		{   
				sportstest[n]=s;
				n++;
		
		}
		
		int o=0;
		techtest= new String[tech.size()];
		for(String s :tech)
		{   
				techtest[o]=s;
				o++;
		
		}
		
		int errorcount=0;

		for(int i=0;i<businesstest.length;i++)
		{
			if(classify(businesstest[i])!=0)
			{
				errorcount++;	
			}
		}
		for(int i=0;i<entertaintmenttest.length;i++)
		{
			if(classify(entertaintmenttest[i])!=1)
			{
				errorcount++;
				
			}
		}
		for(int i=0;i<politicstest.length;i++)
		{
			if(classify(politicstest[i])!=2)
			{
				errorcount++;
				
			}
		}
		for(int i=0;i<sportstest.length;i++)
		{
			if(classify(sportstest[i])==3)
			{
				errorcount++;
				
			}
		}
		for(int i=0;i<techtest.length;i++)
		{
			if(classify(techtest[i])!=4)
			{
				errorcount++;
				
			}
		}
		
		int total=sportstest.length+techtest.length+politicstest.length+entertaintmenttest.length+businesstest.length;
		double accuracy= (double)(total-errorcount)/total;
		 System.out.println("Accuracy " + accuracy);
	
	}
	
	public static void main(String[] args)
	{		
		String folderName1="data/train/";
		String folderName2="data/test/";
		NBClassifier nb = new NBClassifier(folderName1);
		nb.classifyAll(folderName2);
	}
}


