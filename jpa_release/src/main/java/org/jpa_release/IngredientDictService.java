package org.jpa_release;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class IngredientDictService {

    public EntityManager em = Persistence.createEntityManagerFactory("IPSUnit").createEntityManager();
    
    public IngredientDictEntity add(IngredientDictEntity store){
        em.getTransaction().begin();
        IngredientDictEntity fromDB = em.merge(store);
        em.getTransaction().commit();
        return fromDB;
    }
 
    public void delete(Integer id){
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }
 
    public IngredientDictEntity get(Integer id){
        return em.find(IngredientDictEntity.class, id);
    }
 
    public void update(IngredientDictEntity store){
        em.getTransaction().begin();
        em.merge(store);
        em.getTransaction().commit();
    }
    
    public IngredientDictEntity findByName( String name){
        TypedQuery<IngredientDictEntity> namedQuery = em.createNamedQuery("IngredientDictEntity.findByName", IngredientDictEntity.class).
        		setParameter("name", name);
    	return namedQuery.getSingleResult();
    } 
}
