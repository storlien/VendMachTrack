module vendmachtrack.springboot {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.core;
    requires spring.web;
    requires spring.context;
    requires spring.beans;

    requires vendmachtrack.core;
    requires vendmachtrack.jsonio;

    // Necessary 'opens' for Spring Boot framework to work.
    opens vendmachtrack.springboot to spring.core, spring.beans, spring.context;
    opens vendmachtrack.springboot.service to spring.core, org.mockito;
    opens vendmachtrack.springboot.exception to spring.beans;

    // This 'opens' is weak encapsulation, but we haven't managed to find which module that is dependent on the 'controller' package.
    // The error log says it's an unnamed module. This will be worked more on in a later release.
    opens vendmachtrack.springboot.controller;

    //Opening the 'springboot.repository' to the spring.core module is necessary for testing purposes.
    // The reason why is that the Spring framework, especially the 'ReflectionTestUtils' class, needs
    // to gain access to the classes and their private fields within the 'springboot.repository' package.
    // This access is required for setting private fields during testing, such as mocking dependencies.
    opens vendmachtrack.springboot.repository to org.mockito, spring.core;
}