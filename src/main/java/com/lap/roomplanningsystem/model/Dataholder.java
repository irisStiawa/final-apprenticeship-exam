package com.lap.roomplanningsystem.model;

import com.lap.roomplanningsystem.repository.JDBC.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Dataholder {

    protected ObservableList<User> users;
    protected ObservableList<Location> locations;
    protected ObservableList<Course> courses;
    protected ObservableList<Program> programs;
    protected ObservableList<Equipment> equipments;
    protected ObservableList<Event> events;
    protected ObservableList<Room> rooms;
    protected ObservableList<RoomEquipment> roomEquipments;
    protected ObservableList<Contract> contracts;


    public static UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();
    public static RoomRepositoryJDBC roomRepositoryJDBC = new RoomRepositoryJDBC();
    public static EventRepositoryJDBC eventRepositoryJDBC = new EventRepositoryJDBC();
    public static CourseRepositoryJDBC courseRepositoryJDBC = new CourseRepositoryJDBC();
    public static ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();
    public static EquipmentRepositoryJDBC equipmentRepositoryJDBC = new EquipmentRepositoryJDBC();
    public static LocationRepositoryJDBC locationRepositoryJDBC = new LocationRepositoryJDBC();
    public static RoomEquipmentRepositoryJDBC roomEquipmentRepositoryJDBC = new RoomEquipmentRepositoryJDBC();
    public static ContractRepositoryJDBC contractRepositoryJDBC = new ContractRepositoryJDBC();



    public Dataholder() throws Exception {
        this.users = userRepositoryJDBC.readAll().orElse(null);
        this.locations = locationRepositoryJDBC.readAll().orElse(null);
        this.courses = courseRepositoryJDBC.readAll().orElse(null);
        this.programs = programRepositoryJDBC.readAll().orElse(null);
        this.equipments = equipmentRepositoryJDBC.readAll().orElse(null);
        this.events = eventRepositoryJDBC.readAll().orElse(null);
        this.rooms = roomRepositoryJDBC.readAll().orElse(null);
        this.roomEquipments = roomEquipmentRepositoryJDBC.readAll().orElse(null);
        this.contracts = contractRepositoryJDBC.readAll().orElse(null);

    }




    public ObservableList<User> getCoaches() {
        List<Predicate<User>> predicateList = new ArrayList<>();
        FilteredList<User> coaches = new FilteredList<>(users);

        predicateList.add(User::isTrainer);
        predicateList.add(User::isActive);

        Predicate<User> predicate = predicateList.stream().reduce(r -> true, Predicate::and);
        coaches.setPredicate(predicate);

        return FXCollections.observableArrayList(coaches);
    }





    public ObservableList<Event> getEvents() {
        return events;
    }

    public void setEvents(ObservableList<Event> events) {
        this.events = events;
    }

    public ObservableList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ObservableList<Room> rooms) {
        this.rooms = rooms;
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    public ObservableList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ObservableList<Location> locations) {
        this.locations = locations;
    }

    public ObservableList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ObservableList<Course> courses) {
        this.courses = courses;
    }

    public ObservableList<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(ObservableList<Program> programs) {
        this.programs = programs;
    }

    public ObservableList<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(ObservableList<Equipment> equipments) {
        this.equipments = equipments;
    }

    public ObservableList<RoomEquipment> getRoomEquipments() {
        return roomEquipments;
    }

    public void setRoomEquipments(ObservableList<RoomEquipment> roomEquipments) {
        this.roomEquipments = roomEquipments;
    }

    public void addProgram(Program program){
        this.programs.add(program);
    }


    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addEquipment(Equipment equipment) {
        this.equipments.add(equipment);
    }

    public void addRoomEquipment(RoomEquipment roomEquipment) {
        this.roomEquipments.add(roomEquipment);
    }

    public void addLocation(Location location) {
        this.locations.add(location);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public void addEvents(ObservableList<Event> events) {
        this.events.addAll(events);
    }

    public void updateRoom(int index, Room room) {
        this.rooms.set(index, room);
    }

    public void updateUser(int index, User user) {
        this.users.set(index, user);
    }

    public void updateEquipment(int index, Equipment equipment) {
        this.equipments.set(index,equipment);
    }

    public void updateRoomEquipment(int index, RoomEquipment roomEquipment) {
        this.roomEquipments.set(index,roomEquipment);
    }

    public void updateLocation(int index, Location location) {
        this.locations.set(index,location);
    }

    public void updateCourse(int index, Course course) {
        this.courses.set(index,course);
    }

    public void updateEvent(int index, Event event) {
        this.events.set(index,event);
    }


    public void updateProgram(int index, Program program) {
        this.programs.set(index,program);
    }

    public void deleteRoomEquipment(RoomEquipment e) {
        this.roomEquipments.remove(e);
    }

    public void deleteEvent(Event e) {
        this.events.remove(e);
    }

    public void deleteLocation(Location l) {
        this.locations.remove(l);
    }

    public void deleteProgram(Program p) {
        this.programs.remove(p);
    }

    public void deleteEquipment(Equipment e) {
        this.equipments.remove(e);
    }

    public void deleteRoom(Room r) {
        this.rooms.remove(r);
    }

    public void deleteCourse(Course c) {
        this.courses.remove(c);
    }

    public void updateUsers() throws Exception {
        this.users = userRepositoryJDBC.readAll().orElse(null);
    }

    public void updateCourses() throws Exception {
        this.courses = courseRepositoryJDBC.readAll().orElse(null);
    }

    public void updateLocations() throws Exception {
        this.locations = locationRepositoryJDBC.readAll().orElse(null);
    }

    public void updatePrograms() throws Exception {
        this.programs = programRepositoryJDBC.readAll().orElse(null);
    }

    public void updateEquipments() throws Exception {
        this.equipments = equipmentRepositoryJDBC.readAll().orElse(null);
    }

    public void updateRooms() throws Exception {
        this.rooms = roomRepositoryJDBC.readAll().orElse(null);
    }

    public void updateEvents() throws Exception {
        this.events = eventRepositoryJDBC.readAll().orElse(null);
    }

    public void updateRoomEquipments() throws Exception {
        this.roomEquipments = roomEquipmentRepositoryJDBC.readAll().orElse(null);
    }

    public void addContract(Contract contract) {
        this.contracts.add(contract);
    }

    public ObservableList<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(ObservableList<Contract> contracts) {
        this.contracts = contracts;
    }
}
