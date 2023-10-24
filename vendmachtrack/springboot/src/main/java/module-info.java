module vendmachtrack.springboot {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.core;
    requires spring.web;
    requires spring.context;
    requires spring.beans;

    requires vendmachtrack.core;
    requires vendmachtrack.jsonio;

    opens springboot to spring.core;
    opens springboot.controller to spring.core;
    opens springboot.service to spring.core;
    opens springboot.repository to org.mockito, spring.core;
}