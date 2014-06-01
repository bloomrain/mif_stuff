/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lt.vu.mif.jate.tasks.task03.mt.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author gege
 */
public class Sender extends Thread {
    private OutputStream destination;
    private BlockingQueue<Message> messages;
    private Client client;
    
    Sender(Client client) throws IOException {
        this.client = client;
        this.destination = client.getSocket().getOutputStream();
        this.messages = client.getMessagesToBeSend();        
    }
    
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                send(messages.take());
            } catch (Exception ex) {
                ex.printStackTrace();
                interrupt();
                break;
            }
        }
    }
    
    private void send(Message m) throws ServerFunctionException, IOException {
        ByteBuffer bytes = m.toBytes();
        ByteBuffer sizeb = Message.toBytes(bytes.capacity());

        client.getMessagesToBeReceived().put(m.getCorrelation(), m);
        // Write both header and body
        destination.write(sizeb.array());
        destination.write(bytes.array());        
    }
}
