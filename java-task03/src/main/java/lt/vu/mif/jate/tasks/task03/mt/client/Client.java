package lt.vu.mif.jate.tasks.task03.mt.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

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
    private BlockingQueue<Message> messagesToSend = new LinkedBlockingQueue<Message>();
    private ConcurrentMap<Long, Message> messagesToGet = new ConcurrentHashMap<Long, Message>();
    private final Socket socket;
    private final InetSocketAddress url;
    private static List<InetSocketAddress> connections = new LinkedList<InetSocketAddress>();
    private final boolean isConnectionUsed;
    
    public Client(InetSocketAddress addr) throws IOException {
        this.socket = new Socket(addr.getAddress(), addr.getPort());
        this.url = addr;
        
        isConnectionUsed = connections.contains(url);
        if (!isConnectionUsed) {
            connections.add(url);
        }

        (new Sender(this.socket, messagesToSend, messagesToGet)).start();
        (new Receiver(this.socket.getInputStream(), messagesToGet)).start();
        (new KeepAlive(messagesToSend)).start();
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
        if (isConnectionUsed) {
            throw new IOException("Conenction is used");
        }
        
        Message m = new Message(func, op1, op2);
        messagesToSend.add(m);
        
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
    
    @Override
    public void close() throws Exception {
        socket.close();
        if (isConnectionUsed) {
            connections.remove(url);
        }
    }
}
