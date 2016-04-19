package lz.incr.index;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.json.JSONException;
import org.json.JSONObject;


public class LuceneNormIndexWriter {
	String indexPath = "";
	
	String jsonFilePath = "";
	
	IndexWriter indexWrite = null;	
	
	
	public LuceneNormIndexWriter(String indexPath, String jsonFilePath){
		this.indexPath = indexPath;
		this.jsonFilePath = jsonFilePath;
	}

	public void createIndex(String s) throws IOException, JSONException{
		openIndex();
		//System.out.println(indexWrite.numDocs());
		parseraddJSONFile(s);		
		//addDocuments(jA);
		
		finish();
	}
	
	public void addIndex(String indexPath1) throws IOException{
		openIndex();
		Directory aux = FSDirectory.open(Paths.get(indexPath1));	   
		indexWrite.addIndexes(new Directory[] { aux });
		finish();
	}
	
	public void deleteIndex() throws IOException{
		openIndex();
		indexWrite.deleteAll();
		finish();
	}
	
	public void deleteIndexDoc(Query q) throws IOException{
		openIndex();		
		indexWrite.deleteDocuments(q);		
		finish();
	}

	private void finish() {
		// TODO Auto-generated method stub
		try{
			indexWrite.commit();
			//System.out.println(indexWrite.numDocs());
			indexWrite.close();
		}catch(IOException e){
			System.err.println("We had a problem closing the index: " + e.getMessage());
		}
		
	}


	private boolean openIndex() {
		// TODO Auto-generated method stub
		try{
			
			StandardAnalyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig config = new IndexWriterConfig(analyzer);				
			Directory dir = FSDirectory.open(Paths.get(indexPath));
			
			config.setOpenMode(OpenMode.CREATE_OR_APPEND);
			//System.out.println("index is open" );
			indexWrite = new IndexWriter(dir,config);
			return true;
		}catch(Exception e){
			System.err.println("Error opening the index. " + e.getMessage());
		}
		
		return false;		
	}

	@SuppressWarnings({ "resource" })
	private void parseraddJSONFile(String remove) throws IOException{
		// TODO Auto-generated method stub
		FileInputStream jsonFile = new FileInputStream(jsonFilePath);
		BufferedReader readerJson = new BufferedReader(new InputStreamReader(jsonFile,"UTF-8"));
		String line = null;
		
		int count = 0;
		while((line = readerJson.readLine()) != null){
			JSONObject jO;
			Document doc = new Document();
			try {
				
				jO = new JSONObject(line);
				JSONObject deadline = jO.getJSONObject("deadline");					
				String dt = deadline.getString("$date");
				dt=dt.substring(0,10);
				if(dt.equals(remove)){continue;}
				//System.out.println(dt);
				String from = jO.getString("from");
				String to = jO.getString("to");
				//System.out.println(dt+" "+from+" "+to);
				doc.add(new TextField("from", from, Field.Store.YES));
				doc.add(new TextField("to", to, Field.Store.YES));
				doc.add(new StringField("deadline", dt, Field.Store.YES));
				indexWrite.addDocument(doc);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				System.err.println("Error in" + count);
			}
			count++;
		}
		
		//indexWrite.close();
	}
}
