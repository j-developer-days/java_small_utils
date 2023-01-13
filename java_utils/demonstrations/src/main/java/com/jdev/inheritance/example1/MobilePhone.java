package com.jdev.inheritance.example1;

import com.jdev.console.ConsoleUtils;
import com.jdev.inheritance.example1.a.Phone;

public class MobilePhone implements Phone, com.jdev.inheritance.example1.b.Phone {
    @Override
    public void call() {
        ConsoleUtils.printToConsole("this is mobile phone - call");
    }

    @Override
    public void sms() {
        ConsoleUtils.printToConsole("mobile phone sms");
    }
}
