package org.jpa_release;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

//OK
/*CREATE TABLE ingredient_dict
(
  id integer NOT NULL,
  name character varying(200),
  CONSTRAINT id_ingr PRIMARY KEY (id)
)*/

@Entity
@Table(name = "ingredient_dict", uniqueConstraints = { 
        @UniqueConstraint(columnNames = "id")})

@NamedQueries({ 
	@NamedQuery(name = "IngredientDictEntity.findByName", query = "SELECT i FROM IngredientDictEntity i WHERE i.name = :name")
}) 
public class IngredientDictEntity implements Serializable{

	private static final long serialVersionUID = -1414224003598485749L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "name", length = 200)
	private String name;

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
}
