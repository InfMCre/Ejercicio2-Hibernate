package com.example.ejercicio2.auth.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String email;
	@Column(length = 120)
	private String password;
	
	// si quisieramos activarlos de forma manual, o al confirmar un correo podriamos poner default false
	@Column(columnDefinition = "boolean default true")
	private boolean isEnabled;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id", foreignKey=@ForeignKey(name = "Fk_user_id" )), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id", foreignKey=@ForeignKey(name = "Fk_role_id" ))) 
    private List<Role> roles;
	

	public User() { }
	
	public User(String email, String password) {
		this.email = email;
		this.password = password;
		List<Role> roles = new ArrayList<Role>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final List<GrantedAuthority> authorities = new ArrayList<>();
		System.out.println("ch1");
        for (final Role role : roles) {
        	System.out.println("ch2:" + role.getName());
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        System.out.println("ch3");
        return authorities;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
}
