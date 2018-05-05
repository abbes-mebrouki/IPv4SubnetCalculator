package TemporaryName;

import Helper.Helper;
import Helper.Helper.WinType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WindowController implements Initializable {

    @FXML
    private AnchorPane mainPnl;
    @FXML
    private JFXTextField ip_in;
    @FXML
    private JFXTextField cidr_in;
    @FXML
    private JFXButton resultBtn;
    @FXML
    private Label classLbl;
    @FXML
    private Label deMaskLbl;
    @FXML
    private Label netPLbl;
    @FXML
    private Label hostPLbl;
    @FXML
    private Label hRangeLbl;
    @FXML
    private Label netIdLbl;
    @FXML
    private Label bcIdLbl;
    @FXML
    private Label netsNumLbl;
    @FXML
    private Label hostsNumLbl;
    @FXML
    private Label cidrLbl;

    boolean onTopFlag;
    @FXML
    private JFXCheckBox onTopBox;

    int whatOctet;

    Helper helper = new Helper();
    @FXML
    private AnchorPane tblPnl;
    @FXML
    private TableView<TableManager> tbl;
    @FXML
    private TableColumn<TableManager, Long> netNumClm;
    @FXML
    private TableColumn<TableManager, String> netIDClm;
    @FXML
    private TableColumn<TableManager, String> fHostClm;
    @FXML
    private TableColumn<TableManager, String> lHostClm;
    @FXML
    private TableColumn<TableManager, String> bcIDClm;

    ObservableList<TableManager> netsList = FXCollections.observableArrayList();
    @FXML
    private StackPane satchPane;

    JFXDialogLayout dlLayout;
    JFXDialog dialog;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dlLayout = new JFXDialogLayout();
        dialog = new JFXDialog(satchPane, dlLayout, JFXDialog.DialogTransition.CENTER);

        onTopFlag = false;
        whatOctet = 0;
//        OnTopFunction();
        va();
        vaCIDR();
        initCols();

    }

    @FXML
    private void ipAction(ActionEvent event) {
        doWork();
    }

    @FXML
    private void cidrAction(ActionEvent event) {
        doWorkCIDR();
    }

    @FXML
    private void resultBtnAction(ActionEvent event) {
        doWorkCIDR();
    }

    private void down() {
        double h = ((Stage) resultBtn.getScene().getWindow()).getHeight();
        ((Stage) resultBtn.getScene().getWindow()).setHeight(444);

    }

    private boolean isItAValidIP(String userIP) {
        final String ipPattern
                = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        // 
        return Pattern.matches(ipPattern, userIP);
    }

    private void doWork() {
        if (!ip_in.getText().isEmpty()) {
            String uip = ip_in.getText();
            if (uip.length() > 15 || (!isItAValidIP(uip))) {
                userWarning("Invalid input", "this is not ip.");
                //System.err.println("This is not ip.");
            } else {

                char dot = '.';

                String oc1 = "";
                String oc2 = "";
                String oc3 = "";
                String oc4 = "";

                int indexOfDot1, indexOfDot2, indexOfDot3;
                indexOfDot1 = indexOfDot2 = indexOfDot3 = -1;
                char[] ch = uip.toCharArray();

                for (int i = 0; i < ch.length; i++) {
                    if (ch[i] == dot) {
                        indexOfDot1 = i;
                        break;
                    }

                }
                for (int n = 0; n < indexOfDot1; n++) {
                    oc1 += ch[n];
                }

                for (int i = indexOfDot1 + 1; i < ch.length; i++) {
                    if (ch[i] == dot) {
                        indexOfDot2 = i;
                        break;
                    }
                }
                for (int n = indexOfDot1 + 1; n < indexOfDot2; n++) {
                    oc2 += ch[n];
                }

                for (int i = indexOfDot2 + 1; i < ch.length; i++) {
                    if (ch[i] == dot) {
                        indexOfDot3 = i;
                        break;
                    }
                }
                for (int n = indexOfDot2 + 1; n < indexOfDot3; n++) {
                    oc3 += ch[n];
                }
                for (int i = indexOfDot3 + 1; i < ch.length; i++) {
                    oc4 += ch[i];
                }

                if (oc1.length() > 3 || oc2.length() > 3 || oc3.length() > 3 || oc4.length() > 3) {
                    userWarning("Invalid input", "this is not ip.");
                    //System.err.println("this is not ip.");
                    return;
                }
                int oct1 = Integer.parseInt(oc1);
                int oct2 = Integer.parseInt(oc2);
                int oct3 = Integer.parseInt(oc3);
                int oct4 = Integer.parseInt(oc4);
                if (oct1 > 255 || oct2 > 255 || oct3 > 255 || oct4 > 255) {
                    userWarning("Invalid input", "this is not ip.");
                    //System.err.println("this is not ip.");
                    return;
                }
                if (oct1 >= 224 || oct1 == 127 || oct1 == 0) {
                    userWarning("Invalid input", "this ip not allowed.");
                    //System.err.println("this ip not allowed.");
                    return;
                }

//            System.out.println("cotet 1 : " + oc1);
//            System.out.println("cotet 2 : " + oc2);
//            System.out.println("cotet 3 : " + oc3);
//            System.out.println("cotet 4 : " + oc4);
                // oct1 in [1 - 126]
                if (oct1 >= 1 && oct1 <= 126) {
                    down();
                    classLbl.setText("A");
                    deMaskLbl.setText("255.0.0.0");
                    cidrLbl.setText("/8");
                    netPLbl.setText(oc1);
                    hostPLbl.setText(oc2.concat(".").concat(oc3).concat(".").concat(oc4));
                    hRangeLbl.setText("[" + oc1.concat(".").concat("0").concat(".").concat("0").concat(".").concat("1")
                            .concat(" - ").concat(oc1).concat(".").concat("255").concat(".").concat("255").concat(".").concat("254") + "]");
                    netIdLbl.setText(oc1.concat(".").concat("0").concat(".").concat("0").concat(".").concat("0"));
                    bcIdLbl.setText(oc1.concat(".").concat("255").concat(".").concat("255").concat(".").concat("255"));
                    netsNumLbl.setText("1");
                    hostsNumLbl.setText("" + ((long) Math.pow(2, 24) - 2));

                } else if (oct1 >= 128 && oct1 <= 191) {
                    down();
                    classLbl.setText("B");
                    deMaskLbl.setText("255.255.0.0");
                    cidrLbl.setText("/16");
                    netPLbl.setText(oc1.concat(".").concat(oc2));
                    hostPLbl.setText(oc3.concat(".").concat(oc4));
                    hRangeLbl.setText("[" + oc1.concat(".").concat(oc2).concat(".").concat("0").concat(".").concat("1")
                            .concat(" - ").concat(oc1).concat(".").concat(oc2).concat(".").concat("255").concat(".").concat("254") + "]");
                    netIdLbl.setText(oc1.concat(".").concat(oc2).concat(".").concat("0").concat(".").concat("0"));
                    bcIdLbl.setText(oc1.concat(".").concat(oc2).concat(".").concat("255").concat(".").concat("255"));
                    netsNumLbl.setText("1");
                    hostsNumLbl.setText("" + ((long) Math.pow(2, 16) - 2));

                } else if (oct1 >= 192 && oct1 <= 223) {

                    down();
                    classLbl.setText("C");
                    deMaskLbl.setText("255.255.255.0");
                    cidrLbl.setText("/24");
                    netPLbl.setText(oc1.concat(".").concat(oc2).concat(".").concat(oc3));
                    hostPLbl.setText(oc4);
                    hRangeLbl.setText("[" + oc1.concat(".").concat(oc2).concat(".").concat(oc3).concat(".").concat("1")
                            .concat(" - ").concat(oc1).concat(".").concat(oc2).concat(".").concat(oc3).concat(".").concat("254") + "]");
                    netIdLbl.setText(oc1.concat(".").concat(oc2).concat(".").concat(oc3).concat(".").concat("0"));
                    bcIdLbl.setText(oc1.concat(".").concat(oc2).concat(".").concat(oc3).concat(".").concat("255"));
                    netsNumLbl.setText("1");
                    hostsNumLbl.setText("" + ((long) Math.pow(2, 8) - 2));

                } else {
                    userWarning("Invalid input", "Not supported");
                    //System.out.println("Not supported");
                }

            } // main if
        } else {
            userWarning("Invalid input", "please enter a valid IP.");
            //System.err.println("please enter a valid IP.");
        }

    }

    private void doWorkCIDR() {
        //down();
        if (cidr_in.getText().isEmpty()) {
            userWarning("Invalid input", "Please enter a valid CIDR.");
        } else {
            if (!isItAValidIP(ip_in.getText())) {
                userWarning("Invalid input", "Please enter a valid IP.");
            } else {
                if (cidr_in.getText().length() > 2) {
                    userWarning("Invalid input", "CIDR contains only two digits.");
                    //System.err.println("CIDR contains only two digits.");
                } else {
                    int cidr = Integer.parseInt(cidr_in.getText());
                    if (cidr <= 32) {
                        char[] octet1 = new char[8];
                        char[] octet2 = new char[8];
                        char[] octet3 = new char[8];
                        char[] octet4 = new char[8];

                        int IncrementSize = -1;

                        String octe1, octe2, octe3, octe4;
                        octe1 = octe2 = octe3 = octe4 = "";

                        long netsNumber = -1;
                        long hostsNumber = -1;
                        long incSiz = -1;

                        String networkAddr = "";
                        String firstHost = "";
                        String lastHost = "";
                        String broadcastAddr = "";

                        if (cidr <= 8) {
                            whatOctet = 1;
                            incSiz = getIncrementSize(cidr);
                            System.out.println("The increment size is : " + getIncrementSize(cidr));
                            hostsNumber = (long) Math.pow(2, (8 - cidr) + 24);
                            hostsNumber -= 2;
                            if (cidr == 8) {
                                netsNumber = (long) Math.pow(2, 0);
                            } else {
                                netsNumber = (long) Math.pow(2, cidr);
                            }
                            for (int i = 0; i < 8; i++) {
                                octet2[i] = '0';
                                octet3[i] = '0';
                                octet4[i] = '0';
                            }

                            for (int i = 0; i < cidr; i++) {
                                octet1[i] = '1';
                            }
                            for (int i = cidr; i < 8; i++) {
                                octet1[i] = '0';
                            }

//                    networkAddr = "" + getIncrementSize(cidr) + "." + getIPOctets().get(1) + "." + getIPOctets().get(2)
//                            + "." + getIPOctets().get(3);
//                    networkAddr = "" + getIncrementSize(cidr) + "." + "0" + "." + "0"
//                            + "." + "0";
//                    firstHost = "" + getIncrementSize(cidr) + "." + "0" + "." + "0"
//                            + "." + "1";
//                    int lh = (int)hostsNumber - 2;
//                    lastHost = "" + getIncrementSize(cidr) + "." + "0" + "." + "0"
//                            + "." + lh;
//
//                    int bcid = getIncrementSize(cidr) + (getIncrementSize(cidr) - 1);
//                    lh += 1;
//                    broadcastAddr = "" + bcid + "." + "0"+ "." + "0"
//                            + "." + lh;
                        } else if (cidr <= 16) { // ----------------
                            whatOctet = 2;
                            for (int i = 0; i < 8; i++) {
                                octet1[i] = '1';
                                octet3[i] = '0';
                                octet4[i] = '0';
                            }

                            cidr -= 8;
                            incSiz = getIncrementSize(cidr);
                            System.out.println("The increment size is : " + +getIncrementSize(cidr));
                            hostsNumber = (long) Math.pow(2, (8 - cidr) + 16);
                            hostsNumber -= 2;
                            if (cidr == 8) {
                                netsNumber = (long) Math.pow(2, 0);
                            } else {
                                netsNumber = (long) Math.pow(2, cidr);
                            }
                            for (int i = 0; i < cidr; i++) {
                                octet2[i] = '1';
                            }
                            for (int i = cidr; i < 8; i++) {
                                octet2[i] = '0';
                            }
//
//                    networkAddr = "" + getIPOctets().get(0) + "." + getIncrementSize(cidr) + "." + getIPOctets().get(2)
//                            + "." + getIPOctets().get(3);

                        } else if (cidr <= 24) { // ----------------
                            whatOctet = 3;

                            for (int i = 0; i < 8; i++) {
                                octet1[i] = '1';
                                octet2[i] = '1';
                                octet4[i] = '0';
                            }

                            cidr -= 16;
                            System.out.println("incSiz = " + getIncrementSize(cidr));
                            incSiz = getIncrementSize(cidr);

                            hostsNumber = (long) Math.pow(2, (8 - cidr) + 8);
                            hostsNumber -= 2;
                            if (cidr == 8) {
                                netsNumber = (long) Math.pow(2, 0);
                            } else {
                                netsNumber = (long) Math.pow(2, cidr);
                            }

                            for (int i = 0; i < cidr; i++) {
                                octet3[i] = '1';
                            }
                            for (int i = cidr; i < 8; i++) {
                                octet3[i] = '0';
                            }

//                    networkAddr = "" + getIPOctets().get(0) + "." + getIPOctets().get(1) + "." + getIncrementSize(cidr)
//                            + "." + getIPOctets().get(3);
                        } else if (cidr <= 32) { // ----------------
                            whatOctet = 4;
                            for (int i = 0; i < 8; i++) {
                                octet1[i] = '1';
                                octet2[i] = '1';
                                octet3[i] = '1';
                            }

                            cidr -= 24;
                            incSiz = getIncrementSize(cidr);
                            //System.out.println("The increment size is : " + +getIncrementSize(cidr));
                            hostsNumber = (long) Math.pow(2, 8 - cidr);
                            hostsNumber -= 2;
                            if (cidr == 8) {
                                netsNumber = (long) Math.pow(2, 0);
                            } else {
                                netsNumber = (long) Math.pow(2, cidr);
                            }
                            for (int i = 0; i < cidr; i++) {
                                octet4[i] = '1';
                            }
                            for (int i = cidr; i < 8; i++) {
                                octet4[i] = '0';
                            }

//                    networkAddr = "" + getIPOctets().get(0) + "." + getIPOctets().get(1) + "." + getIPOctets().get(2)
//                            + "." + getIncrementSize(cidr);
//
//                    int next = getIncrementSize(cidr) + (getIncrementSize(cidr) - 1);
//                    broadcastAddr = "" + getIPOctets().get(0) + "." + getIPOctets().get(1) + "." + getIPOctets().get(2)
//                            + "." + next;
                        } else {
                            // delMe
                        }

                        for (int i = 0; i < 8; i++) {
                            octe1 += octet1[i];
                            octe2 += octet2[i];
                            octe3 += octet3[i];
                            octe4 += octet4[i];
                        }

                        int oct1, oct2, oct3, oct4;
                        oct1 = oct2 = oct3 = oct4 = -1;
                        oct1 = Integer.parseInt(octe1, 2);
                        oct2 = Integer.parseInt(octe2, 2);
                        oct3 = Integer.parseInt(octe3, 2);
                        oct4 = Integer.parseInt(octe4, 2);

                        /* Delete Me */
                        if (cidr == 0) {
                            userWarning("Invalid CIDR", "CIDR can not be a zero.");
                            return;
                        }
                        /* */

                        if (oct1 != -1 && oct2 != -1 && oct3 != -1 && oct4 != -1) {
                            deMaskLbl.setText("" + oct1 + "." + oct2 + "." + oct3 + "." + oct4);
                        }

                        switch (whatOctet) {
                            case 1:

                                if (cidr == 0) {
                                    //helper.msg("Message", "0 not allowed here.", Type.ERROR);
                                    userWarning("Invalid CIDR", "CIDR can not be a zero.");
                                    return;
//                            networkAddr = "0.0.0.0";
//                            broadcastAddr = "0.0.0.0";
//                            netsList.add(new TableManager(1L, networkAddr, "-", "-", broadcastAddr));
//                            hostsNumber = (long) Math.pow(2, (32 - 2));
                                }
                                if (cidr == 8) {
                                    networkAddr = "" + getIPOctets().get(0) + "."
                                            + "0" + "." + "0"
                                            + "." + "0";

                                    firstHost = "" + getIPOctets().get(0) + "."
                                            + "0" + "." + "0"
                                            + "." + "1";

                                    //long o = (block + (n - 1));
                                    lastHost = "" + getIPOctets().get(0) + "."
                                            + "255" + "." + "255"
                                            + "." + "254";
                                    //long bc = (n + incSiz);
                                    broadcastAddr = "" + getIPOctets().get(0) + "."
                                            + "255" + "." + "255"
                                            + "." + "255";

                                    netsList.add(new TableManager(1L, networkAddr, firstHost, lastHost, broadcastAddr));
                                } else {

                                    /* /For test code*/
                                    for (long i = 0; i < netsNumber; i++) {
                                        //for (int n = 0; n < 256; n += incSiz) {
                                        System.out.println("\nCIDR : " + cidr + "\n");
                                        System.out.println("Block Size : " + getIncrementSize(cidr));
                                        long n = incSiz;
                                        long block = n * i;
                                        if (i == 0) {
                                            networkAddr = "" + block + "."
                                                    + "0" + "." + "0"
                                                    + "." + "0";

                                            firstHost = "" + block + "."
                                                    + "0" + "." + "0"
                                                    + "." + "1";

                                            long o = (block + (n - 1));

                                            lastHost = "" + o + "."
                                                    + "255" + "." + "255"
                                                    + "." + "254";
                                            long bc = (n + incSiz);
                                            broadcastAddr = "" + o + "."
                                                    + "255" + "." + "255"
                                                    + "." + "255";
                                        } else {
                                            networkAddr = "" + block + "."
                                                    + "0" + "." + "0"
                                                    + "." + "0";

                                            firstHost = "" + block + "."
                                                    + "0" + "." + "0"
                                                    + "." + "1";

                                            long o = (block + (n - 1));

                                            lastHost = "" + o + "."
                                                    + "255" + "." + "255"
                                                    + "." + "254";
                                            long bc = (n + incSiz);
                                            broadcastAddr = "" + o + "."
                                                    + "255" + "." + "255"
                                                    + "." + "255";
                                        }

                                        long ii = i + 1;

                                        netsList.add(new TableManager(ii, networkAddr, firstHost, lastHost, broadcastAddr));

                                        //fillTable(ii, networkAddr, firstHost, lastHost, broadcastAddr);
                                        System.out.println("----------------- " + incSiz + " -------------------");
                                        System.out.println("Network Number : " + ii);
                                        System.out.println("Network Address : " + networkAddr);
                                        System.out.println("First Host : " + firstHost);
                                        System.out.println("Last Host : " + lastHost);
                                        System.out.println("Broadcast Address : " + broadcastAddr);
                                        //}
                                    }
                                }
                                tbl.getItems().setAll(netsList);
                                netsList.clear();

                                break;
                            case 2:

                                if (cidr == 8) {
                                    networkAddr = "" + getIPOctets().get(0) + "."
                                            + getIPOctets().get(1) + "." + "0"
                                            + "." + "0";

                                    firstHost = "" + getIPOctets().get(0) + "."
                                            + getIPOctets().get(1) + "." + "0"
                                            + "." + "1";

                                    //long o = (block + (n - 1));
                                    lastHost = "" + getIPOctets().get(0) + "."
                                            + getIPOctets().get(1) + "." + "255"
                                            + "." + "254";
                                    //long bc = (n + incSiz);
                                    broadcastAddr = "" + getIPOctets().get(0) + "."
                                            + getIPOctets().get(1) + "." + "255"
                                            + "." + "255";

                                    netsList.add(new TableManager(1L, networkAddr, firstHost, lastHost, broadcastAddr));
                                } else {

                                    /* /For test code*/
                                    for (long i = 0; i < netsNumber; i++) {
                                        //for (int n = 0; n < 256; n += incSiz) {
                                        System.out.println("\nCIDR : " + cidr + "\n");
                                        System.out.println("Block Size : " + getIncrementSize(cidr));
                                        long n = incSiz;
                                        long block = n * i;
                                        if (i == 0) {
                                            networkAddr = "" + getIPOctets().get(0) + "."
                                                    + block + "." + "0"
                                                    + "." + "0";

                                            firstHost = "" + getIPOctets().get(0) + "."
                                                    + block + "." + "0"
                                                    + "." + "1";

                                            long o = (block + (n - 1));

                                            lastHost = "" + getIPOctets().get(0) + "."
                                                    + o + "." + "255"
                                                    + "." + "254";
                                            long bc = (n + incSiz);
                                            broadcastAddr = "" + getIPOctets().get(0) + "."
                                                    + o + "." + "255"
                                                    + "." + "255";
                                        } else {
                                            networkAddr = "" + getIPOctets().get(0) + "."
                                                    + block + "." + "0"
                                                    + "." + "0";

                                            firstHost = "" + getIPOctets().get(0) + "."
                                                    + block + "." + "0"
                                                    + "." + "1";

                                            long o = (block + (n - 1));

                                            lastHost = "" + getIPOctets().get(0) + "."
                                                    + o + "." + "255"
                                                    + "." + "254";
                                            long bc = (n + incSiz);
                                            broadcastAddr = "" + getIPOctets().get(0) + "."
                                                    + o + "." + "255"
                                                    + "." + "255";
                                        }

                                        long ii = i + 1;

                                        netsList.add(new TableManager(ii, networkAddr, firstHost, lastHost, broadcastAddr));

                                        //fillTable(ii, networkAddr, firstHost, lastHost, broadcastAddr);
                                        System.out.println("----------------- " + incSiz + " -------------------");
                                        System.out.println("Network Number : " + ii);
                                        System.out.println("Network Address : " + networkAddr);
                                        System.out.println("First Host : " + firstHost);
                                        System.out.println("Last Host : " + lastHost);
                                        System.out.println("Broadcast Address : " + broadcastAddr);
                                        //}
                                    }
                                }
                                tbl.getItems().setAll(netsList);
                                netsList.clear();

                                break;
                            case 3:

                                /* For test code */
                                if (cidr == 8) {
                                    networkAddr = "" + getIPOctets().get(0) + "."
                                            + getIPOctets().get(1) + "." + getIPOctets().get(2)
                                            + "." + "0";

                                    firstHost = "" + getIPOctets().get(0) + "."
                                            + getIPOctets().get(1) + "." + getIPOctets().get(2)
                                            + "." + "1";

                                    //long o = (block + (n - 1));
                                    lastHost = "" + getIPOctets().get(0) + "."
                                            + getIPOctets().get(1) + "." + getIPOctets().get(2)
                                            + "." + "254";
                                    //long bc = (n + incSiz);
                                    broadcastAddr = "" + getIPOctets().get(0) + "."
                                            + getIPOctets().get(1) + "." + getIPOctets().get(2)
                                            + "." + "255";

                                    netsList.add(new TableManager(1L, networkAddr, firstHost, lastHost, broadcastAddr));
                                } else {

                                    /* /For test code*/
                                    for (long i = 0; i < netsNumber; i++) {
                                        //for (int n = 0; n < 256; n += incSiz) {
                                        System.out.println("\nCIDR : " + cidr + "\n");
                                        System.out.println("Block Size : " + getIncrementSize(cidr));
                                        long n = incSiz;
                                        long block = n * i;
                                        if (i == 0) {
                                            networkAddr = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + block
                                                    + "." + "0";

                                            firstHost = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + block
                                                    + "." + "1";

                                            long o = (block + (n - 1));

                                            lastHost = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + o
                                                    + "." + "254";
                                            long bc = (n + incSiz);
                                            broadcastAddr = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + o
                                                    + "." + "255";
                                        } else {
                                            networkAddr = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + block
                                                    + "." + "0";

                                            firstHost = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + block
                                                    + "." + "1";

                                            long o = (block + (n - 1));

                                            lastHost = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + o
                                                    + "." + "254";
                                            long bc = (n + incSiz);
                                            broadcastAddr = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + o
                                                    + "." + "255";
                                        }

                                        long ii = i + 1;

                                        netsList.add(new TableManager(ii, networkAddr, firstHost, lastHost, broadcastAddr));

                                        //fillTable(ii, networkAddr, firstHost, lastHost, broadcastAddr);
                                        System.out.println("----------------- " + incSiz + " -------------------");
                                        System.out.println("Network Number : " + ii);
                                        System.out.println("Network Address : " + networkAddr);
                                        System.out.println("First Host : " + firstHost);
                                        System.out.println("Last Host : " + lastHost);
                                        System.out.println("Broadcast Address : " + broadcastAddr);
                                        //}
                                    }
                                }
                                tbl.getItems().setAll(netsList);
                                netsList.clear();

                                break;
                            case 4:
                                if (cidr == 8) {
                                    networkAddr = "" + getIPOctets().get(0) + "."
                                            + getIPOctets().get(1) + "." + getIPOctets().get(2)
                                            + "." + getIPOctets().get(3);

                                    broadcastAddr = "" + getIPOctets().get(0) + "."
                                            + getIPOctets().get(1) + "." + getIPOctets().get(2)
                                            + "." + getIPOctets().get(3);

                                    netsList.add(new TableManager(1L, networkAddr, "-", "-", broadcastAddr));
                                } else {

                                    /* /For test code*/
                                    for (long i = 0; i < netsNumber; i++) {
                                        //for (int n = 0; n < 256; n += incSiz) {
                                        System.out.println("\nCIDR : " + cidr + "\n");
                                        System.out.println("Block Size : " + getIncrementSize(cidr));
                                        long n = incSiz;
                                        long block = n * i;
                                        if (i == 0) {
                                            networkAddr = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + getIPOctets().get(2)
                                                    + "." + block;
                                            long fi = block + 1;
                                            firstHost = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + getIPOctets().get(2)
                                                    + "." + fi;

                                            long o = (block + (n - 1));
                                            long la = o - 1;
                                            lastHost = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + getIPOctets().get(2)
                                                    + "." + la;
                                            long bc = (n + incSiz);
                                            broadcastAddr = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + getIPOctets().get(2)
                                                    + "." + o;
                                        } else {
                                            networkAddr = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + getIPOctets().get(2)
                                                    + "." + block;
                                            long fi = block + 1;
                                            firstHost = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + getIPOctets().get(2)
                                                    + "." + fi;

                                            long o = (block + (n - 1));
                                            long la = o - 1;
                                            lastHost = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + getIPOctets().get(2)
                                                    + "." + la;
                                            long bc = (n + incSiz);
                                            broadcastAddr = "" + getIPOctets().get(0) + "."
                                                    + getIPOctets().get(1) + "." + getIPOctets().get(2)
                                                    + "." + o;
                                        }

                                        long ii = i + 1;

                                        netsList.add(new TableManager(ii, networkAddr, firstHost, lastHost, broadcastAddr));

                                        //fillTable(ii, networkAddr, firstHost, lastHost, broadcastAddr);
                                        System.out.println("----------------- " + incSiz + " -------------------");
                                        System.out.println("Network Number : " + ii);
                                        System.out.println("Network Address : " + networkAddr);
                                        System.out.println("First Host : " + firstHost);
                                        System.out.println("Last Host : " + lastHost);
                                        System.out.println("Broadcast Address : " + broadcastAddr);
                                        //}
                                    }
                                }
                                tbl.getItems().setAll(netsList);
                                netsList.clear();
                                break;
                            default:
                                break;

                        }

//                String fiOCT = getIPOctets().get(0);
//                String sOCT = getIPOctets().get(1);
//                String tOCT = getIPOctets().get(2);
//                String foOCT = getIPOctets().get(3);
                        netsNumLbl.setText(netsNumber + "");
                        hostsNumLbl.setText("" + hostsNumber);
//                netPLbl.setText(fiOCT.concat(".").concat(sOCT).concat(".").concat(tOCT).concat(".").concat(foOCT));

                        hRangeLbl.setText("[" + firstHost + " - " + lastHost + "]");
                        netIdLbl.setText(networkAddr);
                        bcIdLbl.setText(broadcastAddr);

//                System.out.println(octe1.concat(".").concat(octe2).concat(".").concat(octe3).concat(".").concat(octe4));
//                System.out.println("\n----------------------------------");
//                System.out.println("The network address of this network is : " + networkAddr);
//                System.out.println("The first host in this network is : " + firstHost);
//                System.out.println("The last host in this network is : " + lastHost);
//                System.out.println("The broadcast address of this network is : " + broadcastAddr);
                    } else {
                        userWarning("Invalid input", "The max value of CIDR is : 32");
                        //System.err.println("The max value of CIDR is : 32");

                    }
                }

            }
        }
    }

    private void va() {
        ip_in.textProperty().addListener((ObservableValue<? extends String> observable,
                String oldValue, String newValue) -> {
            if (!newValue.matches("[0-9.]*")) {
                ip_in.setText(oldValue);
            }
        });
    }

    private void vaCIDR() {
        cidr_in.textProperty().addListener((ObservableValue<? extends String> observable,
                String oldValue, String newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                cidr_in.setText(oldValue);
            }
        });
    }

    @FXML
    private void openSMWin(ActionEvent event) {

        helper.showWindow("/getips/FXMLDocument", WinType.DECORATED, false, "Subnet Mask Generator");

    }

    private int getIncrementSize(int index) {
        int incrSize = -1;
        if (index >= 0 && index <= 8) {
            switch (index - 1) {
                case 0:
                    incrSize = 128;
                    break;
                case 1:
                    incrSize = 64;
                    break;
                case 2:
                    incrSize = 32;
                    break;
                case 3:
                    incrSize = 16;
                    break;
                case 4:
                    incrSize = 8;
                    break;
                case 5:
                    incrSize = 4;
                    break;
                case 6:
                    incrSize = 2;
                    break;
                case 7:
                    incrSize = 1;
                    break;
                default:
                    break;
            }// Reversal
        }

        return incrSize;
    }

    @FXML
    private void OnTopAction(ActionEvent event) {
        if (onTopFlag == false) {
            ((Stage) onTopBox.getScene().getWindow()).setAlwaysOnTop(true);
            onTopFlag = true;
        } else {
            ((Stage) onTopBox.getScene().getWindow()).setAlwaysOnTop(false);
            onTopFlag = false;
        }
    }

    private ArrayList<Integer> getIPOctets() {
        ArrayList<Integer> ipOctets = new ArrayList<>();
        if (!ip_in.getText().isEmpty()) {
            String uip = ip_in.getText();
            if (uip.length() > 15 || (!isItAValidIP(uip))) {
                userWarning("Invalid input", "this is not ip.");
                //System.err.println("This is not ip.");
            } else {

                char dot = '.';

                String oc1 = "";
                String oc2 = "";
                String oc3 = "";
                String oc4 = "";

                int indexOfDot1, indexOfDot2, indexOfDot3;
                indexOfDot1 = indexOfDot2 = indexOfDot3 = -1;
                char[] ch = uip.toCharArray();

                for (int i = 0; i < ch.length; i++) {
                    if (ch[i] == dot) {
                        indexOfDot1 = i;
                        break;
                    }

                }
                for (int n = 0; n < indexOfDot1; n++) {
                    oc1 += ch[n];
                }

                for (int i = indexOfDot1 + 1; i < ch.length; i++) {
                    if (ch[i] == dot) {
                        indexOfDot2 = i;
                        break;
                    }
                }
                for (int n = indexOfDot1 + 1; n < indexOfDot2; n++) {
                    oc2 += ch[n];
                }

                for (int i = indexOfDot2 + 1; i < ch.length; i++) {
                    if (ch[i] == dot) {
                        indexOfDot3 = i;
                        break;
                    }
                }
                for (int n = indexOfDot2 + 1; n < indexOfDot3; n++) {
                    oc3 += ch[n];
                }
                for (int i = indexOfDot3 + 1; i < ch.length; i++) {
                    oc4 += ch[i];
                }

                if (oc1.length() > 3 || oc2.length() > 3 || oc3.length() > 3 || oc4.length() > 3) {
                    userWarning("Invalid input", "this is not ip.");
                    //System.err.println("this is not ip.");

                }
                int oct1 = Integer.parseInt(oc1);
                int oct2 = Integer.parseInt(oc2);
                int oct3 = Integer.parseInt(oc3);
                int oct4 = Integer.parseInt(oc4);
                if (oct1 > 255 || oct2 > 255 || oct3 > 255 || oct4 > 255) {
                    userWarning("Invalid input", "this is not ip.");
                    //System.err.println("this is not ip.");

                }
                if (oct1 >= 224 || oct1 == 127 || oct1 == 0) {
                    userWarning("Invalid input", "this ip not allowed.");
                    //System.err.println("this ip not allowed.");

                }

//                System.out.println("cotet 1 : " + oc1);
//                System.out.println("cotet 2 : " + oc2);
//                System.out.println("cotet 3 : " + oc3);
//                System.out.println("cotet 4 : " + oc4);
                // oct1 in [1 - 126]
                ipOctets.add(oct1);
                ipOctets.add(oct2);
                ipOctets.add(oct3);
                ipOctets.add(oct4);

                return ipOctets;

            } // main if
        } else {
            userWarning("Invalid input", "please enter a valid IP.");
            //System.err.println("please enter a valid IP.");
        }

        return null;
    }

    private void initCols() {
        netNumClm.setCellValueFactory(new PropertyValueFactory<>("id"));
        netIDClm.setCellValueFactory(new PropertyValueFactory<>("netID"));
        fHostClm.setCellValueFactory(new PropertyValueFactory<>("fHost"));
        lHostClm.setCellValueFactory(new PropertyValueFactory<>("lHost"));
        bcIDClm.setCellValueFactory(new PropertyValueFactory<>("bcID"));
    }

    private void fillTable(long num, String netAddr, String fhost, String lhost, String bcID) {
        netsList.add(new TableManager(num, netAddr, fhost, lhost, bcID));
        tbl.getItems().addAll(netsList);
        netsList.clear();
    }

    @FXML
    private void clearTblAction(ActionEvent event) {
        tbl.getItems().clear();
    }

    @FXML
    private void clearAllAction(ActionEvent event) {
        ip_in.clear();
        cidr_in.clear();
        classLbl.setText("");
        deMaskLbl.setText("");
        cidrLbl.setText("");
        netPLbl.setText("");
        hostPLbl.setText("");
        hRangeLbl.setText("");
        netIdLbl.setText("");
        bcIdLbl.setText("");
        netsNumLbl.setText("");
        hostsNumLbl.setText("");
        tbl.getItems().clear();

    }

    @FXML
    private void clearLSAction(ActionEvent event) {
        ip_in.clear();
        cidr_in.clear();
        classLbl.setText("");
        deMaskLbl.setText("");
        cidrLbl.setText("");
        netPLbl.setText("");
        hostPLbl.setText("");
        hRangeLbl.setText("");
        netIdLbl.setText("");
        bcIdLbl.setText("");
        netsNumLbl.setText("");
        hostsNumLbl.setText("");
    }

    private void userWarning(String titleTxt, String warinigTxt) {
        BoxBlur blur = new BoxBlur(2, 2, 3);

        JFXButton okBtn = new JFXButton("Ok");
        okBtn.getStyleClass().add("okBtn");

        /* okBtn Styling*/
//        okBtn.setPrefWidth(40d);
//        okBtn.setPrefWidth(20d);
        /**/
        Label warningLabel = new Label(warinigTxt);
        warningLabel.setStyle("-fx-text-fill : red");
        dlLayout.setHeading(new Label(titleTxt));
        dlLayout.setBody(warningLabel);
        dlLayout.setActions(okBtn);

        dlLayout.setStyle("-fx-background-color : rgb(245, 245, 245)");

        dialog.show();
        okBtn.requestFocus();

        okBtn.setStyle("-fx-background-color : rgb(0, 153, 204)");
        okBtn.setStyle("-fx-background-radius : 30");
        okBtn.setStyle("-fx-border-radius : 30");

        mainPnl.setEffect(blur);

        okBtn.setOnAction((actionEvent) -> {
            dialog.close();
        });

        dialog.setOnDialogClosed((Event) -> {
            mainPnl.setEffect(null);
        });

    }



}
