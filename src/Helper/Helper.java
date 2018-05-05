package Helper;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;



public class Helper {
    
    
    Connection con = null;
    PreparedStatement pst = null;
    
    
    public void commit() {
        try {
            pst = con.prepareStatement("commit");
            pst.execute();
        } catch (SQLException ex) {
            //msg("Commit Error", ex.getMessage(), Type.ERROR);
            System.err.println("Commit Error : " + ex.getMessage());
        }
    }
    
    
    public enum WinType {
        DECORATED, TRANSPARENT, UNDECORATED, UNIFIED, UTILITY;
    }

    public void showWindow(String filelocation, WinType type, boolean resizable) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(filelocation + ".fxml"));
            Parent ro = (Parent) loader.load();
            Stage stage = new Stage();
            if (type != null) {
                switch (type) {
                    case DECORATED:
                        stage.initStyle(StageStyle.DECORATED);
                        break;
                    case TRANSPARENT:
                        stage.initStyle(StageStyle.TRANSPARENT);
                        break;
                    case UNDECORATED:
                        stage.initStyle(StageStyle.UNDECORATED);
                        break;
                    case UNIFIED:
                        stage.initStyle(StageStyle.UNIFIED);
                        break;
                    case UTILITY:
                        stage.initStyle(StageStyle.UTILITY);
                        break;
                }
            }
            stage.setResizable(resizable);
            stage.setScene(new Scene(ro));
            stage.show();
        } catch (IOException ex) {
            msg("File not found", ex.getMessage(), Type.ERROR);
            System.out.println(ex.getMessage());
        }
    }
    
    public void showWindow(String filelocation, WinType type, boolean resizable, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(filelocation + ".fxml"));
            Parent ro = (Parent) loader.load();
            Stage stage = new Stage();
            if (type != null) {
                switch (type) {
                    case DECORATED:
                        stage.initStyle(StageStyle.DECORATED);
                        break;
                    case TRANSPARENT:
                        stage.initStyle(StageStyle.TRANSPARENT);
                        break;
                    case UNDECORATED:
                        stage.initStyle(StageStyle.UNDECORATED);
                        break;
                    case UNIFIED:
                        stage.initStyle(StageStyle.UNIFIED);
                        break;
                    case UTILITY:
                        stage.initStyle(StageStyle.UTILITY);
                        break;
                }
            }
            stage.setTitle(title);
            stage.setResizable(resizable);
            stage.setScene(new Scene(ro));
            stage.show();
        } catch (IOException ex) {
            msg("File not found -> showWindow", ex.getMessage(), Type.ERROR);
            System.err.println(ex.getMessage());
        }
    }
    
    public void showWindow(String filelocation, WinType type, boolean resizable, String title, boolean fullScreen) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(filelocation + ".fxml"));
            Parent ro = (Parent) loader.load();
            Stage stage = new Stage();
            if (type != null) {
                switch (type) {
                    case DECORATED:
                        stage.initStyle(StageStyle.DECORATED);
                        break;
                    case TRANSPARENT:
                        stage.initStyle(StageStyle.TRANSPARENT);
                        break;
                    case UNDECORATED:
                        stage.initStyle(StageStyle.UNDECORATED);
                        break;
                    case UNIFIED:
                        stage.initStyle(StageStyle.UNIFIED);
                        break;
                    case UTILITY:
                        stage.initStyle(StageStyle.UTILITY);
                        break;
                }
            }
            stage.setTitle(title);
            stage.setResizable(resizable);
            stage.setScene(new Scene(ro));
            stage.setFullScreen(fullScreen);
            stage.show();
        } catch (IOException ex) {
            msg("File not found -> showWindow", ex.getMessage(), Type.ERROR);
            System.err.println(ex.getMessage());
        }
    }
    
    public enum Type {
        CONFIRMATION, ERROR, INFORMATION, NONE, WARNING
    }

    public void msg(String Title, String txt, Type type) {
        Alert alert = null;
        if (null != type) {
            switch (type) {
                case CONFIRMATION:
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    break;
                case ERROR:
                    alert = new Alert(Alert.AlertType.ERROR);
                    break;
                case INFORMATION:
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    break;
                case NONE:
                    alert = new Alert(Alert.AlertType.NONE);
                    break;
                case WARNING:
                    alert = new Alert(Alert.AlertType.WARNING);
                    break;
                default:
                    break;
            }
        }
        alert.setTitle(Title);
        alert.setContentText(txt);
        alert.show();
    }
    
    
    public enum Position {
        TOP_RIGHT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT
    }
    
    public enum NotiType{
        confirm, information, warning, error, normal
    }
    
    public void showNoti(String title, double duration, String text, Position pos, NotiType nt) {
        Notifications n = Notifications.create()
                .title(title)
                .hideAfter(Duration.millis(duration))
                .text(text);
        if (pos != null) {
            switch (pos) {
                case TOP_RIGHT:
                    n.position(Pos.TOP_RIGHT);
                    break;
                case TOP_LEFT:
                    n.position(Pos.TOP_LEFT);
                    break;
                case BOTTOM_RIGHT:
                    n.position(Pos.BOTTOM_RIGHT);
                    break;
                case BOTTOM_LEFT:
                    n.position(Pos.BOTTOM_LEFT);
                    break;
            }
        }
        if (nt != null) {
            switch (nt) {
                case confirm:
                    n.showConfirm();
                    break;
                case warning:
                    n.showWarning();
                    break;
                case error:
                    n.showError();
                    break;
                case information:
                    n.showInformation();
                    break;
            }
        }
    }
    
    public enum movingType {
        Up, Down, Right, Left
    }

    public void MoveItem(double time, Node node, movingType type, int val) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(time), node);
        if (null != type) {
            switch (type) {
                case Up:
                case Down:
                    tt.setToY(val);
                    break;
                case Right:
                case Left:
                    tt.setToX(val);
                    break;
                default:
                    break;
            }
        }
        tt.play();
    }
    
    public void showDots(JFXButton btn) {
        String dot = ".";
        String txt = btn.getText();
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                msg("show points", e.getMessage(), Type.ERROR);
            }
            txt += dot;
            btn.setText(txt);
        }
    }
    
    public void showDots(MenuItem mItem) {
        String dot = ".";
        String txt = mItem.getText();
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                msg("show points", e.getMessage(), Type.ERROR);
            }
            txt += dot;
            mItem.setText(txt);
        }
    }
    
}
