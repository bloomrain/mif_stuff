/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lt.vu.mif.jate.tasks.task03.jpa.model;

import java.io.Serializable;

/**
 *
 * @author gege
 */
public abstract class SaleEntry extends Entry implements Serializable {
    public String getBrand() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public String getName() {
        throw new UnsupportedOperationException("Not implemented."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getCountry() {
        throw new UnsupportedOperationException("Not implemented."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getCity() {
        throw new UnsupportedOperationException("Not implemented."); //To change body of generated methods, choose Tools | Templates.
    }
}
