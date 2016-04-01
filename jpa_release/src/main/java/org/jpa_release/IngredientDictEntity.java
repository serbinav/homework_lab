package org.jpa_release;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ingredient_dict")
public class IngredientDictEntity implements Serializable{

	private static final long serialVersionUID = -1414224003598485749L;

	@Id
	@Column(name = "id", nullable = false)
	@OneToMany(fetch = FetchType.EAGER)
	private int id;
	// CONSTRAINT id_ingr PRIMARY KEY (id)

	@Column(name = "name", length = 200)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}
}
