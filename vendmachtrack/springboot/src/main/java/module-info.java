module vendmachtrack.springboot {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.core;
    requires spring.web;
    requires spring.context;
    requires spring.beans;

    requires vendmachtrack.core;
    requires vendmachtrack.jsonio;

    opens vendmachtrack.springboot to spring.core, spring.beans, spring.context;
    exports vendmachtrack.springboot to spring.beans, spring.context, vendmachtrack.springboot.exception;
    exports vendmachtrack.springboot.exception to spring.beans;
    exports vendmachtrack.springboot.controller to spring.beans, spring.context, spring.web;
    opens vendmachtrack.springboot.controller; //to spring.core, org.mockito, vendmachtrack.springboot.SpringBootApplication, vendmachtrack.springboot.service.MachineTrackerService, 
    //vendmachtrack.springboot.exception, vendmachtrack.core, vendmachtrack.jsonio, vendmachtrack.springboot,spring.web;
    opens vendmachtrack.springboot.service to spring.core;
    //Opening the 'springboot.repository' to the spring.core module is necessary for testing purposes.
    // The reason why is because the Spring framework especialy the'ReflectionTestUtils' class needs
    // to gain access to the classes and their private fields within the 'springboot.repository' package.
    // This access is required for setting private fields during testing, such as mocking dependencies.
    opens vendmachtrack.springboot.repository to org.mockito, spring.core;
}