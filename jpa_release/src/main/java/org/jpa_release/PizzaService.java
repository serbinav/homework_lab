package org.jpa_release;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
 
public class PizzaService {
 
    public EntityManager em = Persistence.createEntityManagerFactory("IPSUnit").createEntityManager();
 
    public PizzaEntity add(PizzaEntity store){
        em.getTransaction().begin();
        PizzaEntity fromDB = em.merge(store);
        em.getTransaction().commit();
        return fromDB;
    }
 
    public void delete(Integer id){
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }
 
    public PizzaEntity get(Integer id){
        return em.find(PizzaEntity.class, id);
    }
 
    public void update(PizzaEntity store){
        em.getTransaction().begin();
        em.merge(store);
        em.getTransaction().commit();
    }
  
    public Integer getMax(){
    	Query namedQuery = em.createNamedQuery("PizzaEntity.getMax");
    	return (Integer)namedQuery.getSingleResult();
    } 
}