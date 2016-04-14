package org.jpa_release;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OptimisticLockType;

//OK BAD NAME
/*CREATE TABLE storage
(
  id serial NOT NULL,
  id_ingr integer,
  number_ingr integer,
  CONSTRAINT id_stor PRIMARY KEY (id),
  CONSTRAINT id_ingr FOREIGN KEY (id_ingr)
      REFERENCES ingredient_dict (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)*/

@Entity
/*@NamedQueries({ 
	@NamedQuery(name="StorageEntity.selectIngredientStorage", 
			query="SELECT s FROM StorageEntity s, PizzaEntity p WHERE s.idingr_id = p.idingr_id "), 
})*/ 
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.ALL, dynamicUpdate = true ) 
@Table(name = "storage", uniqueConstraints = { 
        @UniqueConstraint(columnNames = "id")})
public class StorageEntity implements Serializable {

	private static final long serialVersionUID = -6768626086597040657L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@OneToOne(fetch = FetchType.EAGER )
	private IngredientDictEntity idIngr;
	
	@Column(name = "number_ingr")
	private Integer numberIngr;

	public IngredientDictEntity getIdIngr() {
		return idIngr;
	}

	public void setIdIngr(IngredientDictEntity idIngr) {
		this.idIngr = idIngr;
	}
	
	public Integer getNumberIngr() {
		return numberIngr;
	}

	public void setNumberIngr(Integer numberIngr) {
		this.numberIngr = numberIngr;
	}
}