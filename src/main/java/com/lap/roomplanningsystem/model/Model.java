package com.lap.roomplanningsystem.model;

import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;


public class Model {
    private StringProperty pathToView = new SimpleStringProperty();
    private StringProperty authorization = new SimpleStringProperty("standard");

    private User user = null;
    private Dataholder dataholder;


    private ObjectProperty<Event> selectedEventProperty = new SimpleObjectProperty<>();
    private ObjectProperty<Room> selectedRoomProperty = new SimpleObjectProperty<>();
    private ObjectProperty<Course> selectedCourseProperty = new SimpleObjectProperty<>();
    private ObjectProperty<Program> selectedProgramProperty = new SimpleObjectProperty<>();
    private ObjectProperty<Location> selectedLocationProperty = new SimpleObjectProperty<>();
    private ObjectProperty<Equipment> selectedEquipmentProperty = new SimpleObjectProperty<>();
    private ObjectProperty<RoomEquipment> selectedRoomEquipmentProperty = new SimpleObjectProperty<>();
    private ObjectProperty<User> selectedUserProperty = new SimpleObjectProperty<>();
    private ObjectProperty<Contract> selectedContractProperty = new SimpleObjectProperty<>();

    private boolean detailView;
    private boolean showInCalendar;
    private boolean addEntry;
    private boolean isLogout;

    private CalendarView calendarView;

    private ObjectProperty<Event> newEvent = new SimpleObjectProperty<>();
    private Entry newEntry;
    private BooleanProperty removeEntry = new SimpleBooleanProperty();

    private ObservableList<Event> bookedEvents = FXCollections.observableArrayList();
    private ObservableList<Event> notBookedEvents = FXCollections.observableArrayList();

    private ObservableList<Room> requestResult;
    private ObservableList<User> requestResultCoaches;
    private ObjectProperty<Result> selectedResultProperty = new SimpleObjectProperty<>();

    private StringProperty hashedPassword=  new SimpleStringProperty();



