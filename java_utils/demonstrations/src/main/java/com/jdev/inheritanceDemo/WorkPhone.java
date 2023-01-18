package com.jdev.inheritanceDemo;

import com.jdev.console.ConsoleUtils;
import com.jdev.inheritanceDemo.b.Phone;

public class WorkPhone extends AbstractPhone implements Phone {
    @Override
    public void call() {
        ConsoleUtils.printToConsole("work phone");
    }

    @Override
    public void sms() {
        ConsoleUtils.printToConsole("work sms");
    }
}
