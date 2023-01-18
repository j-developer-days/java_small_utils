package com.jdev.inheritanceDemo;

import com.jdev.console.ConsoleUtils;
import com.jdev.inheritanceDemo.b.Phone;

public class Run {

    public static void main(String[] args) {
        withInterface();

        ConsoleUtils.printDelimiter('=');

        withAbstractClass();
    }

    private static void withAbstractClass(){
        AbstractPhone abstractPhone = new WorkPhone();
        abstractPhone.call();
        abstractPhone.fax();

        com.jdev.inheritanceDemo.a.Phone phoneA = abstractPhone;
        phoneA.call();

        Phone phoneB = new WorkPhone();
        phoneB.sms();
        phoneB.call();

        WorkPhone workPhone = new WorkPhone();
        workPhone.call();
        workPhone.sms();
        workPhone.fax();
    }

    private static void withInterface() {
        Phone phoneB = new HomePhone();
        phoneB.call();
        ConsoleUtils.printToConsole(phoneB instanceof HomePhone);
        ((HomePhone) phoneB).home();
        phoneB.sms();

        ConsoleUtils.printDelimiter('*');

        com.jdev.inheritanceDemo.a.Phone phoneA = new HomePhone();
        phoneA.call();
        ConsoleUtils.printToConsole(phoneA instanceof HomePhone);
        ((HomePhone) phoneA).home();

        ConsoleUtils.printDelimiter('*');

        Phone phoneB2 = new MobilePhone();
        phoneB2.call();
        ConsoleUtils.printToConsole(phoneB2 instanceof MobilePhone);
        ((MobilePhone) phoneB2).mobile();

        ConsoleUtils.printDelimiter('-');

        MobilePhone mobilePhone = new MobilePhone();
        mobilePhone.mobile();
        mobilePhone.sms();
        mobilePhone.call();
    }

}
