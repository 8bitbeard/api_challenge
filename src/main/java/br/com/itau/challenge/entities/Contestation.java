package br.com.itau.challenge.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "contestations")
public class Contestation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    private Purchase purchase;

    @Column
    private String protocolNumber;

    @Column
    private OffsetDateTime contestationDate;

    public void generateProtocolNumber() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
        setProtocolNumber(formatter.format(date));
    }
}
