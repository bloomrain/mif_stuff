/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lt.vu.mif.jate.tasks.task03.jpa.model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

/**
 *
 * @author gege
 */
public class Product extends SaleEntry {
    @Getter @Setter private Long id;
    @Getter @Setter private String name;
    @Getter @Setter private String brand;
}
