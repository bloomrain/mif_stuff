/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lt.vu.mif.jate.tasks.task03.jpa.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author gege
 */
@Entity
public class Product extends SaleEntry {
    @Getter
    @Setter
    @Id
    @Column(name = "pro_id", nullable = false) 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Getter
    @Setter
    @Column(name = "pro_name", nullable = false, length = 255) 
    private String name;
    
    @Getter
    @Setter
    @Column(name = "pro_brand", nullable = false, length = 255) 
    private String brand;
    
    @Getter
    @Setter
    @OneToMany(mappedBy="product")
    private List<Sale> sales = new LinkedList<>();
}
