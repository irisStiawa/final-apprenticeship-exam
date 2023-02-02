module com.lap.roomplaningsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;
    requires org.apache.commons.lang3;
    requires com.calendarfx.view;
    requires spring.security.crypto;


    opens com.lap.roomplanningsystem to javafx.fxml;
    exports com.lap.roomplanningsystem;
    exports com.lap.roomplanningsystem.controller;
    exports com.lap.roomplanningsystem.controller.tableController;
    opens com.lap.roomplanningsystem.controller to javafx.fxml;
    opens com.lap.roomplanningsystem.controller.tableController to javafx.fxml;
    exports com.lap.roomplanningsystem.controller.detailController;
    opens com.lap.roomplanningsystem.controller.detailController to javafx.fxml;
    exports com.lap.roomplanningsystem.controller.updateController;
    opens com.lap.roomplanningsystem.controller.updateController to javafx.fxml;
    exports com.lap.roomplanningsystem.controller.deleteController;
    opens com.lap.roomplanningsystem.controller.deleteController to javafx.fxml;
    exports com.lap.roomplanningsystem.controller.addController;
    opens com.lap.roomplanningsystem.controller.addController to javafx.fxml;
}