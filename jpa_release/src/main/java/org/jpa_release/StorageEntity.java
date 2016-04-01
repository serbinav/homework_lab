package org.jpa_release;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class StorageEntity implements Serializable {

	private static final long serialVersionUID = -6768626086597040657L;

	@Id
	@Column(name = "id", nullable = false)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@ManyToMany(fetch = FetchType.EAGER)
	
	// 	  CONSTRAINT id_stor PRIMARY KEY (id),
	//	  CONSTRAINT id_ingr FOREIGN KEY (id_ingr)
	//	  REFERENCES ingredient_dict (id) MATCH SIMPLE
	//	  ON UPDATE NO ACTION ON DELETE NO ACTION

	@Column(name = "id_ingr")
	@OneToMany(fetch = FetchType.EAGER)
	private int idIngr;
	
	@Column(name = "number_ingr")
	private int numberIngr;

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