import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int size;
    
    public RandomizedQueue(){                 // construct an empty randomized queue
        size=0;
        array=(Item[]) new Object[1];        //initially an empty array with one null inside
    }
    public boolean isEmpty(){                 // is the randomized queue empty?
        return size==0;
    }
    public int size() {                       // return the number of items on the randomized queue
        return size;
    }

    /* add the item*/
    public void enqueue(Item item) {
        if(item==null) {
            throw new java.lang.IllegalArgumentException();
        }
        if(size==array.length)
            resize(2*array.length);
        array[size]=item;
        size++;
    }
    /* remove and return a random item*/
    public Item dequeue() {
        if(isEmpty()){
            throw new java.util.NoSuchElementException();
        }
        
        Item item;
        int randomIndex=StdRandom.uniform(0,size);  //[0,size)
        item=array[randomIndex];
        array[randomIndex]=array[size-1];
        array[size-1]=null;
        size--;
        
        if(size==array.length/4)
            resize(array.length/2);
        if(array.length==0)
            array=(Item[]) new Object[1];

        return item;
    }

    /* return a random item (but do not remove it)*/
    public Item sample() {
        if(isEmpty()){
            throw new java.util.NoSuchElementException();
        }       
        int randomIndex=StdRandom.uniform(0,size);  //[0,size)      
        return array[randomIndex];   
    }
    
    /* return an independent iterator over items in random order*/
    public Iterator<Item> iterator(){
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item>{
        Item[] randomArray;
        int current;
        public RandomIterator(){
            randomArray=(Item[]) new Object[size];
            for(int i=0;i<size;i++){
                randomArray[i]=array[i];
            }
            StdRandom.shuffle(randomArray);
            current=0;
        }
        
        public boolean hasNext(){
            return current<(size);
        }
        public Item next(){
            if(current>=size){
                throw new java.util.NoSuchElementException();
            }
            Item item=randomArray[current];
            current++;
            return item;
        }
        public void remove(){
            throw new java.lang.UnsupportedOperationException();
        }
    }
    private void resize(int capacity){
        Item[] copy=(Item[]) new Object[capacity];
        for(int i=0;i<size;i++){
            copy[i]=array[i];
        }
        array=copy;
    }
    public static void main(String[] args){   // unit testing (optional)
    }
}