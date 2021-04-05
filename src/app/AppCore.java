package app;

import database.Database;
import database.DatabaseImplementation;
import database.MSSQLrepository;
import database.settings.Settings;
import database.settings.SettingsImplementation;
import gui.LogIN;
import gui.table.TableModel;
import observer.Notification;
import observer.enums.NotificationCode;
import observer.implementation.PublisherImplementation;
import resource.implementation.InformationResource;
import utils.Constants;


public class AppCore extends PublisherImplementation {

    private Database database;
    private Settings settings;
    private TableModel tableModel;

    public AppCore() {
        this.settings = initSettings();
        this.database = new DatabaseImplementation(new MSSQLrepository(this.settings));
        tableModel = new TableModel();
    }

    private Settings initSettings() {
        Settings settingsImplementation = new SettingsImplementation();
        settingsImplementation.addParameter("mssql_ip", LogIN.ip());
        settingsImplementation.addParameter("mssql_database", LogIN.database());
        settingsImplementation.addParameter("mssql_username", LogIN.username());
        settingsImplementation.addParameter("mssql_password", LogIN.password());
        return settingsImplementation;
    }


    public void loadResource(){
        InformationResource ir = (InformationResource) this.database.loadResource();
        if (ir == null) this.notifySubscribers(new Notification(NotificationCode.RESOURCE_NOT_LOADED,ir));
        else this.notifySubscribers(new Notification(NotificationCode.RESOURCE_LOADED,ir));
    }

    public void readDataFromTable(String fromTable){

        tableModel.setRows(this.database.readDataFromTable(fromTable));

        //Zasto ova linija moze da ostane zakomentarisana?
        //this.notifySubscribers(new Notification(NotificationCode.DATA_UPDATED, this.getTableModel()));
    }


    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }
    
    public Database getDatabase() {
		return database;
	}


}
