
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(args[0].equals("updateDB")){
            SQLdb.updateDB(args[1]);
        }
//        SQLdb.updateDB("C:/test_for_project");
    }


}
