/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lt.vu.mif.jate.tasks.task03.mt.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import lt.vu.mif.jate.tasks.task03.mt.common.Response;

/**
 *
 * @author gege
 */
public class Receiver extends Thread {
    private final InputStream source;
    private final ConcurrentMap<Long, Message> messages;
    
    Receiver(Client client) throws IOException {
        this.source = client.getSocket().getInputStream();
        this.messages = client.getMessagesToBeReceived();
    }
    
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                if(!messages.isEmpty()) {
                    receive();
                }                
            } catch (Exception ex) {
                ex.printStackTrace();
                interrupt();
                break;
            }
        }
    }
    
    
    
    private void receive() throws IOException, InterruptedException {
        ByteBuffer header = ByteBuffer.allocate(4);
        source.read(header.array());
        header.rewind();
        
        // Read body length from the header and receive body
        ByteBuffer body = ByteBuffer.allocate(header.getInt());
        source.read(body.array());
        body.rewind();
        
        //
        // Disassemble body and return
        //
        
        // Response code
        Response resp = Response.fromInt(body.getInt());
        
        // Correlation ID
        long corr = body.getLong();
        Message m = messages.get(corr);
        // Check if it is my message
        switch (resp) {

            case Success:
                // Return result
                m.setResult(body.getLong());
            case Error:
                // Define byte array
                byte[] mb = new byte[body.remaining()];
                // Fill array
                body.get(mb);
                // Throw exception
                m.setError(new String(mb));
        }
        messages.remove(corr);
    }
}
