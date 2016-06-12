package com.amazon.cache.lru;

import java.util.HashMap;

public class Cache {

	private static HashMap<Integer, QueueNode> queueMap = null;
	private QueueCache head = null;

	public Cache() {

	}

	public Cache(int size) {
		queueMap = new HashMap<>();
		head = new QueueCache(size);
	}

	public void insertIntoCache(int pageNumber, int data) {
		// checks if cache is empty or not
		if (head.isCacheEmpty()) {
			System.out.println("Cache is empty , need to add node in cache now");
			// insert one node in the front of cache
			QueueNode node = new QueueNode(data);
			node.setPageNumber(pageNumber);
			head.front = head.rear = node;
			head.setCount(1);
			queueMap.put(pageNumber, node);
			return;
		}

		// cache is not empty
		// checks if the node exist in hashmap
		// if yes bring that node in front of queue
		// else add new node in front and remove least recently used node from
		// cache if cache is full

		
		QueueNode node = queueMap.get(pageNumber);
		if(node != null){
			if(node.front == null && node.rear == null){
				//only node
				node.setData(data);
				return;
			}
			if(node.rear == null && node.front != null){
				//it's a front node
				node.setData(data);
				return;
			}
			if(node.front == null){
				//its the last node
				head.rear = node.rear;
				node.rear.front = null;
			}else {
				//its the mid node
				node.rear.front = node.front;
				node.front.rear = node.rear;
			}
			
			node.front = head.front;
			head.front.rear = node;
			head.front =  node;
			node.setData(data);
			return;
		}
		
		//this case occurs when we need to enter new node in queue
		
		if(head.isCacheFull()){
			//remove last node from queue and map
			
			QueueNode tempNode = queueMap.get(head.rear.getPageNumber());
			System.out.println("removing node " + tempNode.getData() + " page number " + tempNode.getPageNumber());
			queueMap.remove(tempNode.getPageNumber());
			head.rear = tempNode.rear;
			tempNode.rear.front = null;
			tempNode = null;
			head.setCount(head.getCount() - 1);
		}
		
		//now add new node in front of queue
		QueueNode newNode = new QueueNode(data);
		newNode.setPageNumber(pageNumber);
		newNode.front = head.front;
		head.front.rear = newNode;
		head.front = newNode;
		head.setCount(head.getCount() + 1);
		queueMap.put(pageNumber, newNode);
		return;
	}
	
	public void removeDataFromCache(int pageNumber){
		if(queueMap.get(pageNumber) == null){
			//node does not exist in cache
			System.out.println("node does  not exist");
			return;
		}
		
		if(head.isCacheEmpty()){
			System.out.println("cache is empty nothing to be removed");
			return;
		}
		
		QueueNode node = queueMap.get(pageNumber);
		queueMap.remove(pageNumber);
		System.out.println("removing node with data and page number " + node.getData() + " " + node.getPageNumber());
		head.setCount(head.getCount() - 1);
		//if its the only node 
		if(node.rear == null && node.front == null){
			head.front = head.rear = null;
			node = null;
			return;
		}
		//if node is front node
		if(node.rear == null){
			node.front.rear = null;
			head.front = node.front;
			node = null;
			return;
		}
		
		//node to be deleted is end node
		if(node.front == null){
			head.rear = node.rear;
			node.rear.front = null;
			node = null;
			return;
		}
		
		node.front.rear = node.rear;
		node.rear.front = node.front;
		node = null;
		return;
	}
	
	public int referenceNodeFromCache(int pageNumber){
		if(queueMap.get(pageNumber) == null){
			System.out.println("node does not exist, please check again page number" + pageNumber);
			return -1;
		}
		
		QueueNode node = queueMap.get(pageNumber);
		if(node.front == null){
			//this is the front node do nothing
		} else{
			if(node.front == null){
				//this is the  last node
				head.rear = node.rear;
				node.rear.front = null;
			}else {
				node.front.rear = node.rear;
				node.rear.front = node.front;
			}
			node.front = head.front;
			head.front.rear = node;
			head.front = node;
		}
		return  node.getData();
	}
	
	
	public boolean isCacheFull() {
		return head.isCacheFull();
	}

	public boolean isCacheEmpty() {
		return head.isCacheEmpty();
	}
	
	public int getCount() {
		return head.getCount();
	}
}

class QueueCache {
	int count;
	int totalSize;
	QueueNode front; // front of the queue
	QueueNode rear; // rear end of the queue

	public QueueCache() {
		this.count = 0;
		this.totalSize = 0;
		front = rear = null;
	}

	public QueueCache(int totalSize) {
		this.totalSize = totalSize;
		this.count = 0;
		front = rear = null;
	}

	public boolean isCacheFull() {
		if (this.count == this.totalSize) {
			return true;
		}
		return false;
	}

	public boolean isCacheEmpty() {
		if (this.count == 0) {
			return true;
		}

		return false;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

}

class QueueNode {
	int data;
	int pageNumber;
	QueueNode front; // points to next node
	QueueNode rear; // points to previous node

	public QueueNode() {
		front = rear = null;
	}

	public QueueNode(int data) {
		this.data = data;
		front = rear = null;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	
}