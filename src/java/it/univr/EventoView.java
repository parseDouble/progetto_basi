package it.univr;

import it.univr.database.Evento;
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

@ManagedBean(name = "evt")
@SessionScoped

public class EventoView implements Serializable {

  // === Properties ============================================================

  // Alcune variabili 

  private DataSource ds;
  private List<Evento> listaEventi;
  private Evento eventoSelezionato;
  private String tipo;
  private int anno;


  // === Methods ===============================================================


  public EventoView() {
    this.listaEventi = null;
    this.eventoSelezionato = null;
    this.tipo = null;
    this.anno = -1;

  }

  @PostConstruct
  public void initialize() {
      this.ds = new DataSource();
  }

  public List<Evento> getEventi(String corso, String tipo, int anno) {
    

    if( this.ds != null ){
			
        try {
            listaEventi= ds.getListaEvento(corso, anno, tipo) ;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EventoView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    return listaEventi;
  }

  public void recuperaEvento( String id ){
    if( this.ds != null ){

      
        try {
            eventoSelezionato = ds.getEvento( id );
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EventoView.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    else{
    	if(listaEventi!= null && !listaEventi.isEmpty()){
    		for(Evento evt : listaEventi){
    			if(evt.getId()!=null){
    				if(evt.getId().equalsIgnoreCase(id)){
    					eventoSelezionato = evt;
    				}
    			}
    		}
    	}
    }
  }

  public Evento getEvento(){
    return eventoSelezionato;
  }

  public void setTipo( String tipo ) {
    this.tipo = tipo;
  }

  public String getTipo() {
    return this.tipo;
  }

  public int getAnno() {
    return this.anno;
  }

  public void setAnno( int anno ) {
    this.anno = anno;
  }

  public String docenteNC( String id){
    this.recuperaEvento(id );
    String doc = "";

      try {
          doc = ds.getNeCDoc( eventoSelezionato.getDocente() );
      } catch (ClassNotFoundException ex) {
          Logger.getLogger(EventoView.class.getName()).log(Level.SEVERE, null, ex);
      }
    return doc;
  }

  public String getColore(){

    String colori="";
    for (Evento event : listaEventi) {
      if(event.getTipo().equals("LEZ")){
        colori+="calendarioLEZ";
      }else if(event.getTipo().equals("ESE")){
        colori+="calendarioESE";
      }else if(event.getTipo().equals("VER")){
        colori+="calendarioVER";
      }
      colori+=", ";
    }
    colori+="calendarioDefault";
    return colori;
  }

}