module vendmachtrack.springboot {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.core;
    //    requires spring.context;
    //    requires spring.beans;

    // opens springboot to spring.beans, spring.context, spring.web, spring.core;
    opens springboot to spring.core;

}