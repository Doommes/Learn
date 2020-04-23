package com.doommes.learn.Lru;

import android.util.Log;

import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LruCache {
    private Node head;
    private Node end;

    private int limit;

    private HashMap<String, Node> hashMap;

    public LruCache() {
        limit = 10;
        hashMap = new HashMap<>();
    }

    public LruCache(int limit) {
        this.limit = limit;
        hashMap = new HashMap<>();
    }

    public void put (String key, String value){
        Node node = hashMap.get(key);
        if (node == null){
            if (hashMap.size() >= limit){
                String oldKey = removeNode(head);
                hashMap.remove(oldKey);
            }
            node = new Node(key, value);
            addNode(node);
            hashMap.put(key, node);
        }else {
            node.value = value;
            refreshNode(node);
        }
    }


    public String get (String key){
        Node node = hashMap.get(key);
        if (node == null){
            return null;
        }
        refreshNode(node);
        return node.value;
    }

    private void refreshNode(Node node) {
        if (node == end){
            return ;
        }
        removeNode(node);
        addNode(node);
    }

    private void addNode(Node node) {
        if (end != null){
            end.next = node;
            node.pre = end;
        }
        end = node;
        if (head == null){
            head = node;
        }
    }

    private String removeNode(Node node) {
        if (node == head){
            head = node.next;
        }else if (node == end){
            end = node.pre;
        }else {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }

        return node.key;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    private class Node {
        private String key;
        private String value;
        private Node pre;
        private Node next;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args){
        LruCache lruCache = new LruCache(5);
        lruCache.put("1", "one1");
        lruCache.put("2", "one2");
        lruCache.put("3", "one3");
        lruCache.put("4", "one4");
        lruCache.put("5", "one5");
        System.out.println(lruCache.getEnd().value);
        lruCache.get("3");
        System.out.println(lruCache.getEnd().value);
        lruCache.put("6", "noe6");


        System.out.println(lruCache.getHead().value);
        System.out.println(lruCache.getEnd().value);


    }
}
