package lt.vu.mif.jate.tasks.task03.jpa;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import lt.vu.mif.jate.tasks.task03.jpa.model.Customer;
import lt.vu.mif.jate.tasks.task03.jpa.model.Product;

public class DbManager implements AutoCloseable {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction userTransaction;

    @Override
    public void close() {
        this.getEntityManager().close();
        this.getEntityManagerFactory().close();
    }

    public List getListOf(Class aClass) {
        return this.getListOf(aClass, "id");
    }
    
    List getListOf(Class aClass, String orderAttribute) {
        Query query;
        String queryString;
        
        queryString = "SELECT x FROM " + aClass.getSimpleName();
        queryString += " x ORDER BY " + orderAttribute;
        
        System.out.println(queryString);
        query = this.getEntityManager().createQuery(queryString);
        return (List<Customer>) query.getResultList();
    }

    
    private EntityManager getEntityManager() {
        if(this.entityManager == null) {
            this.entityManager = getEntityManagerFactory().createEntityManager();
        }
        return this.entityManager;
    }
    
    private EntityTransaction getUserTransaction() {
        if(this.userTransaction == null) {
            this.userTransaction = getEntityManager().getTransaction();
        }
        return this.userTransaction;
    }

    

    private EntityManagerFactory getEntityManagerFactory() {
        if(this.entityManagerFactory == null) {
            this.entityManagerFactory = Persistence.createEntityManagerFactory("stud");
        }
        return this.entityManagerFactory;
    }
}
