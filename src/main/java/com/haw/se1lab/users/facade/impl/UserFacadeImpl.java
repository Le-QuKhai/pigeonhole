package com.haw.se1lab.users.facade.impl;

import com.haw.se1lab.users.common.api.datatype.RegestrierungsFormular;
import com.haw.se1lab.users.common.api.exception.IncorrectPasswordException;
import com.haw.se1lab.users.common.api.exception.RegestrierungsFormularException;
import com.haw.se1lab.users.common.api.exception.UserDoesntExistsException;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import com.haw.se1lab.users.facade.api.UserFacade;
import com.haw.se1lab.users.logic.api.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
public class UserFacadeImpl implements UserFacade {

    private static final Logger LOG = LoggerFactory.getLogger(UserFacadeImpl.class);

    @Autowired
    private UserUseCase userUseCase;

    public ResponseEntity<?> createBenutzer(RegestrierungsFormular formular){
        try {
            Benutzer benutzer = userUseCase.createUser(formular);
            LOG.info("Benutzer '{}' wurde erfolgreich registriert!", benutzer.getBenutzerName());
            return ResponseEntity.ok(benutzer);
        } catch (IllegalArgumentException | RegestrierungsFormularException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> loginBenutzer(Benutzer benutzer) {
        try {
            Benutzer dataBaseBenutzer = userUseCase.loginBenutzer(benutzer);
            LOG.info("Benutzer '{}' wurde erfolgreich eingeloggt!", dataBaseBenutzer.getBenutzerName());
            return ResponseEntity.ok(dataBaseBenutzer);
        } catch (IncorrectPasswordException | UserDoesntExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
