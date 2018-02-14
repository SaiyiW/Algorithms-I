import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first,last;
    private int size;
    private class Node{
        Item item;
        Node last;
        Node next;
    }
     private class DequeIterator implements Iterator<Item>{
        private Node current = first;
        public boolean hasNext(){
            return current!=null;
        }
        public Item next(){
            if(current==null){
                throw new java.util.NoSuchElementException();
            }
            Item item=current.item;
            current=current.next;
            return item;
        }
        public void remove(){
            throw new java.lang.UnsupportedOperationException();
        }
    }
     public Iterator<Item> iterator(){         // return an iterator over items in order from front to end
        return new DequeIterator();
    }
    public Deque(){                           // construct an empty deque
        size=0;
        first=last=null;
    }
    public boolean isEmpty(){                 // is the deque empty?
        return size==0;
    }
    public int size(){                        // return the number of items on the deque
        return size;
    }
    public void addFirst(Item item){          // add the item to the front
         if(item==null) {
            throw new java.lang.IllegalArgumentException();
        }
         size++;
         Node oldFirst=first;
         first=new Node();
         first.item=item;
         first.next=oldFirst;
         first.last=null;
         if(size==1)  //was empty before adding
            last=first;
         else
             oldFirst.last=first;
    }
    public void addLast(Item item){           // add the item to the end
        if(item==null) {
            throw new java.lang.IllegalArgumentException();
        }
        size++;
        Node oldLast=last;
        last=new Node();
        last.item=item;
        last.last=oldLast;
        last.next=null;
       
        if(size==1) //was empty before adding
            first=last;
        else
            oldLast.next=last;       
    }
    public Item removeFirst(){                // remove and return the item from the front
        if(isEmpty()){
            throw new java.util.NoSuchElementException();
        }
        size--;
        Item item=first.item;
        first=first.next;
        if(size==0)
            last=null;
        else
             first.last=null;
       return item;
    }
    public Item removeLast() {                // remove and return the item from the end
        if(isEmpty()){
            throw new java.util.NoSuchElementException();
        }
        size--;
        Item item=last.item;
        last=last.last;
        if(size==0)
            first=null;
        else
            last.next=null;   
        return item;
    }
   
   
    public static void main(String[] args){   // unit testing (optional)
        
    }
}