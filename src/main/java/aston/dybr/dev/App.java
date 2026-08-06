package aston.dybr.dev;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        for (int i = 0; i < 11; i++) {

            map.put(i + "", i);

        }

        map.printBuckets();

        for (int i = 1; i < 5; i++) {

            map.remove(i + "");

        }

        System.out.println();
        System.out.println("После удаления");
        System.out.println();

        map.printBuckets();
    }
}
