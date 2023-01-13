package com.jdev.inheritance.example1;

import com.jdev.console.ConsoleUtils;
import com.jdev.inheritance.example1.a.AbstractPhone;
import com.jdev.inheritance.example1.b.Phone;

public class HomePhone extends AbstractPhone implements Phone {
    @Override
    public void call() {
        ConsoleUtils.printToConsole(this.getClass().getCanonicalName() + ".call method");
    }

    @Override
    public void sms() {
        ConsoleUtils.printToConsole(this.getClass().getCanonicalName() + ".sms method");
    }
}
