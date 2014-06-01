package lt.vu.mif.jate.tasks.task03.mt.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
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

    @Getter private final Socket socket;
    @Getter private BlockingQueue<Message> messagesToBeSend = new LinkedBlockingQueue<Message>();
    @Getter private ConcurrentMap<Long, Message> messagesToBeReceived = new ConcurrentHashMap<Long, Message>();
    
    private static HashMap<InetSocketAddress, Integer> connections = new HashMap<InetSocketAddress, Integer>();
    private InetSocketAddress url;
    
    public Client(InetSocketAddress addr) throws IOException {
        this.socket = new Socket(addr.getAddress(), addr.getPort());
        this.url = addr;
        (new Sender(this)).start();
        (new Receiver(this)).start();
        (new KeepAlive(this)).start();
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
        if (getConnectionsNumber() > 1) {
            throw new IOException("Socket is in usep");
        }
        
        Message m = new Message(func, op1, op2);
        messagesToBeSend.add(m);
        while(m.isPending()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                // do nothing
            }
        }
        
        if (m.isSuccess()) {
            return m.getResult();
        }
        
        throw new ServerFunctionException(m.getError());
        
    }
    
    public int getConnectionsNumber() {
        int connectionsNumber = 0;
        if(connections.get(url) != null) {
            connectionsNumber = connections.get(url);
        };
        return connectionsNumber;
    }
    
    @Override
    public void close() throws Exception {
        socket.close();
        
        int connectionsNumber = getConnectionsNumber();
        connections.remove(url);
        
        if (connectionsNumber > 1) {
            connections.put(url, connectionsNumber - 1);
        }
    }
}
