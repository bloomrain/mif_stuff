package lt.vu.mif.jate.tasks.task03.mt.common;

import java.math.BigInteger;

public interface ServerFunction {
    
    public static final BigInteger MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
    public static final BigInteger MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
    
    Long exec(Long op1, Long op2) throws Exception;
    
}
