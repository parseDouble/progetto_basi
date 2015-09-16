package it.univr.database;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe mette a disposizione i metodi per effettuare interrogazioni
 * sulla base di dati.
 */
public class DataSource implements Serializable {

    // === Methods ===============================================================
    /**
     * Costruttore della classe. Carica i driver da utilizzare per la
     * connessione alla base di dati.
     *
     *
     */
    public DataSource() {

    }
    // ===========================================================================

    /**
     * Metodo per il recupero di tutti i corsi con almeno un erogazione che non
     * è nello stato chiuso il risultato può essere filtrato secondo alcuni
     * valori
     *
     * @param categoriacerca
     * @param programmacerca
     * @param sottostringanelnome
     * @param maxStud
     * @param oreLezTot
     *
     * @return result
     */
    public List<CorsoStudi> getCorsiStudiAttivi(String categoriacerca, String programmacerca, String sottostringanelnome, int maxStud, int oreLezTot) throws ClassNotFoundException {

        // dichiarazione delle variabili
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        List<CorsoStudi> result = new ArrayList<CorsoStudi>();

        try {
            // tentativo di connessione al database
            con = Supporto.getConnection();

            // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
            stmt = con.createStatement();
            // eseguo l'interrogazione desiderata

            String query = Supporto.getQuery("cca");

            if (categoriacerca != null && categoriacerca != "") {
                query = query + " AND corso.categoria ILIKE '" + categoriacerca + "'";
            }
            if (programmacerca != null && programmacerca != "") {
                query = query + " AND corso.programma ILIKE '" + programmacerca + "'";
            }
            if (sottostringanelnome != null && sottostringanelnome != "") {
                query = query + " AND corso.nome ILIKE '%" + sottostringanelnome + "%'";
            }
            if (maxStud >= 0) {
                query = query + " AND corso.NMaxStud =" + maxStud + "";
            }
            if (oreLezTot >= 0) {
                query = query + " AND corso.NOreLez =" + oreLezTot + "";
            }
            query = query + " GROUP BY corso.nome ";

            rs = stmt.executeQuery(query);

            // memorizzo il risultato dell'interrogazione nel Vector
            while (rs.next()) {

                result.add(MakeBean.makeCorsoStudiBean(rs));

            }

        } catch (SQLException sqle) { // catturo le eventuali eccezioni!
            sqle.printStackTrace();

        } finally { // alla fine chiudo la connessione.
            try {
                con.close();
            } catch (SQLException sqle1) {
                sqle1.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Metodo per il recupero delle informazioni del corso di studi con il nome
     * specificato.
     *
     * @param nome
     * @return result
     */
    public CorsoStudi getCorsoStudi(String nome) throws ClassNotFoundException {
        // Dichiarazione delle variabili necessarie
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CorsoStudi result = null;
        try {
            // tentativo di connessione al database
            con = Supporto.getConnection();
            // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
            pstmt = con.prepareStatement(Supporto.getQuery("cs"));
            pstmt.clearParameters();
            // imposto i parametri della query
            pstmt.setString(1, nome);
            // eseguo la query
            rs = pstmt.executeQuery();
            // memorizzo il risultato dell'interrogazione in Vector di Bean
            if (rs.next()) {
                result = MakeBean.makeCorsoStudiBean(rs);
            }

        } catch (SQLException sqle) { // Catturo le eventuali eccezioni
            sqle.printStackTrace();

        } finally { // alla fine chiudo la connessione.
            try {
                con.close();
            } catch (SQLException sqle1) {
                sqle1.printStackTrace();
            }
        }

        return result;
    }

    public List<Erogazione> getListaErogazioni(String nome) throws ClassNotFoundException {
        // Dichiarazione delle variabili necessarie
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Erogazione> result = new ArrayList<Erogazione>();
        try {
            // tentativo di connessione al database
            con = Supporto.getConnection();
            // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
            pstmt = con.prepareStatement(Supporto.getQuery("cle"));
            pstmt.clearParameters();
            // imposto i parametri della query
            pstmt.setString(1, nome);
            // eseguo la query
            rs = pstmt.executeQuery();

            // memorizzo il risultato dell'interrogazione in Vector di Bean
            while (rs.next()) {
                result.add(MakeBean.makeErogazioneBean(rs));
            }

        } catch (SQLException sqle) { // catturo le eventuali eccezioni!
            sqle.printStackTrace();

        } finally { // alla fine chiudo la connessione.
            try {
                con.close();
            } catch (SQLException sqle1) {
                sqle1.printStackTrace();
            }
        }
        return result;
    }

    public Erogazione getErogazione(String codice) throws ClassNotFoundException {
        // Dichiarazione delle variabili necessarie
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Erogazione result = null;
        try {
            // tentativo di connessione al database
            con = Supporto.getConnection();
            // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
            pstmt = con.prepareStatement(Supporto.getQuery("ce"));
            pstmt.clearParameters();
            // imposto i parametri della query
            pstmt.setString(1, codice);
            // eseguo la query
            rs = pstmt.executeQuery();
            // memorizzo il risultato dell'interrogazione in Vector di Bean
            if (rs.next()) {
                result = MakeBean.makeErogazioneBean(rs);
            }

        } catch (SQLException sqle) { // Catturo le eventuali eccezioni
            sqle.printStackTrace();

        } finally { // alla fine chiudo la connessione.
            try {
                con.close();
            } catch (SQLException sqle1) {
                sqle1.printStackTrace();
            }
        }
        return result;
    }

    public List<Docente> getListaDocente(String codice) throws ClassNotFoundException {
        // Dichiarazione delle variabili necessarie
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Docente> result = new ArrayList<Docente>();
        try {
            // tentativo di connessione al database
            con = Supporto.getConnection();
            // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
            pstmt = con.prepareStatement(Supporto.getQuery("cld"));
            pstmt.clearParameters();
            // imposto i parametri della query
            pstmt.setString(1, codice);
            // eseguo la query
            rs = pstmt.executeQuery();

            // memorizzo il risultato dell'interrogazione in Vector di Bean
            while (rs.next()) {
                result.add(MakeBean.makeDocBean(rs));
            }

        } catch (SQLException sqle) { // catturo le eventuali eccezioni!
            sqle.printStackTrace();

        } finally { // alla fine chiudo la connessione.
            try {
                con.close();
            } catch (SQLException sqle1) {
                sqle1.printStackTrace();
            }
        }
        return result;
    }

    public Docente getDocente(String id) throws ClassNotFoundException {
        // Dichiarazione delle variabili necessarie
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Docente result = null;
        try {
            // tentativo di connessione al database
            con = Supporto.getConnection();
            // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
            pstmt = con.prepareStatement(Supporto.getQuery("cde"));
            pstmt.clearParameters();
            // imposto i parametri della query
            pstmt.setString(1, id);
            // eseguo la query
            rs = pstmt.executeQuery();

            // memorizzo il risultato dell'interrogazione in Vector di Bean
            while (rs.next()) {
                result = MakeBean.makeDocBean(rs);
            }

        } catch (SQLException sqle) { // catturo le eventuali eccezioni!
            sqle.printStackTrace();

        } finally { // alla fine chiudo la connessione.
            try {
                con.close();
            } catch (SQLException sqle1) {
                sqle1.printStackTrace();
            }
        }
        return result;
    }

    public List<Evento> getListaEvento(String nome, int anno, String stato) throws ClassNotFoundException {

        // dichiarazione delle variabili
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Evento> result = new ArrayList<Evento>();

        String query = Supporto.getQuery("clevt");

        if (anno >= 0) {
            query = query + " AND  date_part('Year', Evento.data) = " + anno + "";
        }
        if (stato != null && stato != "") {
            query = query + " AND Evento.Stato ILIKE '" + stato + "'";
        }

        query = query + " GROUP BY Evento.ID ORDER BY Evento.data, Evento.ora ";

        try {
            // tentativo di connessione al database
            con = Supporto.getConnection();

            pstmt = con.prepareStatement(query);

            // rs = stmt.executeQuery( query );
            pstmt.clearParameters();

            // imposto i parametri della query
            pstmt.setString(1, nome);
            rs = pstmt.executeQuery();

            // memorizzo il risultato dell'interrogazione nel Vector
            while (rs.next()) {

                result.add(MakeBean.makeEvtBean(rs));

            }

        } catch (SQLException sqle) { // catturo le eventuali eccezioni!
            sqle.printStackTrace();

        } finally { // alla fine chiudo la connessione.
            try {
                con.close();
            } catch (SQLException sqle1) {
                sqle1.printStackTrace();
            }
        }
        return result;
    }

    public Evento getEvento(String id) throws ClassNotFoundException {
        // Dichiarazione delle variabili necessarie
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Evento result = null;
        try {
            // tentativo di connessione al database
            con = Supporto.getConnection();            // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
            pstmt = con.prepareStatement(Supporto.getQuery("cevt"));
            pstmt.clearParameters();
            // imposto i parametri della query
            pstmt.setString(1, id);
            // eseguo la query
            rs = pstmt.executeQuery();
            // memorizzo il risultato dell'interrogazione in Vector di Bean
            if (rs.next()) {
                result = MakeBean.makeEvtBean(rs);
            }

        } catch (SQLException sqle) { // Catturo le eventuali eccezioni
            sqle.printStackTrace();

        } finally { // alla fine chiudo la connessione.
            try {
                con.close();
            } catch (SQLException sqle1) {
                sqle1.printStackTrace();
            }
        }
        return result;
    }

    public String getNeCDoc(String cf) throws ClassNotFoundException {
        // Dichiarazione delle variabili necessarie
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String result = null;
        try {
            // tentativo di connessione al database
            con = Supporto.getConnection();            // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
            pstmt = con.prepareStatement(Supporto.getQuery("ncd"));
            pstmt.clearParameters();
            // imposto i parametri della query
            pstmt.setString(1, cf);
            // eseguo la query

            rs = pstmt.executeQuery();

            // memorizzo il risultato dell'interrogazione in Vector di Bean
            if (rs.next()) {

                result = "" + rs.getString("Nome") + " " + rs.getString("Cognome");
            }

        } catch (SQLException sqle) { // Catturo le eventuali eccezioni
            sqle.printStackTrace();

        } finally { // alla fine chiudo la connessione.
            try {
                con.close();
            } catch (SQLException sqle1) {
                sqle1.printStackTrace();
            }
        }
        return result;
    }

    public boolean doLogin(String name, String password) throws ClassNotFoundException {
        boolean log = false;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // tentativo di connessione al database
            con = Supporto.getConnection();
            // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
            pstmt = con.prepareStatement(Supporto.getQuery("psw"));
            pstmt.clearParameters();
            // imposto i parametri della query
            pstmt.setString(1, name);

            // eseguo la query
            rs = pstmt.executeQuery();

            if (rs.next()) {
                try {
                    log = PasswordHash.validatePassword(password, rs.getString("password"));
                } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                    Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } catch (SQLException sqle) { // Catturo le eventuali eccezioni
            sqle.printStackTrace();

        } finally { // alla fine chiudo la connessione.
            try {
                con.close();
            } catch (SQLException sqle1) {
                sqle1.printStackTrace();
            }
        }

        return log;

    }

}
