package it.univr.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alessandro
 */
public class MakeBean {

    /**
     * Il metodo costruisce un bean a partire dal record attuale del ResultSet
     * passato come parametro.
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public static Erogazione makeErogazioneBean(ResultSet rs) throws SQLException {
        Erogazione bean = new Erogazione();
        bean.setCodice(rs.getString("Codice"));
        bean.setDataInizio(0, rs.getInt("GiornoInizio"));
        bean.setDataInizio(1, rs.getInt("meseInizio"));
        bean.setDataInizio(2, rs.getInt("annoInizio"));
        bean.setDataFine(0, rs.getInt("giornoFine"));
        bean.setDataFine(1, rs.getInt("meseFine"));
        bean.setDataFine(2, rs.getInt("annoFine"));
        bean.setStato(rs.getString("stato"));
        if (rs.getObject("postiDisp") instanceof Integer) {
            bean.setPosti((Integer) rs.getObject("postiDisp"));
        }

        return bean;
    }

    /**
     * Il metodo costruisce un bean a partire dal record attuale del ResultSet
     * passato come parametro.
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public static Evento makeEvtBean(ResultSet rs) throws SQLException {
        Evento bean = new Evento();
        bean.setId(rs.getString("ID"));
        bean.setCodiceErogazione(rs.getString("CodiceErogazione"));
        bean.setTipo(rs.getString("Tipo"));
        bean.setData(0, rs.getInt("giorno"));
        bean.setData(1, rs.getInt("mese"));
        bean.setData(2, rs.getInt("anno"));
        bean.setOra(0, rs.getInt("ora"));
        bean.setOra(1, rs.getInt("minuti"));
        bean.setDocente(rs.getString("CFDoc"));

        return bean;
    }

    /**
     * Il metodo costruisce un bean a partire dal record attuale del ResultSet
     * passato come parametro.
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public static Docente makeDocBean(ResultSet rs) throws SQLException {
        Docente bean = new Docente();
        bean.setCf(rs.getString("CF"));
        bean.setNome(rs.getString("Nome"));
        bean.setCognome(rs.getString("Cognome"));
        bean.setEmail(rs.getString("Email"));
        bean.setTelefono(rs.getString("Telefono"));

        return bean;
    }

    /**
     * Il metodo costruisce un bean a partire dal record attuale del ResultSet
     * passato come parametro.
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public static CorsoStudi makeCorsoStudiBean(ResultSet rs) throws SQLException {
        CorsoStudi bean = new CorsoStudi();
        bean.setNome(rs.getString("Nome"));
        bean.setCategoria(rs.getString("Categoria"));
        bean.setProgramma(rs.getString("Programma"));
        bean.setNMaxStud(rs.getInt("NMaxStud"));
        bean.setNOreLez(rs.getInt("NOreLez"));

        return bean;
    }
}
