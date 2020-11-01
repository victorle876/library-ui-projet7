package com.example.library.service;

import com.example.library.model.entity.Utilisateur;
import com.example.library.repository.UtilisateurRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository UtilisateurRepository;

    private static final Logger logger = LogManager.getLogger(CustomUserDetailsService.class);

    /**
     * Méthode permet de trouver l'utilisateur en fonction de l'adresse mail
     *
     * @param username
     *
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = UtilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + username + " not found"));
        logger.debug(utilisateur.getId());
        return new org.springframework.security.core.userdetails.User(utilisateur.getUsername(), utilisateur.getPassword(),
                getAuthorities(utilisateur));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Utilisateur utilisateur) {
        String[] userRoles = utilisateur.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}
