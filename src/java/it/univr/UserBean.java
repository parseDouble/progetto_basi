package it.univr;

import it.univr.database.DataSource;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

@ManagedBean(name = "user")
@SessionScoped
public class UserBean {
	
	private String name;
	private String password;
	private boolean loggedIn;
	private DataSource ds;


	public UserBean(){
		this.name=null;
		this.password=null;
		this.loggedIn=false;
	}

	@PostConstruct
 	public void initialize() {
   		try {
    		this.ds = new DataSource();
    	} catch( ClassNotFoundException e ){
      		this.ds = null;
    	}
  	}

	public void setName(String newValue) { 
		name = newValue; 
	}
	
	public boolean getLoggedIn(){
		return this.loggedIn;
	}

	public void setPassword(String newValue) { 
		password = newValue; 
	}

	
	public String login() {
		boolean loggedIn=false;
		try {
		// il metodo doLogin() esegue interrogazione al database per
		// determinare se esiste la coppia username-password
			loggedIn=ds.doLogin(this.name, this.password);
		} catch (Exception ex) {
			return "internalError";
		}
		if (loggedIn){
			return "loginSuccess";
		} else {
			return "loginFailure";
		}

	}
	public String logout() {
		loggedIn = false;
		return "login";
	}
}