package org.jpa_release;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

//OK BAD NAME
/*CREATE TABLE pizza
(
  id serial NOT NULL,
  id_pizza integer NOT NULL,
  size smallint NOT NULL,
  id_ingr integer,
  number_ingr integer,
  CONSTRAINT id_pizza PRIMARY KEY (id),
  CONSTRAINT id_ingr FOREIGN KEY (id_ingr)
      REFERENCES ingredient_dict (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)*/

@Entity
//@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.ALL, dynamicUpdate = true ) 
@Table(name = "pizza", uniqueConstraints = { 
        @UniqueConstraint(columnNames = "id")})

@NamedQueries({ 
	@NamedQuery(name = "PizzaEntity.getMax", query = "SELECT MAX(p.idPizza) FROM PizzaEntity p")
}) 
public class PizzaEntity implements Serializable {

	private static final long serialVersionUID = 4930564783604224701L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "id_pizza", nullable = false)
	private Integer idPizza;
	
	//short
	@Column(name = "size", nullable = false)
	private Integer size;
	
	@OneToOne(fetch = FetchType.EAGER)
	private IngredientDictEntity idIngr;
	
	@Column(name = "number_ingr")
	private Integer numberIngr;
	
	public Integer getIdPizza() {
		return idPizza;
	}

	public void setIdPizza(Integer idPizza) {
		this.idPizza = idPizza;
	}
	
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

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
	
	public Integer getId() {
		return id;
	}
}