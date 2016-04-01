package org.jpa_release;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class PizzaEntity implements Serializable {

	private static final long serialVersionUID = 4930564783604224701L;

	@Id
	@Column(name = "id", nullable = false)
	private int id;
	// 	  CONSTRAINT id_pizza PRIMARY KEY (id),
	// 	  CONSTRAINT id_ingr FOREIGN KEY (id_ingr)
	// 	  REFERENCES ingredient_dict (id) MATCH SIMPLE
	// 	  ON UPDATE NO ACTION ON DELETE NO ACTION

	@Column(name = "id_pizza", nullable = false)
	private int idPizza;
	
	@Column(name = "size", nullable = false)
	private short size;
	
	@Column(name = "id_ingr")
	@OneToMany(fetch = FetchType.EAGER)
	private int idIngr;
	
	@Column(name = "number_ingr")
	private int numberIngr;
	
	public int getIdPizza() {
		return idPizza;
	}

	public void setIdPizza(int idPizza) {
		this.idPizza = idPizza;
	}
	
	public short getSize() {
		return size;
	}

	public void setSize(short size) {
		this.size = size;
	}

	public int getIdIngr() {
		return idIngr;
	}

	public void setIdIngr(int idIngr) {
		this.idIngr = idIngr;
	}
	
	public int getNumberIngr() {
		return numberIngr;
	}

	public void setNumberIngr(int numberIngr) {
		this.numberIngr = numberIngr;
	}

	public long getId() {
		return id;
	}
}