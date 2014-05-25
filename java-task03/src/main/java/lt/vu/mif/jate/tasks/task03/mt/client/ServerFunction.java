package lt.vu.mif.jate.tasks.task03.mt.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ServerFunction class
 * @author valdo
 */
@RequiredArgsConstructor
public enum ServerFunction {

    Ping(0),
    Addition(1),
    Substraction(2),
    Multiplication(3),
    Division(4),
    Function01(5),
    Function02(6);

    @Getter
    private final int code;

}
