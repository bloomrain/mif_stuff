package lt.vu.mif.jate.tasks.task03.jpa.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gege
 */
public abstract class Entry implements Serializable {

    abstract Long getId();

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 37).append(this.getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entry other = (Entry) obj;
        
        return this.getId().equals(other.getId());
    }

}
