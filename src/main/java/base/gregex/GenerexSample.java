package base.gregex;

import com.mifmif.common.regex.Generex;
import com.mifmif.common.regex.util.Iterator;

import java.util.List;

/**
 * Created by changhongzi on 2016/8/27.
 */
public class GenerexSample {
    public static void main(String[] args){
        Generex generex = new Generex("[0-3]([a-c]|[e-g]{1,2})");

        // Generate random String
        String randomStr = generex.random();
        System.out.println(randomStr);// a random value from the previous String list

        // generate the second String in lexicographical order that match the given Regex.
        String secondString = generex.getMatchedString(2);
        System.out.println(secondString);// it print '0b'

        // Generate all String that matches the given Regex.
        List<String> matchedStrs = generex.getAllMatchedStrings();

        // Using Generex iterator
        Iterator iterator = generex.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
    }
}
