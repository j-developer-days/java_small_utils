package com.jdev.inheritance.example1;

import com.jdev.console.ConsoleUtils;
import com.jdev.inheritance.example1.a.AbstractPhone;
import com.jdev.inheritance.example1.a.Phone;

public class Run {

    public static void main(String[] args) {
        interfaceDemo();
        ConsoleUtils.printDelimiter('_');
        abstractClassDemo();
    }

    private static void abstractClassDemo() {
        HomePhone homePhone = new HomePhone();
        homePhone.call();
        homePhone.sms();

        ConsoleUtils.printDelimiter('-');
        AbstractPhone abstractPhone = new HomePhone();
        abstractPhone.call();
        abstractPhone.sms();

        ConsoleUtils.printDelimiter('-');
        Phone phoneA = new HomePhone();
        phoneA.call();
        phoneA.sms();

        ConsoleUtils.printDelimiter('-');
        com.jdev.inheritance.example1.b.Phone phoneB = new HomePhone();
        phoneB.call();
    }

    private static void interfaceDemo() {
        MobilePhone mobilePhone = new MobilePhone();
        mobilePhone.call();
        mobilePhone.sms();

        ConsoleUtils.printDelimiter('!');

        Phone phoneA = new MobilePhone();
        phoneA.call();
        phoneA.sms();

        ConsoleUtils.printDelimiter('!');

        com.jdev.inheritance.example1.b.Phone phoneB = new MobilePhone();
        phoneB.call();
    }

}
