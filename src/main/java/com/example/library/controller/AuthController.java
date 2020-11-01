package com.example.library.controller;

import com.example.library.model.dto.UtilisateurDTO;
import com.example.library.model.entity.Utilisateur;
import com.example.library.repository.UtilisateurRepository;
import com.example.library.service.UtilisateurService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String pwd) {
        UtilisateurDTO utilisateurExistant = this.utilisateurService.getUserByUsername(username);
        if (utilisateurExistant == null) {
            System.out.println("utilisateur non existant");
        }
        String token = getJWTToken(username);
        System.out.println(username);
        System.out.println(pwd);
        System.out.println(passwordEncoder.matches(pwd, utilisateurExistant.getPassword()));
        System.out.println(utilisateurExistant);
        if (passwordEncoder.matches(pwd, utilisateurExistant.getPassword()) == true) {
            Utilisateur utilisateur = convertToEntity(utilisateurExistant);
            System.out.println(token);
            utilisateur.setUsername(username);
            utilisateur.setToken(token);
        }
        return token;
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        System.out.println(token);

        return "Bearer " + token;
    }

    @PostMapping("/logout")
    private boolean logout(@RequestParam("username") String username) {
        Boolean utilisateurConnecte = this.utilisateurRepository.existsByUsername(username);
        return true;
    }

    private Utilisateur convertToEntity(UtilisateurDTO utilisateurDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Utilisateur utilisateur = mapper.map(utilisateurDTO, Utilisateur.class);
        return utilisateur;
    }
}
