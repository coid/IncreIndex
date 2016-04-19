package lz.incr.index;

import java.util.ArrayList;
import java.util.List;

public class IndexNode<T> {
	private List<IndexNode<T>> children = new ArrayList<IndexNode<T>>();
	private IndexNode<T> parent = null;
	private T data = null;
	//private String name = "";
	
	public IndexNode(T data){
		//this.name = name;
		this.data = data;
	}
	
	public IndexNode(T data, IndexNode<T> parent){
		//this.name = name;
		this.data = data;
		this.parent = parent;
	}
	
	public List<IndexNode<T>> getChildren(){
		return children;
	}
	
	public void setParent(IndexNode<T> parent){
		parent.addChild(this);
		this.parent = parent;
	}
	
	public void addChild(T data){
		IndexNode<T> child = new IndexNode<T>(data);
		child.setParent(this);
		this.children.add(child);
	}

	public void addChild(IndexNode<T> indexNode) {
		// TODO Auto-generated method stub
		indexNode.setParent(this);
		this.children.add(indexNode);
	}
	
	public T getData(){
		return this.data;
	}
	
//	public String getName(){
//		return this.name;
//	}
	
	public void setData(T data){
		this.data =data;
	}
	
//	public void setName(String name){
//		this.name = name;
//	}
	
	public boolean isRoot(){
		return (this.parent == null);
	}
	
	public boolean isLeaf(){
		if(this.children.size() ==0)
			return true;
		else
			return false;
	}
	
	public void removeParent(){
		this.parent = null;
	}

}
