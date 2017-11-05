package sample;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Controller {

    private TableView<Site> table = new TableView<Site>();

    @FXML
    public PasswordField pfWlanPasswd;
    public TextField tfWlanSSID;
    public PasswordField pfWlanPasswdConfirm;
    public TextField tfDHCPLease;
    public ChoiceBox chBoxDHCP;
    public TextField tfWhiteSite;
    public Label lbl_siteCount;
    public Label lbPasswd;
    public Label lbConfirmPasswd;

    public Button btAdd;

    public TableColumn<Site, String> columnSite;
    public TableColumn<Site, String> columnIpRange;
    // public Button btEdit;

    public CheckBox chbIsIPrange;

    public TableView tbvMainTable;

    CollectionWhiteList whiteList = new CollectionWhiteList();
    Map<Enum,String> textFields = new HashMap<>();


    boolean isEdited = false;
    int editedIndex;

    @FXML
    public void initialize(){

        tbvMainTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        columnSite.setCellValueFactory(new PropertyValueFactory<Site, String>("siteName"));
        columnIpRange.setCellValueFactory(new PropertyValueFactory<Site, String>("ipRange"));

        whiteList.getSiteList().addListener(new ListChangeListener<Site>() {
            @Override
            public void onChanged(Change<? extends Site> c) {
                updateSiteCount();
            }
        });

        /*ObservableList<Character> timeOption = FXCollections.observableArrayList();
        timeOption.add('m');
        timeOption.add('h');
        timeOption.add('d');

        chBoxDHCP.setItems(timeOption);
        */


        ChoiceBox<Character> chBox = chBoxDHCP;
        chBoxDHCP.setCache(true);
        chBox.setItems( FXCollections.observableArrayList('m','h','d'));
        chBox.getSelectionModel().selectFirst();

        whiteList.setTestData();
        tbvMainTable.setItems(whiteList.getSiteList());

        //GenerateScript.setSSID(tfWlanSSID.getText());
    }

    public void generateScript(ActionEvent actionEvent) {
        textFields.put(TextFieldsEnum.SSID,tfWlanSSID.getText());
        textFields.put(TextFieldsEnum.DHCP_LEASE,tfDHCPLease.getText());


        GenerateScript generate = new GenerateScript(whiteList,textFields);

        generate.saveToDirectory();


     /*   String passwd = pfWlanPasswd.getText();
        String confirmPasswd = pfWlanPasswdConfirm.getText();
        if(passwd.length() < 8 ){
            String tmpPasswdLable = lbPasswd.getText();
            lbPasswd.setText(tmpPasswdLable + " (Password should be at least 8 characters)");
        }
        if(!passwd.equals(confirmPasswd)){
            lbConfirmPasswd.setText( "Confirm WLAN Password (does not match)");
            lbConfirmPasswd.setTextFill(Color.RED);
        }
        */
        
    }

    public void addSite(ActionEvent actionEvent) {
        String siteName = tfWhiteSite.getText();
        boolean isIpRange = chbIsIPrange.isSelected();

        Site site = new Site(siteName, isIpRange ? "Yes" : "No");

        if(!isEdited){
            if(siteName != null && !siteName.equals("")) {
                whiteList.add(site);
            }
        }else {
            whiteList.getSiteList().set(editedIndex,site);
            tfWhiteSite.clear();
            chbIsIPrange.setSelected(false);
            isEdited = false;
            btAdd.setText("Add");
        }
    }

    public void deleteSite(ActionEvent actionEvent) {
        ObservableList<Site> selectedItems = tbvMainTable.getSelectionModel().getSelectedItems();
       // ObservableList<Site> allItems = whiteList.getSiteList();
        //selectedItems.forEach(allItems::remove);

        whiteList.getSiteList().removeAll(selectedItems);
    }

    public void editSite(ActionEvent actionEvent) {
        Site selectedItem = (Site)tbvMainTable.getSelectionModel().getSelectedItem();

        if(selectedItem != null){
            tfWhiteSite.setText(selectedItem.getSiteName());
            chbIsIPrange.setSelected(selectedItem.ipRange.equals("Yes") ? true : false);

            isEdited = true;
            editedIndex = whiteList.getSiteList().indexOf(selectedItem);
            btAdd.setText("Write");
        }
    }

    public void updateSiteCount(){
        lbl_siteCount.setText("Added sites: " + whiteList.getSiteList().size());
    }

    public void onSSIDchanged(KeyEvent keyEvent) {
        //GenerateScript.setSSID(tfWlanSSID.getText());
        textFields.put(TextFieldsEnum.SSID,tfWlanSSID.getText());
    }

    public void onTextFieldDataChanged(KeyEvent keyEvent){
        textFields.put(TextFieldsEnum.SSID,tfWlanSSID.getText());
        String dhcpLease = tfDHCPLease.getText();
        if(dhcpLease == null || dhcpLease.equals("")){
            dhcpLease = tfDHCPLease.getPromptText();
        }
        textFields.put(TextFieldsEnum.DHCP_LEASE,dhcpLease);
    }

    public void onUpload(ActionEvent actionEvent) {

    //myStage.sizeToScene();
       // myStage.setMinHeight(600);
        FTPConnectAndLogin.upload();
    }


    static Stage myStage;

    public static void setStage(Stage stage){
        myStage = stage;
    }


}
