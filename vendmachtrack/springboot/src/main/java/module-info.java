module vendmachtrack.springboot {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.core;
    requires spring.web;
    requires vendmachtrack.core;
    requires spring.context;
    requires spring.beans;
    requires vendmachtrack.jsonio;

    opens springboot to spring.core;
    opens springboot.controller to spring.core;
    opens springboot.service to spring.core;

}