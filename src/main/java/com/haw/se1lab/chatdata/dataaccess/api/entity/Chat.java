package com.haw.se1lab.chatdata.dataaccess.api.entity;

import com.haw.se1lab.general.dataaccess.api.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@NoArgsConstructor
@Entity
public class Chat extends AbstractEntity
{

    @ElementCollection
    private List<Nachricht> nachrichten;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "C",
               joinColumns = @JoinColumn(name = "chatId"),
                inverseJoinColumns = @JoinColumn(name = "participantId"))
    private Set<Benutzer> teilnehmer;

    public Chat(Benutzer benutzer)
    {
        nachrichten = new ArrayList<Nachricht>();
        teilnehmer = new HashSet<Benutzer>();
        addTeilnehmer(benutzer);
    }

    public void addNachricht(Nachricht nachricht)
    {
        Objects.requireNonNull(nachricht);
        nachrichten.add(nachricht);
    }

    public void addTeilnehmer(Benutzer benutzer)
    {
        Objects.requireNonNull(benutzer);
        teilnehmer.add(benutzer);
    }
}
