package org.jpa_release;
 
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
 
public class StorageService {
 
    public EntityManager em = Persistence.createEntityManagerFactory("IPSUnit").createEntityManager();
 
    public StorageEntity add(StorageEntity store){
        em.getTransaction().begin();
        StorageEntity fromDB = em.merge(store);
        em.getTransaction().commit();
        return fromDB;
    }
 
    public void delete(Integer id){
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }
 
    public StorageEntity get(Integer id){
        return em.find(StorageEntity.class, id);
    }
 
    public void update(StorageEntity store){
        em.getTransaction().begin();
        em.merge(store);
        em.getTransaction().commit();
    }
 
/*    public List<StorageEntity> getAll(){
        TypedQuery<StorageEntity> namedQuery = em.createNamedQuery("StorageEntity.getAll", StorageEntity.class);
        return namedQuery.getResultList();
    }
*/   
    public Long getCount(){
    	Query namedQuery = em.createNamedQuery("StorageEntity.getCount");
    	return (Long)namedQuery.getSingleResult();
    } 
    
    public StorageEntity findByName( String name){
        TypedQuery<StorageEntity> namedQuery = em.createNamedQuery("StorageEntity.findByName", StorageEntity.class).
        		setParameter("name", name);
    	return namedQuery.getSingleResult();
    } 
}