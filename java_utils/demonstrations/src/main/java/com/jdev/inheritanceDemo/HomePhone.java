package com.jdev.inheritanceDemo;

import com.jdev.console.ConsoleUtils;
import com.jdev.inheritanceDemo.a.Phone;

public class HomePhone implements Phone, com.jdev.inheritanceDemo.b.Phone {

    public void home(){
        ConsoleUtils.printToConsole("Home");
    }

    @Override
    public void call() {
        ConsoleUtils.printToConsole("Home phone");
    }

    @Override
    public void sms() {
        ConsoleUtils.printToConsole("Home sms");
    }
}
