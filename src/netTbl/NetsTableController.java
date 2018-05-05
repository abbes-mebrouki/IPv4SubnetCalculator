package netTbl;

import TemporaryName.TableManager;
import TemporaryName.WindowController;
import TemporaryName.WindowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class NetsTableController implements Initializable {

    @FXML
    private AnchorPane mainPnl;
    @FXML
    private TableView<TableManager> tbl;
    @FXML
    private TableColumn<TableManager, Integer> idClm;
    @FXML
    private TableColumn<TableManager, String> netClm;
    @FXML
    private TableColumn<TableManager, String> fhostClm;
    @FXML
    private TableColumn<TableManager, String> lhostClm;
    @FXML
    private TableColumn<TableManager, String> bcClm;

    ObservableList<TableManager> netsList = FXCollections.observableArrayList();

    SimpleIntegerProperty id;
    SimpleStringProperty netID, fHost, lHost, bcID;

    public NetsTableController(int id, String netID, String fHost, String lHost, String bcID) {
        this.id = new SimpleIntegerProperty(id);
        this.netID = new SimpleStringProperty(netID);
        this.fHost = new SimpleStringProperty(fHost);
        this.lHost = new SimpleStringProperty(lHost);
        this.bcID = new SimpleStringProperty(bcID);
    }

    public int getId() {
        return id.get();
    }
    public void setId(int value){
        this.id.set(value);
    }

    public String getNetID() {
        return netID.get();
    }
    public void setNetID(String value){
        this.netID.set(value);
    }
    
    public String getFHost() {
        return fHost.get();
    }
    public void setFHost(String value){
        this.fHost.set(value);
    }

    public String getLHost() {
        return lHost.get();
    }
    
    public void setLHost(String value){
        this.lHost.set(value);
    }

    public String getBcID() {
        return bcID.get();
    }
    public void setBcID(String value){
        this.bcID.set(value);
    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCols();
        fillTable();
    }

    private void initCols() {
        idClm.setCellValueFactory(new PropertyValueFactory<>("id"));
        idClm.setCellValueFactory(new PropertyValueFactory<>("netID"));
        idClm.setCellValueFactory(new PropertyValueFactory<>("fHost"));
        idClm.setCellValueFactory(new PropertyValueFactory<>("lHost"));
        idClm.setCellValueFactory(new PropertyValueFactory<>("bcID"));
    }

    WindowController winC = new WindowController();

    private void fillTable() {
        netsList.add(new TableManager(getId(), getNetID(), getFHost(), getLHost(), getBcID()));
        tbl.getItems().setAll(netsList);
        netsList.clear();

        System.out.println("************* I tryed to fill the table.");

        System.out.println("id : " + getId());
        System.out.println("netID : " + getNetID());
        System.out.println("fHost : " + getFHost());
        System.out.println("lHost : " + getLHost());
        System.out.println("bcID : " + getBcID());
    }

}
