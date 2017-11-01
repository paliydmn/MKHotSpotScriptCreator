package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CollectionWhiteList implements WhiteList {

    private ObservableList<Site> siteList = FXCollections.observableArrayList();

    @Override
    public void add(Site site) {
        siteList.add(site);
    }

    @Override
    public void edit(Site site) {

    }

    @Override
    public void delete(Site site) {
        siteList.remove(site);
    }

    public  void setTestData(){

        Site site;
        for(int i =0; i <=10; i++){
            site = new Site("google " + i, "No");
            siteList.add(site);
        }
    }

    public ObservableList<Site> getSiteList() {
        return siteList;
    }
}
