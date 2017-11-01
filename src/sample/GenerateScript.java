package sample;

import javafx.stage.DirectoryChooser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class GenerateScript {

    private String SSID;
    private String DHCP_LEASE;

    private CollectionWhiteList whiteList = new CollectionWhiteList();
    private Map<Enum,String> textFields = new HashMap<>();


    public GenerateScript(CollectionWhiteList whiteList, Map<Enum, String> textFields) {
        this.whiteList = whiteList;
        this.textFields = textFields;
    }


    public  void saveToDirectory() {
      /*  FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose location To Save Script");
        File selectedFile = null;
        while(selectedFile == null){
            selectedFile = chooser.showSaveDialog(null);
        }*/

       assignValues();

       DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose location To Save Script");
        File selectedFile = null;
        while(selectedFile == null){
            selectedFile = chooser.showDialog(null);
        }

        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter(selectedFile+"/MKHotspotConfigScript.rsc");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String fileName = "sources/configFile";
        List<String> list = new ArrayList<>();
        try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
            Scanner scanner = new Scanner(new FileReader(fileName));
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                StringBuilder buffer = new StringBuilder();
                if(line.contains("#SSID")){
                    line = line.replace("#SSID",SSID);
                }
                 if(line.contains("#DHCP_LEASE")){
                    line = line.replace("#DHCP_LEASE",DHCP_LEASE);
                }
                if(line.startsWith("#addWGSite#")) {

                    for (Site site : whiteList.getSiteList()) {
                        if (site.getIpRange().equals("No"))
                            buffer.append("add dst-host=").append(site.getSiteName()).append("\n");
                    }
                }
                if(line.startsWith("#addWGIP#")){
                    for(Site site : whiteList.getSiteList()){
                        if(site.getIpRange().equals("Yes")){
                            //#add action=accept disabled=no !dst-address !dst-address-list dst-host=winfospot.com !dst-port !protocol !src-address !src-address-list
                            buffer.append("add action=accept disabled=no !dst-address !dst-address-list dst-host=")
                                    .append(site.getSiteName())
                                    .append(" !dst-port !protocol !src-address !src-address-list\n");
                        }
                    }
                }
                if(buffer != null && !buffer.toString().isEmpty() && buffer.toString() != ""){
                    outFile.println(buffer.toString());
                    buffer = new StringBuilder();
                }else {
                    outFile.println(line);
                }
            }
            outFile.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        outFile.close();
    }

    private void assignValues() {
        for(Map.Entry<Enum, String> entry : textFields.entrySet()){
            if(entry.getKey() == TextFieldsEnum.SSID)
                SSID = entry.getValue();
            if(entry.getKey() == TextFieldsEnum.DHCP_LEASE)
                DHCP_LEASE = entry.getValue();
        }
    }

    /*public  void setSSID(String ssid){
        SSID = ssid;
    }
*/
    public void readSource() throws IOException {

        String fileName = "sources/configFile";
        List<String> list = new ArrayList<>();

    }
}
