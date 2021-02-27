package DAO;

import java.io.Serializable;

public class Menu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code_menu;
	private int id_restaurateur;
     private String items;
     private int prix;
     private String categorie;
     
     public Menu(int code_menu,int id_restaurateur, String items,int prix,String categorie ) {
    	 this.code_menu=code_menu;
    	 this.id_restaurateur=id_restaurateur;
    	 this.items=items;
    	 this.prix=prix;
     }
     public Menu(String items,int code_menu,int prix,String categorie ) {
    	 this.code_menu=code_menu;
    	
    	 this.items=items;
    	 this.prix=prix;
     }
     public Menu(int id_restaurateur, String items,int prix,String categorie ) {
    	
    	 this.id_restaurateur=id_restaurateur;
    	 this.items=items;
    	 this.prix=prix;
     }
	public Menu() {
		// TODO Auto-generated constructor stub
	}

	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	
	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
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

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	
     
}
