package io;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FicheroLogger {
    private static final Logger LOGGER = Logger.getLogger(FicheroLogger.class.getName());
    private static Handler fileHandler;

    static {
        try {
            Handler fileHandler = new FileHandler("registroPrograma.log", false);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void cerrarFileHandler() {
        if (fileHandler != null) {
            fileHandler.close();
        }
    }
    
 

   
}


