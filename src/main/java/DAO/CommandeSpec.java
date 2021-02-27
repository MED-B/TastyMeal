package DAO;

public class CommandeSpec {
private String email;
private String adress;
private String items;
private String state;
private int prix;
private int id_commande;
public CommandeSpec(int id_commande,String email, String adress, String items, int prix,String state) {
	this.id_commande=id_commande;
	this.email = email;
	this.adress = adress;
	this.items = items;
	this.prix = prix;
	this.state=state;
}
public CommandeSpec(){
	
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getAdress() {
	return adress;
}
public void setAdress(String adress) {
	this.adress = adress;
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
public void setPrix(int  prix) {
	this.prix = prix;
}
public int getId_commande() {
	return id_commande;
}
public void setId_commande(int id_commande) {
	this.id_commande = id_commande;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}

}