    public Model()  {

        try {
            this.dataholder = new Dataholder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean validateLogin(String username, String password) throws SQLException {

        Optional<User> validUser = Dataholder.userRepositoryJDBC.checkUsernamePw(username, password);

        if(validUser.isPresent()){
            setAuthorization(validUser.get().getAuthorization());
            setUser(validUser.get());
            return true;
        }
        return false;
    }

    public ObservableList<Event> getBookedEvents() {
        return bookedEvents;
    }

    public void setBookedEvents(ObservableList<Event> bookedEvents) {
        this.bookedEvents = bookedEvents;
    }

    public void setShowInCalendar(boolean showInCalendar) {
        this.showInCalendar = showInCalendar;
    }

    public boolean isShowInCalendar() {
        return showInCalendar;
    }

    public boolean isDetailView() {
        return detailView;
    }

    public void setDetailView(boolean detailView) {
        this.detailView = detailView;
    }

    public String getHashedPassword() {
        return hashedPassword.get();
    }

    public StringProperty hashedPasswordProperty() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword.set(hashedPassword);
    }

    public Event getSelectedEventProperty() {
        return selectedEventProperty.get();
    }

    public void setSelectedEventProperty(Event selectedEventProperty) {
        this.selectedEventProperty.set(selectedEventProperty);
    }

    public Result getSelectedResultProperty() {
        return selectedResultProperty.get();
    }

    public ObjectProperty<Result> selectedResultProperty() {
        return selectedResultProperty;
    }

    public void setSelectedResultProperty(Result selectedResultProperty) {
        this.selectedResultProperty.set(selectedResultProperty);
    }

    public String getPathToView() {
        return pathToView.get();
    }

    public StringProperty pathToViewProperty() {
        return pathToView;
    }

    public void setPathToView(String pathToView) {
        this.pathToView.set(pathToView);
    }

    public String getAuthorization() {
        return authorization.get();
    }

    public StringProperty authorizationProperty() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization.set(authorization);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dataholder getDataholder() {
        return dataholder;
    }

    public void setDataholder(Dataholder dataholder) {
        this.dataholder = dataholder;
    }

    public ObjectProperty<Event> selectedEventProperty() {
        return selectedEventProperty;
    }

    public Room getSelectedRoomProperty() {
        return selectedRoomProperty.get();
    }

    public ObjectProperty<Room> selectedRoomProperty() {
        return selectedRoomProperty;
    }

    public void setSelectedRoomProperty(Room selectedRoomProperty) {
        this.selectedRoomProperty.set(selectedRoomProperty);
    }

    public Course getSelectedCourseProperty() {
        return selectedCourseProperty.get();
    }

    public ObjectProperty<Course> selectedCourseProperty() {
        return selectedCourseProperty;
    }

    public void setSelectedCourseProperty(Course selectedCourseProperty) {
        this.selectedCourseProperty.set(selectedCourseProperty);
    }

    public Program getSelectedProgramProperty() {
        return selectedProgramProperty.get();
    }

    public ObjectProperty<Program> selectedProgramProperty() {
        return selectedProgramProperty;
    }

    public void setSelectedProgramProperty(Program selectedProgramProperty) {
        this.selectedProgramProperty.set(selectedProgramProperty);
    }

    public Location getSelectedLocationProperty() {
        return selectedLocationProperty.get();
    }

    public ObjectProperty<Location> selectedLocationProperty() {
        return selectedLocationProperty;
    }

    public void setSelectedLocationProperty(Location selectedLocationProperty) {
        this.selectedLocationProperty.set(selectedLocationProperty);
    }

    public Equipment getSelectedEquipmentProperty() {
        return selectedEquipmentProperty.get();
    }

    public ObjectProperty<Equipment> selectedEquipmentProperty() {
        return selectedEquipmentProperty;
    }

    public void setSelectedEquipmentProperty(Equipment selectedEquipmentProperty) {
        this.selectedEquipmentProperty.set(selectedEquipmentProperty);
    }

    public RoomEquipment getSelectedRoomEquipmentProperty() {
        return selectedRoomEquipmentProperty.get();
    }

    public ObjectProperty<RoomEquipment> selectedRoomEquipmentProperty() {
        return selectedRoomEquipmentProperty;
    }

    public void setSelectedRoomEquipmentProperty(RoomEquipment selectedRoomEquipmentProperty) {
        this.selectedRoomEquipmentProperty.set(selectedRoomEquipmentProperty);
    }

    public User getSelectedUserProperty() {
        return selectedUserProperty.get();
    }

    public ObjectProperty<User> selectedUserProperty() {
        return selectedUserProperty;
    }

    public void setSelectedUserProperty(User selectedUserProperty) {
        this.selectedUserProperty.set(selectedUserProperty);
    }

    public ObservableList<Room> getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(ObservableList<Room> requestResult) {
        this.requestResult = requestResult;
    }

    public ObservableList<Event> getNotBookedEvents() {
        return notBookedEvents;
    }

    public void setNotBookedEvents(ObservableList<Event> notBookedEvents) {
        this.notBookedEvents = notBookedEvents;
    }

    public boolean isAddEntry() {
        return addEntry;
    }

    public void setAddEntry(boolean addEntry) {
        this.addEntry = addEntry;
    }

    public Event getNewEvent() {
        return newEvent.get();
    }

    public ObjectProperty<Event> newEventProperty() {
        return newEvent;
    }

    public void setNewEvent(Event newEvent) {
        this.newEvent.set(newEvent);
    }

    public boolean isLogout() {
        return isLogout;
    }

    public void setLogout(boolean logout) {
        isLogout = logout;
    }

    public ObservableList<User> getRequestResultCoaches() {
        return requestResultCoaches;
    }

    public void setRequestResultCoaches(ObservableList<User> requestResultCoaches) {
        this.requestResultCoaches = requestResultCoaches;
    }

    public CalendarView getCalendarView() {
        return calendarView;
    }

    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
    }

    public Entry getNewEntry() {
        return newEntry;
    }

    public void setNewEntry(Entry newEntry) {
        this.newEntry = newEntry;
    }

    public boolean isRemoveEntry() {
        return removeEntry.get();
    }

    public BooleanProperty removeEntryProperty() {
        return removeEntry;
    }

    public void setRemoveEntry(boolean removeEntry) {
        this.removeEntry.set(removeEntry);
    }

    public Contract getSelectedContractProperty() {
        return selectedContractProperty.get();
    }

    public ObjectProperty<Contract> selectedContractPropertyProperty() {
        return selectedContractProperty;
    }

    public void setSelectedContractProperty(Contract selectedContractProperty) {
        this.selectedContractProperty.set(selectedContractProperty);
    }
}
