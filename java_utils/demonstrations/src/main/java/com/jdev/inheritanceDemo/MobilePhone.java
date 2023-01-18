package com.jdev.inheritanceDemo;

import com.jdev.console.ConsoleUtils;
import com.jdev.inheritanceDemo.a.Phone;

public class MobilePhone implements Phone, com.jdev.inheritanceDemo.b.Phone {

    public void mobile(){
        ConsoleUtils.printToConsole("mobile");
    }

    @Override
    public void call() {
        ConsoleUtils.printToConsole("Mobile phone");
    }

    @Override
    public void sms() {
        ConsoleUtils.printToConsole("mobile sms");
    }
}
