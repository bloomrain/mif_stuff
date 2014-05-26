/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lt.vu.mif.jate.tasks.task03.jpa.model;

import java.util.LinkedList;
import lombok.Getter;

/**
 *
 * @author gege
 */
public abstract class SaleEntry extends Entry {
    @Getter LinkedList<Sale> sales = new LinkedList<Sale>();
}
