package it.univr.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alessandro
 */
public class Supporto {

    //Proprietà della connessione
    private final static String USER = "userlab41";
    private final static String PWD = "quarantaunoUd";
    private final static String URL = "jdbc:postgresql://dbserver.scienze.univr.it/dblab41";
    private final static String DRIVER = "org.postgresql.Driver";

    //Elenco query
    private static String psw = "SELECT Studente.password AS password "
            + "FROM Studente"
            + "WHERE Studente.Codice=?";

    private static String cca = "SELECT Corso.* "
            + "FROM Corso INNER JOIN Erogato "
            + "ON (nome = nomeCorso) INNER JOIN Erogazione "
            + "ON (codiceErogazione = codice) "
            + "WHERE Erogazione.stato != 'CH' ";

    private static String cs = "SELECT * "
            + "FROM Corso "
            + "WHERE nome =? ";

    private static String cle = "SELECT Erogazione.codice, "
            + "date_part('Day', Erogazione.dataInizio) AS GiornoInizio, "
            + "date_part('Month', Erogazione.dataInizio) AS meseInizio, "
            + "date_part('Year', Erogazione.dataInizio) AS annoInizio, "
            + "date_part('Day', Erogazione.dataFine) AS giornoFine, "
            + "date_part('Month', Erogazione.dataFine) AS meseFine, "
            + "date_part('Year', Erogazione.dataFine) AS annoFine, "
            + "Erogazione.stato, Erogazione.postiDisp "
            + "FROM Corso INNER JOIN Erogato "
            + "ON (nome = nomeCorso) INNER JOIN Erogazione "
            + "ON (codiceErogazione = codice) "
            + "WHERE Corso.nome =? ";

    private static String ce = "SELECT Erogazione.codice, "
            + "date_part('Day', Erogazione.dataInizio) AS GiornoInizio, "
            + "date_part('Month', Erogazione.dataInizio) AS meseInizio, "
            + "date_part('Year', Erogazione.dataInizio) AS annoInizio, "
            + "date_part('Day', Erogazione.dataFine) AS giornoFine, "
            + "date_part('Month', Erogazione.dataFine) AS meseFine, "
            + "date_part('Year', Erogazione.dataFine) AS annoFine, "
            + "Erogazione.stato, Erogazione.postiDisp "
            + "FROM Erogazione "
            + "WHERE codice =? ";

    private static String cld = "SELECT Docente.* "
            + "FROM Docente INNER JOIN Evento ON (CF = CFDoc) "
            + "WHERE Evento.CodiceErogazione = ? ";

    private static String cde = "SELECT Docente.* "
            + "FROM Docente INNER JOIN Evento ON (CF = CFDoc) "
            + "WHERE Evento.ID = ? ";

    private static String clevt = "SELECT Evento.ID, Evento.CodiceErogazione, "
            + "Evento.Tipo, date_part('Day', Evento.data) AS giorno, date_part('Month', Evento.data) AS mese, date_part('Year', Evento.data) AS anno, "
            + "date_part('Hour', Evento.ora) AS ora, date_part('Minute', Evento.ora) AS minuti, Evento.CFDoc "
            + "FROM Corso INNER JOIN Erogato ON (nome = nomeCorso) INNER JOIN Erogazione ON (codiceErogazione = codice) "
            + "INNER JOIN Evento ON (Erogazione.codice = Evento.CodiceErogazione) "
            + "WHERE Corso.nome = ? ";

    private static String cevt = "SELECT Evento.ID, Evento.CodiceErogazione, "
            + "Evento.Tipo, date_part('Day', Evento.data) AS giorno, date_part('Month', Evento.data) AS mese, date_part('Year', Evento.data) AS anno, "
            + "date_part('Hour', Evento.ora) AS ora, date_part('Minute', Evento.ora) AS minuti, Evento.CFDoc "
            + "FROM Evento "
            + "WHERE Evento.ID = ?";

    private static String ncd = "SELECT Docente.Nome, Docente.Cognome "
            + "FROM Docente "
            + "WHERE Docente.CF = ? ";

    private static final ArrayList<String> pos = posizioni();
    private static final ArrayList<String> query = valori();

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Connection con = DriverManager.getConnection(URL, USER, PWD);
        return con;
    }

    private static ArrayList<String> posizioni() {
        ArrayList<String> tmp = new ArrayList<String>();
        tmp.add("psw");
        tmp.add("cca");
        tmp.add("cs");
        tmp.add("cle");
        tmp.add("ce");
        tmp.add("cld");
        tmp.add("ce");
        tmp.add("clevt");
        tmp.add("cevt");
        tmp.add("ncd");
        return tmp;
    }

    private static ArrayList<String> valori() {
        ArrayList<String> tmp = new ArrayList<String>();
        tmp.add(psw);
        tmp.add(cca);
        tmp.add(cs);
        tmp.add(cle);
        tmp.add(ce);
        tmp.add(cld);
        tmp.add(ce);
        tmp.add(clevt);
        tmp.add(cevt);
        tmp.add(ncd);
        return tmp;
    }
    
    public static String getQuery(String querySel){
        int i = pos.indexOf(querySel);
        return query.get(i);
    }
}
