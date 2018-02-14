import java.util.Scanner; 
import edu.princeton.cs.algs4.In;
import java.util.Iterator;

public class TestDeque{
     public void intOrderTest(){ 
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Input the name of text file:");
//        String fileName = scan.next();
//        scan.close();
//        In in = new In(fileName);      // input file
         
        In in = new In("dequeTest.txt");      // input file 
        Deque<Integer> testDeque =new Deque<Integer>();
        while(!in.isEmpty()){
        testDeque.addFirst(in.readInt());
        }
        System.out.println("Size of the deque is: "+testDeque.size()+"\n");
        System.out.println("Output removeLast(): "+"\n");
        while(!testDeque.isEmpty()){
            System.out.println(testDeque.removeLast()+"; ");
        }
    }
     
     public void iteratorTest(){
        In in = new In("dequeTest.txt");      // input file 
        Deque<Integer> testDeque =new Deque<Integer>();
        while(!in.isEmpty()){
        testDeque.addFirst(in.readInt());
        }
        Iterator<Integer> testIterator=testDeque.iterator();
        System.out.println("Iterator output:"+"\n");
        while(testIterator.hasNext()){
            System.out.println(testIterator.next()+" ");
        }
     }
      public static void main(String[] args){
         TestDeque tester = new TestDeque();
         tester.iteratorTest();
    }
     
}