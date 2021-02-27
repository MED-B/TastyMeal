package DAO;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Offre implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int id_restaurateur;
	private String description;
	private float discount;
	private int points;
	private String Operation;
	private int date_range;
	private String dateF;
	private String dateD;

	

	public Offre() {
		// TODO Auto-generated constructor stub
	}

	
	public Offre(int id,int id_restaurateur, String description, float discount, int points, String operation, int date_range,String dateD,
			String dateF) {
		this.id=id;
		this.id_restaurateur = id_restaurateur;
		this.description = description;
		this.discount = discount;
		this.points = points;
		this.Operation = operation;
		this.date_range = date_range;
		this.dateD=dateD;
		this.dateF =dateF;
	}
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getOperation() {
		return Operation;
	}

	public void setOperation(String operation) {
		Operation = operation;
	}

	public int getDate_range() {
		return date_range;
	}

	public void setDate_range(int date_range) {
		this.date_range = date_range;
	}

	public String getDateF() {
		return dateF;
	}

	public void setDateF(String dateF) {
		this.dateF = dateF;
	}

	public String getDateD() {
		return dateD;
	}

	public void setDateD(String dateD) {
		this.dateD = dateD;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_restaurateur() {
		return id_restaurateur;
	}

	public void setId_restaurateur(int id_restaurateur) {
		this.id_restaurateur = id_restaurateur;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj==null)return false;
		else {
        Offre other=(Offre)obj;
	return this.description.equalsIgnoreCase(other.description);}
	}

}
