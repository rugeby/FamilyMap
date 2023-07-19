package dataAccess;

import javax.xml.crypto.Data;

/**
 * just a normal exception class
 */


public class DataAccessException extends Exception {
    DataAccessException(){super();};
    DataAccessException(String message) {
        super(message);
    }

}
