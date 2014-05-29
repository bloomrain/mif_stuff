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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Customer extends SaleEntry {// extends SaleEntry {
    @Getter 
    @Setter 
    @Id 
    @Column(name = "cus_id", nullable = false) 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = 0L;
    
    @Getter
    @Setter
    @Column(name="cus_fname", nullable = false, length = 128)
    private String firstName;
    
    @Getter
    @Setter
    @Column(name="cus_lname", nullable = false, length = 255)
    private String lastName;
    
    
    @Getter
    @Setter
    @Column(name="cus_city", nullable = false, length = 128)
    private String city;
    
    @Getter
    @Setter
    @Column(name="cus_country", nullable = false, length = 128)
    private String country;
    
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name="cus_gender", nullable = false, length = 1)
    private Gender gender;
    
                
    @Getter
    @Setter
    @OneToMany(mappedBy="customer")
    private List<Sale> sales = new LinkedList<>();
    

}
