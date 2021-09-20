package fr.isika.cdi07.projet3demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_admin")
	private Long idAdmin;
	
	@Column(name="is_super_admin")
	private boolean isSuperAdmin;

	
	////TO DOOOOO
	@OneToOne
	private Role role;


	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}


	public void setSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public Long getIdAdmin() {
		return idAdmin;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Admin [idAdmin=");
		builder.append(idAdmin);
		builder.append(", isSuperAdmin=");
		builder.append(isSuperAdmin);
		builder.append(", role=");
		builder.append(role.getIdRole());
		builder.append("]");
		return builder.toString();
	}
	
	

	
	
}

