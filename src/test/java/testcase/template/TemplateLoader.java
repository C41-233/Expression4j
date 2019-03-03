package testcase.template;

import java.io.InputStream;
import java.util.Scanner;

public class TemplateLoader {

    public static String load(String name) {
        StringBuilder sb = new StringBuilder();
        InputStream is = TemplateLoader.class.getResourceAsStream(name + ".txt");
        try(Scanner scanner = new Scanner(is, "utf-8")){
            while(scanner.hasNext()){
                sb.append(scanner.nextLine() + "\n");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
