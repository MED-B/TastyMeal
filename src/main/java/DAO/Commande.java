package DAO;

import java.io.Serializable;

public class Commande implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id_commande;
	private int id_client;
    private int code_menu;
    private int id_restaurateur;
    private int prix;
    
    public Commande(int id_client,int code_menu,int id_restaurateur,int prix) {
    	this.id_client=id_client;
    	this.code_menu=code_menu;
    	this.id_restaurateur=id_restaurateur;
    	this.prix=prix;
    }
    
    public Commande(int id_commande,int id_client,int code_menu,int id_restaurateur,int prix) {
    	this.id_commande=id_commande;
    	this.id_client=id_client;
    	this.code_menu=code_menu;
    	this.id_restaurateur=id_restaurateur;
    	this.prix=prix;
    }
	
	public Commande() {
		// TODO Auto-generated constructor stub
	}



	public int getId_client() {
		return id_client;
	}
	public void setId_client(int id_client) {
		this.id_client = id_client;
	}
	public int getCode_menu() {
		return code_menu;
	}
	public void setCode_menu(int code_menu) {
		this.code_menu = code_menu;
	}
	public int getId_restaurateur() {
		return id_restaurateur;
	}
	public void setId_restaurateur(int id_restaurateur) {
		this.id_restaurateur = id_restaurateur;
	}



	public int getId_commande() {
		return id_commande;
	}



	public void setId_commande(int id_commande) {
		this.id_commande = id_commande;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}



	

}
