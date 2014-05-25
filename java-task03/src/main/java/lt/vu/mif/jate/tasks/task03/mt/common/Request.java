package lt.vu.mif.jate.tasks.task03.mt.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Request codes
 * @author valdo
 */

@Getter
@RequiredArgsConstructor
public enum Request {
    
    Ping(0),
    Addition(1),
    Substraction(2),
    Multiplication(3),
    Division(4),
    Function01(5),
    Function02(6);
    
    private final int code;
    
    public static Request fromInt(int code) {
        for (Request r: values()) {
            if (r.getCode() == code) {
                return r;
            }
        }
        throw new IllegalArgumentException("Code " + code + " not defined?");
    }
    
}
