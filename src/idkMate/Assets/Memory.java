package idkMate.Assets;
 
public class Memory {
   
    private static int firstPoint;
    private static int secondPoint;
    public Memory() {
       
    }
    public static void setMemory(int a, int b) {
        firstPoint = a;
        secondPoint = b;
    }
   
    public static int getPointA() {
        return firstPoint;
    }
    public static int getPointB() {
        return secondPoint;
    }
}