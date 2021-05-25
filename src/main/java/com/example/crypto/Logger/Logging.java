package com.example.crypto.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logging {

    public static Logger getLogger(Class<?> cls){
        return LogManager.getLogger(cls);
    }
}
