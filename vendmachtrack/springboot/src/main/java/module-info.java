module vendmachtrack.springboot {
    requires spring.beans;
    requires spring.boot;
    requires spring.context;
    requires spring.boot.autoconfigure;
    requires spring.core;

    // opens springboot to spring.beans, spring.context, spring.web, spring.core;

    opens springboot to spring.core;

}