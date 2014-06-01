/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lt.vu.mif.jate.tasks.task03.mt.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * @author gege
 */
public class Sender extends Thread {
    private Socket socket;
    private BlockingQueue<Message> messages;
    private ConcurrentMap<Long, Message> receiving;

    Sender(Socket socket, BlockingQueue<Message> messages, ConcurrentMap<Long, Message> receiving) {
        this.socket = socket;
        this.messages = messages;
        this.receiving = receiving;
    }
    
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                send(messages.take());
            } catch (Exception ex) {
                interrupt();
                break;
            }
        }
    }
    
    private void send(Message m) throws ServerFunctionException, IOException {
        ByteBuffer bytes = m.toBytes();
        ByteBuffer sizeb = Message.toBytes(bytes.capacity());
        OutputStream destination = socket.getOutputStream();
        receiving.put(m.getCorrelation(), m);
        // Write both header and body
        destination.write(sizeb.array());
        destination.write(bytes.array());        
    }
}
