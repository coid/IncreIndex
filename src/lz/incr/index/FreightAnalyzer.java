package lz.incr.index;

import org.apache.lucene.analysis.Analyzer;

import java.io.Reader;
import java.util.Map.Entry;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.CharArrayMap;
import org.apache.lucene.util.Version;

public class FreightAnalyzer extends Analyzer {
	
	private Analyzer valueAnalyzer;
	private Analyzer fieldAnalyzer;
	
	private final Version matchVersion;
	
	public FreightAnalyzer(final Version v, final Analyzer fA,
			final Analyzer vA){
		matchVersion = v;
	    this.valueAnalyzer = vA;
	    this.fieldAnalyzer = fA;
	}
	
	public void setValueAnalyzer(final Analyzer analyzer) {
	    valueAnalyzer = analyzer;
	  }
	
	public void setFieldAnalyzer(final Analyzer analyzer) {
	    fieldAnalyzer = analyzer;
	  }

	protected TokenStreamComponents createComponents(final String fieldName, final Reader reader) {
		// TODO Auto-generated method stub
		
		return null;
	}

	protected TokenStreamComponents createComponents(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
