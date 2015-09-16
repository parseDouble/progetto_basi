package it.univr;

import it.univr.database.Erogazione;
import it.univr.database.Docente;
import it.univr.database.DataSource;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

@ManagedBean(name = "er")
@SessionScoped
public class ErogazioneView implements Serializable {

    private DataSource ds;
    private List<Erogazione> listaErogazione;
    private Erogazione erogazioneSelezionato;
    private Docente doc;
    private List<Docente> listaDoc;

    public ErogazioneView() {
        this.ds = null;
        this.listaErogazione = null;
        this.erogazioneSelezionato = null;
        this.doc=null;
        this.listaDoc=null;
    }

    @PostConstruct
    public void initialize() {
        this.ds = new DataSource();
    }

    public List<Erogazione> getErogazioni(String nome) {
        if (this.ds != null) {
            try {
                listaErogazione = ds.getListaErogazioni(nome);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ErogazioneView.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return listaErogazione;

    }

    public String recuperaErogazione(String codice){
        if (this.ds != null) {
            try {
                erogazioneSelezionato = ds.getErogazione(codice);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ErogazioneView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (listaErogazione != null && !listaErogazione.isEmpty()) {

                for (Erogazione erogazione : listaErogazione) {
                    if (erogazione.getCodice().equalsIgnoreCase(codice)) {
                        erogazioneSelezionato = erogazione;
                    }
                }
            }
        }
        return "/Template/dettaglioErogazione";
    }

    public List<Docente> getDocenti(String codice) {
        List<Docente> listaDoc=null;
        if (this.ds != null) {
            try {
                listaDoc = ds.getListaDocente(codice);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ErogazioneView.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return listaDoc;

    }    

    public List<Erogazione> getListaErogazione() {
        return listaErogazione;
    }

    public void setListaErogazione(List<Erogazione> listaErogazione) {
        this.listaErogazione = listaErogazione;
    }

    public Erogazione getErogazioneSelezionato() {
        return erogazioneSelezionato;
    }

    public void setErogazioneSelezionato(Erogazione erogazioneSelezionato) {
        this.erogazioneSelezionato = erogazioneSelezionato;
    }

}