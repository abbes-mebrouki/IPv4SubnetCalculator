package Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class PublicIP {

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
//        while (true) {
//            try {
////            URL getPublicIP = new URL("https://checkip.amazonaws.com");
////            BufferedReader ipin = new BufferedReader(new InputStreamReader(getPublicIP.openStream()));
////            String publicIP = ipin.readLine();
////            System.out.println("Public IP : " + publicIP);
//

//                System.out.print("Enter the host ip : ");
//                String userIP = in.next();
//
//                InetAddress ipaddress = InetAddress.getByName(userIP);
//                //System.out.println(ipaddress);
//                if (ipaddress.isReachable(5000)) {
//                    System.out.println(userIP + " is reachable.");
//                } else {
//                    System.out.println(userIP + " is not reachable.");
//                }
//            } catch (MalformedURLException ex) {
//                System.err.println("Exception : " + ex.getMessage());
//            } catch(IOException e){
//                System.err.println("Invalid Input.");
//                
//            }
//        }
        while (true) {
            System.out.print("~~#");
            String command = in.nextLine();
            runSystemCommand(command);
        }
    }

    public static void runSystemCommand(String command) {

        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            String s = "";
            StringBuilder theWholeS = new StringBuilder();
            // reading output stream of the command
            while ((s = inputStream.readLine()) != null) {
                System.out.println(s);
                //theWholeS.append(s);
            }
            //System.out.println(theWholeS);

        } catch (IOException e) {
            System.err.println("IOException : " + e.getMessage());
        }
    }
}
