package lt.vu.mif.jate.tasks.task03.mt.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import lt.vu.mif.jate.tasks.task03.mt.common.Response;

/**
 * Client object: implements Java API for server-side arithmetic operations protocol.
 * 
 * Request (client -> server) message format:
 * 
 * - message length (int, 4 bytes): length of the remaining message
 * - request code (int, 4 bytes): message (operation) type, @see lt.vu.mif.jate.tasks.task03.mt.common.Request
 * - correlation id (long, 8 bytes): id to correlate request-response pair
 * - operand1 (long, 8 bytes, optional): first operand
 * - operand2 (long, 8 bytes, optional): second operand
 * 
 * Response (server -> client) message format:
 * 
 * - message length (int, 4 bytes): length of the remaining message
 * - response code (int, 4 bytes): message type, @see lt.vu.mif.jate.tasks.task03.mt.common.Response
 * - correlation id (long, 8 bytes): id to correlate request-response pair
 * - result (long, 8 bytes, optional): operation result (if code == Success)
 * - message (String, optional): error message (if code == Error)
 * 
 * @author valdo
 */
public class Client implements AutoCloseable {

    private final Socket socket;
    
    public Client(InetSocketAddress addr) throws IOException {
        this.socket = new Socket(addr.getAddress(), addr.getPort());
    }
    
    public Long addition(Long op1, Long op2) throws ServerFunctionException, IOException {
        return exec(ServerFunction.Addition, op1, op2);
    }
    
    public Long substraction(Long op1, Long op2) throws ServerFunctionException, IOException {
        return exec(ServerFunction.Substraction, op1, op2);
    }
    
    public Long multiplication(Long op1, Long op2) throws ServerFunctionException, IOException {
        return exec(ServerFunction.Multiplication, op1, op2);
    }
    
    public Long division(Long op1, Long op2) throws ServerFunctionException, IOException {
        return exec(ServerFunction.Division, op1, op2);
    }
    
    public Long function01(Long op1, Long op2) throws ServerFunctionException, IOException {
        return exec(ServerFunction.Function01, op1, op2);
    }

    public Long function02(Long op1, Long op2) throws ServerFunctionException, IOException {
        return exec(ServerFunction.Function02, op1, op2);
    }

    private Long exec(ServerFunction func, Long op1, Long op2) throws ServerFunctionException, IOException {
        
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        
        //
        // Sending message
        //
        
        // Building a message and a header
        Message m = new Message(func, op1, op2);
        ByteBuffer bytes = m.toBytes();
        ByteBuffer sizeb = Message.toBytes(bytes.capacity());
        
        // Write both header and body
        out.write(sizeb.array());
        out.write(bytes.array());
        
        //
        // Receiving message back
        //
        
        // Receive header
        ByteBuffer header = ByteBuffer.allocate(4);
        in.read(header.array());
        header.rewind();
        
        // Read body length from the header and receive body
        ByteBuffer body = ByteBuffer.allocate(header.getInt());
        in.read(body.array());
        body.rewind();
        
        //
        // Disassemble body and return
        //
        
        // Response code
        Response resp = Response.fromInt(body.getInt());
        
        // Correlation ID
        long corr = body.getLong();
        
        // Check if it is my message
        if (corr == m.getCorrelation()) {
            switch (resp) {
                
                case Success:
                    
                    // Return result
                    return body.getLong();
                    
                case Error:

                    // Define byte array
                    byte[] mb = new byte[body.remaining()];
                    
                    // Fill array
                    body.get(mb);
                    
                    // Throw exception
                    throw new ServerFunctionException(new String(mb));
                    
            }
        } else {
            throw new IOException("Wrong correlation id received: expected " + m.getCorrelation() + ", got " + corr);
        }
        
        return 0L;
    }
    
    @Override
    public void close() throws Exception {
        socket.close();
    }

}