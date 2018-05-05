package Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class MSNUM {

    public static void main(String[] args) throws UnknownHostException, SocketException {
//        try {
//        Process p = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
//        BufferedReader inn = new BufferedReader(new InputStreamReader(p.getInputStream()));
//        
//        String result = "";
//        while (true) {
//
//            String line = inn.readLine();
//            
//            if (line == null) {
//                break;
//            }
//            
//            result += line;
//            
//            System.out.println(line);
//            System.out.println("\n-------------" + line.length());
//        }
//            System.out.println("\n==================" + result);
//    } catch (IOException e) {
//            System.err.println("Exception : "+ e.getMessage());
//    }

//        try {
//            String result = "";
//            Process p = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
//            try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
//                String line;
//                while ((line = input.readLine()) != null) {
//                    result += line;
//                }
//                if (result.equalsIgnoreCase(" ")) {
//                    System.out.println("Result is empty");
//                } else {
//                    System.out.println(result);
//                }
//            }
//        } catch (IOException ex) {
//            System.err.println(ex);
//        }

        InetAddress ip;
	try {
			
		ip = InetAddress.getLocalHost();
		System.out.println("Current IP address : " + ip.getHostAddress());
		
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			
		byte[] mac = network.getHardwareAddress();
			
		System.out.print("Current MAC address : ");
			
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
		}
		System.out.println(sb.toString());
			
	} catch (UnknownHostException | SocketException e) {
	}

    }
}
