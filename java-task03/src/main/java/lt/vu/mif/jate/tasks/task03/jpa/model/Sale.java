/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lt.vu.mif.jate.tasks.task03.jpa.model;

import java.math.BigDecimal;
import java.util.Calendar;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author gege
 */
public class Sale extends Entry {
    @Getter @Setter BigDecimal cost;
    @Getter @Setter Integer units;
    @Getter @Setter Calendar time;
    @Getter @Setter Customer customer;
    @Getter @Setter Product product;
}
