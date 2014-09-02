package com.mocha.axon.sample.main;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mocha.axon.sample.CreateToDoItemCommand;
import com.mocha.axon.sample.MarkCompletedCommand;

public class ToDoItemSpringRunner {

    private CommandGateway commandGateway;
    
    public ToDoItemSpringRunner(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
 
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        ToDoItemSpringRunner runner = new ToDoItemSpringRunner(applicationContext.getBean(CommandGateway.class));
        runner.run();
    }
 
    private void run() {
        final String itemId = UUID.randomUUID().toString();
        commandGateway.send(new CreateToDoItemCommand(itemId, "Need to do this"));
        commandGateway.send(new MarkCompletedCommand(itemId));
    }

}
