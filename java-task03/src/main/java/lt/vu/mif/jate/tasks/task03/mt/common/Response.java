package lt.vu.mif.jate.tasks.task03.mt.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Response codes
 * @author valdo
 */

@Getter
@RequiredArgsConstructor
public enum Response {
    
    PONG(0),
    Success(1),
    Error(2);
    
    private final int code;
    
    public static Response fromInt(int code) {
        for (Response r: values()) {
            if (r.getCode() == code) {
                return r;
            }
        }
        throw new IllegalArgumentException("Code " + code + " not defined?");
    }
    
}