/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lt.vu.mif.jate.tasks.task03.mt.client;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gege
 */
public class KeepAlive extends Thread {
    private Client client;

    KeepAlive(Client client) {
        this.client = client;
    }
    
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                //System.out.println("Ping...");
                Thread.sleep(200);
                client.getMessagesToBeSend().add(new Message(ServerFunction.Ping, 0L, 0L));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                interrupt();
                break;
            }
        }
        
    }
}
