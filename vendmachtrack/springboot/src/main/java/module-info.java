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
    opens vendmachtrack.springboot.service to spring.core, org.mockito, spring.beans;


    // These 'opens' are weak encapsulation, but we haven't managed to find which modules that are dependent on the 'controller' and 'exception' packages.
    // The error log says it's an unnamed module and our indications are that it is a Jackson module. This will be worked more on in a later release.
    opens vendmachtrack.springboot.controller;
    opens vendmachtrack.springboot.exception;

    //Opening the 'springboot.repository' to the spring.core module is necessary for testing purposes.
    // The reason why is that the Spring framework, especially the 'ReflectionTestUtils' class, needs
    // to gain access to the classes and their private fields within the 'springboot.repository' package.
    // This access is required for setting private fields during testing, such as mocking dependencies.
    opens vendmachtrack.springboot.repository to org.mockito, spring.core, spring.beans;

}