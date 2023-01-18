package com.jdev.inheritanceDemo;

import com.jdev.console.ConsoleUtils;
import com.jdev.inheritanceDemo.a.Phone;

public abstract class AbstractPhone implements Phone {

    public void fax(){
        ConsoleUtils.printToConsole("abstract fax");
    }

}
