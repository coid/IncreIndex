package lz.incr.index;


import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.json.JSONException;

public class HelloLucene {
  public static void main(String[] args) throws IOException, JSONException, ParseException{
    // 0. Specify the analyzer for tokenizing text.
    //    The same analyzer should be used for indexing and searching
    //StandardAnalyzer analyzer = new StandardAnalyzer();

    // 1. create the index
	final String indxfile ="C:\\Users\\lizhuo\\Desktop\\indx";
	
	final String jsonfile ="C:\\Users\\lizhuo\\Desktop\\db0.json";
	final String jsonafile = "C:\\Users\\lizhuo\\Desktop\\db1.json";
	final String jsonafile1 = "C:\\Users\\lizhuo\\Desktop\\db2.json";
	final String jsonafile2 = "C:\\Users\\lizhuo\\Desktop\\db3.json";
	final String jsonafilet = "C:\\Users\\lizhuo\\Desktop\\db01.json";
	final String jsonafilet1 = "C:\\Users\\lizhuo\\Desktop\\db012.json";
	final String jsonafilet2 = "C:\\Users\\lizhuo\\Desktop\\db0123.json";
	
	
	
	System.out.println("index begin" );
	long start = System.currentTimeMillis();

	LuceneNormIndexWriter ni = new LuceneNormIndexWriter(indxfile, jsonfile);
	ni.createIndex(null);
	
//	LuceneIncrIndexWriter ii = new LuceneIncrIndexWriter(indxfile, jsonfile);
//	ii.createIndex();
  
	System.out.println("index is completed" );
	long end0 = System.currentTimeMillis();
	System.out.println("time0 is "+(end0-start)+"ms" );
	System.out.println("index update begin" );

//	ii.updateIndex(jsonafile, "");


	//ni.deleteIndex();
	LuceneNormIndexWriter ni1 = new LuceneNormIndexWriter(indxfile, jsonafilet);
	ni1.createIndex(null);

//	String s = "2016-04-01";
//	ni.createIndex(s);
//	Query q =new TermQuery(new Term("deadline",s));
//	ni.deleteIndexDoc(q);
	
    long end1 = System.currentTimeMillis();
    System.out.println("time1 is "+(end1-start)+"ms" );
//    ii.updateIndex(jsonafile1, "");
    //ni.deleteIndex();
    LuceneNormIndexWriter ni2 = new LuceneNormIndexWriter(indxfile, jsonafilet1);
	ni2.createIndex(null);
    long end2 = System.currentTimeMillis();
    System.out.println("time2 is "+(end2-start)+"ms" );
 //   ii.updateIndex(jsonafile2, "");
    //ni.deleteIndex();
    LuceneNormIndexWriter ni3 = new LuceneNormIndexWriter(indxfile, jsonafilet2);
	ni3.createIndex(null);
    long end3 = System.currentTimeMillis();
    System.out.println("time3 is "+(end3-start)+"ms" );
    
    
    
   
//    StandardAnalyzer analyzer = new StandardAnalyzer();
//    Query q = new QueryParser("to", analyzer).parse("π„÷›");
//    //Query q =new TermQuery(new Term("deadline","2016-03-21"));
//    System.out.println(q.toString());
//    int hitsPerPage = 10;
//    IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indxfile)));
//    IndexSearcher searcher = new IndexSearcher(reader);
//    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
//    searcher.search(q, collector);
//    ScoreDoc[] hits = collector.topDocs().scoreDocs;
//    
//    System.out.println("Found " + hits.length + " hits.");
//    for(int i=0;i<hits.length;++i) {
//      int docId = hits[i].doc;
//      Document d = searcher.doc(docId);
//      System.out.println((i + 1) + ". " + d.get("from") + "\t" + d.get("to")+ "\t" + d.get("deadline"));
//    }
//    
//    reader.close();
  }

  
}