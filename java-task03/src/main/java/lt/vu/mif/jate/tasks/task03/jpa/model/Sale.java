/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lt.vu.mif.jate.tasks.task03.jpa.model;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author gege
 */
@Entity
@Table(name="sales")
public class Sale extends Entry {
    @Getter
    @Setter 
    @Id 
    @Column(name = "sal_id", nullable = false) 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  
    
    @Getter
    @Setter
    @Column(name= "sal_cost", nullable = false )
    private BigDecimal cost;
    
    @Getter
    @Setter
    @Column(name = "sal_units", nullable = false) 
    private Integer units = 0;
    
    @Getter 
    @Setter 
    @Column(name = "sal_time", nullable = false) 
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar time;
    
    @Getter
    @Setter    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sal_cus_id")
    private Customer customer;
    
    @Getter
    @Setter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sal_pro_id")
    private Product product;
}
