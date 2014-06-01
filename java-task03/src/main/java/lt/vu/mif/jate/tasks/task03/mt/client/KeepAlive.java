/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lt.vu.mif.jate.tasks.task03.mt.client;

import java.util.concurrent.BlockingQueue;

/**
 *
 * @author gege
 */
public class KeepAlive extends Thread {
    private final BlockingQueue<Message> messages;

    KeepAlive(BlockingQueue<Message> messages) {
        this.messages = messages;
    }
    
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                //System.out.println("Ping...");
                messages.add(new Message(ServerFunction.Ping, 0L, 0L));
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                interrupt();
                break;
            }
        }
        
    }
}
