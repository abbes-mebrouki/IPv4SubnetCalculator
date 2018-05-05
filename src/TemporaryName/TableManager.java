package TemporaryName;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableManager {
    private final SimpleLongProperty id;
    private final SimpleStringProperty netID, fHost, lHost, bcID;
    public TableManager(long id, String netID, String fHost, String lHost, String bcID){
        this.id = new SimpleLongProperty(id);
        this.netID = new SimpleStringProperty(netID);
        this.fHost = new SimpleStringProperty(fHost);
        this.lHost = new SimpleStringProperty(lHost);
        this.bcID = new SimpleStringProperty(bcID);
    }
    
    public long getID(){
        return id.get();
    }
    public String getNetID(){
        return netID.get();
    }
    public String getFHost(){
        return fHost.get();
    }
    public String getLHost(){
        return lHost.get();
    }
    public String getBcID(){
        return bcID.get();
    }
}
