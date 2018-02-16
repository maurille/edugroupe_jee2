package com.mau.firstsecurity.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mau.firstsecurity.metier.Utilisateur;

public class MyUserDetails implements UserDetails {

	private Utilisateur utilisateur;
	
	public MyUserDetails(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.utilisateur.getRoles()
						.stream().map(r -> r.getRoleName())
						 .map(rolename -> new SimpleGrantedAuthority(rolename))
						 .collect(Collectors.toList());// attention les role doivent etre necessairement  de forme ROLE_XXXX

	}

	@Override
	public String getPassword() { return this.utilisateur.getPassword();}
	@Override
	public String getUsername() { return this.utilisateur.getUsername();}
	@Override
	public boolean isAccountNonExpired() {return true;}
	@Override
	public boolean isAccountNonLocked() {return true;}
	@Override
	public boolean isCredentialsNonExpired() {return true;}
	@Override
	public boolean isEnabled() {return this.utilisateur.isEnabled();}

}
