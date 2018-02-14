public class TryArray{
    private Integer[] array;
    public TryArray(){
        array = new Integer[5];
        array[0]=1;
        array[1]=0;
        array[2]=null;
        array[3]=4; 
        array[4]=null;
    }
    public int length(){
        return array.length;
    }
    public static void main (String[] args){
        TryArray test=new TryArray();
        System.out.println("The length of this array is: "+test.length());
    }
}