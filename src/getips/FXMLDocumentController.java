package getips;

import Helper.Helper;
import Helper.Helper.WinType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField hnTxt;
    @FXML
    private Label hTxt;
    @FXML
    private Label showHV;

    long hNumber, n;
    double d;
    @FXML
    private Label oce1;
    @FXML
    private Label oce2;
    @FXML
    private Label oce3;
    @FXML
    private Label oce4;
    @FXML
    private TextField txt;
    @FXML
    private Label avhsTxt;
    @FXML
    private Label avhosShow;
    @FXML
    private Label unuhosTxt;
    @FXML
    private Label unuhosShow;
    @FXML
    private Label errorsLbl;

    Helper helper = new Helper();
    @FXML
    private Label cidrTxt;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        n = -1;
        cidrTxt.setText("");
        avhsTxt.setVisible(false);
        unuhosTxt.setVisible(false);
        va();
    }

    @FXML
    private void getHAction(ActionEvent event) {
        functionName();
    }

    @FXML
    private void getResultAction(ActionEvent event) {
        functionName();
    }

    private void functionName() {
        long cidr;
        if (isItLong(hnTxt.getText())) {
            hNumber = Long.parseLong(hnTxt.getText());

            for (int i = 1;; i++) {
                //&& (((Math.pow(2, i) -2)) - hNumber) < ((Math.pow(2, i + 1) -2))) 
                long availableHosts = (long) (Math.pow(2, i) - 2);
                if (availableHosts >= hNumber) {
                    //System.out.println(i);
                    avhsTxt.setVisible(true);
                    avhsTxt.setText("Available hosts : ");

                    avhosShow.setText(availableHosts + "");
                    long unusedHosts = availableHosts - hNumber;

                    unuhosTxt.setVisible(true);
                    unuhosTxt.setText("Unused hosts : ");
                    unuhosShow.setText(unusedHosts + "");
                    n = i;
                    break;
                }
            }
            if (hNumber != 0 && hNumber < 4294967294L) {
                hTxt.setText(" n = ");
                showHV.setText(n + "");

            }
            cidr = 32 - n;
            // octet1 from left to right
            char[] octet1 = new char[8];
            char[] octet2 = new char[8];
            char[] octet3 = new char[8];
            char[] octet4 = new char[8];
            int oc1 = -1;
            int oc2 = -1;
            int oc3 = -1;
            int oc4 = -1;

            String oct1 = "";
            String oct2 = "";
            String oct3 = "";
            String oct4 = "";

            if (hNumber == 0) {
                clearAll();
                errorsLbl.setStyle("-fx-text-fill : red");
                errorsLbl.setText("Pleas enter a valid number.");
                System.out.println("Pleas enter a valid number.");
            } else if (hNumber > 4294967294L) {
                clearAll();
                errorsLbl.setStyle("-fx-text-fill : red");
                errorsLbl.setText("This number ( " + hNumber + " ) is not allowed.");
                System.out.println("This number ( " + hNumber + " ) is not allowed.");
            } else {// enumeration

                if (n != -1 && n != 1) {
                    long o = 8 - n;

                    if (n > 1 && n < 9) {  // Octet 4
                        /* n in [1, 8] */
                        for (int i = 0; i < 8; i++) {
                            octet1[i] = '1';
                            octet2[i] = '1';
                            octet3[i] = '1';
                        }

                        for (int i = 0; i < o; i++) {
                            octet4[i] = '1';
                        }
                        for (int i = (int) o; i < 8; i++) {
                            octet4[i] = '0';
                        }

                        for (int i = 0; i < octet1.length; i++) {
                            oct1 += octet1[i];
                            oct2 += octet2[i];
                            oct3 += octet3[i];
                            oct4 += octet4[i];
                        }
                    }

                    if (n > 8 && n < 17) { // Octet 3
                        /* n in [9, 16] */
                        for (int i = 0; i < 8; i++) {
                            octet1[i] = '1';
                            octet2[i] = '1';
                            octet4[i] = '0';
                        }

                        long u = 16 - n;
                        for (int i = 0; i < u; i++) {
                            octet3[i] = '1';
                        }
                        for (int i = (int) u; i < 8; i++) {
                            octet3[i] = '0';
                        }

                        for (int i = 0; i < octet2.length; i++) {
                            oct1 += octet1[i];
                            oct2 += octet2[i];
                            oct3 += octet3[i];
                            oct4 += octet4[i];
                        }
                    }

                    if (n > 16 && n < 25) { // Octet 2
                        /* n in [17, 24] */
                        for (int i = 0; i < 8; i++) {
                            octet1[i] = '1';
                            octet3[i] = '0';
                            octet4[i] = '0';
                        }

                        long u = 24 - n;
                        for (int i = 0; i < u; i++) {
                            octet2[i] = '1';
                        }
                        for (int i = (int) u; i < 8; i++) {
                            octet2[i] = '0';
                        }

                        for (int i = 0; i < octet3.length; i++) {
                            oct1 += octet1[i];
                            oct2 += octet2[i];
                            oct3 += octet3[i];
                            oct4 += octet4[i];
                        }
                    }

                    if (n > 24 && n < 33) { // Octet 1
                        /* n in [25, 32] */
                        for (int i = 0; i < 8; i++) {
                            octet2[i] = '0';
                            octet3[i] = '0';
                            octet4[i] = '0';
                        }

                        long u = 32 - n;
                        for (int i = 0; (int) i < u; i++) {
                            octet1[i] = '1';
                        }
                        for (int i = (int) u; i < 8; i++) {
                            octet1[i] = '0';
                        }

                        for (int i = 0; i < octet4.length; i++) {
                            oct1 += octet1[i];
                            oct2 += octet2[i];
                            oct3 += octet3[i];
                            oct4 += octet4[i];
                        }
                    }

                    // showResult
                    oc1 = Integer.parseInt(oct1, 2);
                    oc2 = Integer.parseInt(oct2, 2);
                    oc3 = Integer.parseInt(oct3, 2);
                    oc4 = Integer.parseInt(oct4, 2);
                    oce1.setText(oc1 + "");
                    oce2.setText("." + oc2);
                    oce3.setText("." + oc3);
                    oce4.setText("." + oc4);
                    String octts = oct1.concat(".").concat(oct2).concat(".")
                            .concat(oct3).concat(".").concat(oct4);
                    //octets.setText(octts);
                    txt.setText(octts);
                    cidrTxt.setText("CIDR : " + cidr);
                    errorsLbl.setText("");

//            System.out.println("The fourth octet value : [" + oct1 + "]");
//            System.out.println("The fourth octet value : [" + oct2 + "]");
//            System.out.println("The fourth octet value : [" + oct3 + "]");
//            System.out.println("The fourth octet value : [" + oct4 + "]");
                } else {

                }
            }
        } else {
            errorsLbl.setStyle("-fx-text-fill : red");
            errorsLbl.setText("Not valid");
        }
    }

    private void clearAll() {
        oce1.setText("");
        oce2.setText("");
        oce3.setText("");
        oce4.setText("");
        avhsTxt.setText("");
        avhosShow.setText("");
        unuhosTxt.setText("");
        unuhosShow.setText("");
        txt.setText("");
        hTxt.setText("");
        showHV.setText("");
        cidrTxt.setText("");
    }

    private boolean isItLong(String txt) {
        try {
            long number = Long.parseLong(txt);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void va() {
        hnTxt.textProperty().addListener((ObservableValue<? extends String> observable, 
                String oldValue, String newValue) -> {
            if (!newValue.matches("[0-9]*")) {//\\d{0,7}([\\.]\\d{0,4})?
                hnTxt.setText(oldValue);
            }
        });
    }
}
