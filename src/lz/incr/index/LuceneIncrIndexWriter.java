package lz.incr.index;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.json.JSONException;
import org.json.JSONObject;


public class LuceneIncrIndexWriter {
	String indexPath = "";
	
	String jsonFilePath = "";
	
	String jsonAddFilePath = "";
	
	//IndexWriter indexWrite = null;
	
	Map<String,IndexWriter> indexMap = new HashMap<String,IndexWriter>();
	//IndexNode<String> ROOT = new IndexNode<String>("ROOT");
	
	
	public LuceneIncrIndexWriter(String indexPath, String jsonFilePath){
		this.indexPath = indexPath;
		this.jsonFilePath = jsonFilePath;
	}	
	

	public void createIndex() throws IOException, JSONException{
		openIndex("ROOT");
		parseraddJSONFile(jsonFilePath);		
		//addDocuments(jA);
		finish();
	}

	private void finish() {
		// TODO Auto-generated method stub
		try{
			for(Object key:indexMap.keySet()){
				
				if(indexMap.get(key).isOpen()){
					indexMap.get(key).commit();
					indexMap.get(key).close();
				}
			}
			
		}catch(IOException e){
			System.err.println("We had a problem closing the index: " + e.getMessage());
		}
		
	}


	private boolean openIndex(String name) {
		// TODO Auto-generated method stub
		try{
			if (name.equals("ROOT") ){
				
				return true;
			}
			
			StandardAnalyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig config = new IndexWriterConfig(analyzer);				
			Directory dir = FSDirectory.open(Paths.get(indexPath+'\\'+name));			
						
			//indexMap.put(name, null);			
			//System.out.println(name+" index is open" );
			if(!DirectoryReader.indexExists(dir)){
			config.setOpenMode(OpenMode.CREATE);
			IndexWriter indexWrite = new IndexWriter(dir,config);			
			indexMap.put(name, indexWrite);}
			else{ 
				config.setOpenMode(OpenMode.APPEND);
				IndexWriter indexWrite = new IndexWriter(dir,config);			
				indexMap.put(name, indexWrite);
			}
			
			return true;
		}catch(Exception e){
			System.err.println("Error opening the index. " + name);
		}		
		return false;		
	}

	@SuppressWarnings({ "resource" })
	private void parseraddJSONFile(String path) throws IOException{
		// TODO Auto-generated method stub
		FileInputStream jsonFile = new FileInputStream(path);
		BufferedReader readerJson = new BufferedReader(new InputStreamReader(jsonFile,"UTF-8"));
		String line = null;
		
		int count = 0;
		while((line = readerJson.readLine()) != null){
			JSONObject jO;
			//Document doc_t = new Document();
			Document doc_p = new Document();
			try {				
				jO = new JSONObject(line);
				JSONObject deadline = jO.getJSONObject("deadline");					
				String dt = deadline.getString("$date");
				dt=dt.substring(0,10);
				//System.out.println(dt);				
				//doc_t.add(new StringField("deadline", dt, Field.Store.YES));
				if(!indexMap.containsKey(dt)){
					openIndex(dt);
				}
				if(indexMap.containsKey(dt) && !indexMap.get(dt).isOpen()){
					//indexMap.get(dt).					
					openIndex(dt);					
				}
				String from = jO.getString("from");
				String to = jO.getString("to");
				//System.out.println(dt+" "+from+" "+to);
				doc_p.add(new TextField("from", from, Field.Store.YES));
				doc_p.add(new TextField("to", to, Field.Store.YES));
				
				//indexMap.get("ROOT").addDocument(doc_t);
				indexMap.get(dt).addDocument(doc_p);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				System.err.println("Error in" + count);
			}
			count++;
		}
	}
	
	@SuppressWarnings({ "resource" })
	private void parseraddJSONFile2(String path) throws IOException{
		// TODO Auto-generated method stub
		FileInputStream jsonFile = new FileInputStream(path);
		BufferedReader readerJson = new BufferedReader(new InputStreamReader(jsonFile,"UTF-8"));
		String line = null;
		
		int count = 0;
		while((line = readerJson.readLine()) != null){
			JSONObject jO;
			//Document doc_t = new Document();
			Document doc_p = new Document();
			try {				
				jO = new JSONObject(line);
				JSONObject deadline = jO.getJSONObject("deadline");					
				String dt = deadline.getString("$date");
				dt=dt.substring(0,10);
				//System.out.println(dt);				
				//doc_t.add(new StringField("deadline", dt, Field.Store.YES));
				if(!indexMap.containsKey(dt)){
					openIndex(dt);
				}
				if(indexMap.containsKey(dt) && !indexMap.get(dt).isOpen()){
					//indexMap.get(dt).	
					
					openIndex(dt);					
				}
				String from = jO.getString("from");
				String to = jO.getString("to");
				//System.out.println(dt+" "+from+" "+to);
				doc_p.add(new TextField("from", from, Field.Store.YES));
				doc_p.add(new TextField("to", to, Field.Store.YES));
				
				//indexMap.get("ROOT").addDocument(doc_t);
				indexMap.get(dt).addDocument(doc_p);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				System.err.println("Error in" + count);
			}
			count++;
		}
	}
	
	public void updateIndex(String jsonAddFilePath,String removeIndex) throws IOException{
		openIndex("ROOT");		
		parseraddJSONFile2(jsonAddFilePath);		
		//addDocuments(jA);		
		//System.out.println("pass");
		if(indexMap.containsKey(removeIndex))
		{
			openIndex(removeIndex);			
			indexMap.get(removeIndex).deleteAll();		
		}	
		
		finish();		
		indexMap.remove(removeIndex);
	}
	
}
