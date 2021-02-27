package DAO;

import java.io.Serializable;

public class Client  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id_client;
	private String name;
	private String pass;
	private String email;
	private String adress;
	private String tel;
	private int points;
	
	public Client(String name, String pass, String email, String adress,int points,String tel) {
		
		this.name = name;
		this.pass = pass;
		this.email = email;
		this.adress = adress;
		this.points=points;
		this.tel=tel;
	}
	public Client() {
		
	}
	public int getId_client() {
		return id_client;
	}
	public void setId_client(int id_client) {
		this.id_client = id_client;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
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
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	

}
