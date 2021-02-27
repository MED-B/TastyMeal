package metier;

import java.util.ArrayList;

import java.util.*;

import org.springframework.ui.Model;

import java.sql.*;
import java.sql.Date;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import DAO.Client;
import DAO.Commande;
import DAO.Menu;
import DAO.Offre;
import DAO.User;

public class ClientOperations {
	private static ConnexionBase con;
	private static Connection Connect;

	public ClientOperations() {
		con = new ConnexionBase();
		Connect = con.getConnection();
	}
	public boolean reduirePrix(Offre offre,Client client) {
		boolean b=false;
		
		String queryAll = "UPDATE commande SET prix = ? WHERE id_client=? AND prix = ?";
		PreparedStatement st = null;
		int r = 0;

		try {
			st = this.Connect.prepareStatement(queryAll);
			st.setInt(1, (int) (this.getCommandePrix(client)-((this.getCommandePrix(client)*offre.getDiscount())/100)));
			st.setInt(2, this.getClientId(client));
			st.setInt(3, this.getCommandePrix(client));


			r = st.executeUpdate();
			if (r != 0)
				b = true;
			st.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return b;
	}
	public int getCommandePrix(Client client) {
		int prix=0;
		String query="SELECT * FROM commande WHERE id_client=?";
		PreparedStatement st = null;
		ResultSet r = null;
		int id = 0;
		
		try {
			st = this.Connect.prepareStatement(query);
			st.setInt(1, this.getClientId(client));

			r = st.executeQuery();

			if (r.next())
				prix=Integer.parseInt(r.getString("prix"));
			
			st.close();
			r.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prix;
	}
    public boolean isOffreExpired(Offre offre) {
    	boolean b=true;
    	String query = "SELECT dateF FROM offres WHERE description LIKE ?";
		PreparedStatement st = null;
		ResultSet r = null;
		int id = 0;
		LocalDate nowDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd]");

		java.util.Date date2 = java.sql.Date.valueOf(LocalDate.parse(nowDate.toString(), formatter));
		try {
			st = this.Connect.prepareStatement(query);
			st.setString(1, offre.getDescription());

			r = st.executeQuery();

			if (r.next())
				if(date2.before(java.sql.Date.valueOf(r.getString("dateF")))) b=false;
			
			st.close();
			r.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(b) {
			
			 query = "DELETE FROM offres WHERE description LIKE ?";
			 st = null;
			 
			 
			try {
				st = this.Connect.prepareStatement(query);
				st.setString(1, offre.getDescription());

				int rs = st.executeUpdate();

				
				st.close();
				

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

    	return b;
    }
	public boolean accepteeOffre(Offre offre, Client client) {
		boolean b = false;
		String queryAll = "INSERT INTO offreacceptee(id_offre,id_client) VALUES(?, ?)";
		PreparedStatement st = null;
		int r = 0;

		try {
			st = this.Connect.prepareStatement(queryAll);
			st.setInt(1, offre.getId());
			st.setInt(2, this.getClientId(client));

			if (!this.isOffreAccepted(offre, client)) {
				r = st.executeUpdate();

				b = true;
			}
			st.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public int getOffreID(Offre offre) {
		String query = "SELECT id_offre FROM offres WHERE description LIKE ?";
		PreparedStatement st = null;
		ResultSet r = null;
		int id = 0;

		try {
			st = this.Connect.prepareStatement(query);
			st.setString(1, offre.getDescription());

			r = st.executeQuery();

			if (r.next())
				id = Integer.parseInt(r.getString("id_offre"));
			st.close();
			r.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id;
	}

	public boolean isOffreAccepted(Offre offre, Client client) {
		boolean b = false;
		String query = "SELECT * FROM offreacceptee WHERE id_offre= ? AND id_client=?";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = this.Connect.prepareStatement(query);
			st.setInt(1, this.getOffreID(offre));
			st.setInt(2, this.getClientId(client));
			rs = st.executeQuery();
			if (rs.next()) {
				b = true;
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public Offre findOffre(String description) {
		Offre offre = null;
		String query = "SELECT * FROM offres WHERE description LIKE ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = this.Connect.prepareStatement(query);
			st.setString(1, description);
			rs = st.executeQuery();
			while (rs.next()) {
				offre = new Offre(Integer.parseInt(rs.getString("id_offre")),
						Integer.parseInt(rs.getString("id_restaurateur")), rs.getString("description"),
						Float.parseFloat(rs.getString("discount")), Integer.parseInt(rs.getString("points")),
						rs.getString("operation"), Integer.parseInt(rs.getString("date_range")), rs.getString("dateD"),
						rs.getString("dateF"));
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return offre;
	}

	public ArrayList<Menu> consulterMenus() {

		ArrayList<Menu> menus = new ArrayList<Menu>();
		String query = "SELECT * FROM menus ";
		Statement st = null;
		ResultSet rs = null;

		try {

			st = this.Connect.prepareStatement(query);

			rs = st.executeQuery(query);

			while (rs.next()) {
				menus.add(new Menu(Integer.parseInt(rs.getString("code_menu")),
						Integer.parseInt(rs.getString("id_restaurateur")), rs.getString("items"),
						Integer.parseInt(rs.getString("prix")), rs.getString("categorie")));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menus;

	}

	public ArrayList<Menu> consulterMenus(String categorie) {

		ArrayList<Menu> menus = new ArrayList<Menu>();
		String query = "SELECT * FROM menus WHERE categorie LIKE ?";
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = this.Connect.prepareStatement(query);
			st.setString(1, categorie);
			rs = st.executeQuery();

			while (rs.next()) {
				menus.add(new Menu(Integer.parseInt(rs.getString("code_menu")),
						Integer.parseInt(rs.getString("id_restaurateur")), rs.getString("items"),
						Integer.parseInt(rs.getString("prix")), rs.getString("categorie")));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menus;

	}

	public ArrayList<Menu> consulterMenusPrix(int prix) {

		ArrayList<Menu> menus = new ArrayList<Menu>();
		String query = "SELECT * FROM menus WHERE prix <= ?";
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = this.Connect.prepareStatement(query);
			st.setInt(1, prix);
			rs = st.executeQuery();

			while (rs.next()) {
				menus.add(new Menu(Integer.parseInt(rs.getString("code_menu")),
						Integer.parseInt(rs.getString("id_restaurateur")), rs.getString("items"),
						Integer.parseInt(rs.getString("prix")), rs.getString("categorie")));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menus;

	}

	public ArrayList<Offre> consulterOffres() {
		ArrayList<Offre> offres = new ArrayList<Offre>();
		String query = "SELECT * FROM offres";
		Statement st = null;
		ResultSet rs = null;
		try {
			st = this.Connect.prepareStatement(query);
			rs = st.executeQuery(query);
			while (rs.next()) {
				offres.add(new Offre(Integer.parseInt(rs.getString("id_offre")),
						Integer.parseInt(rs.getString("id_restaurateur")), rs.getString("description"),
						Float.parseFloat(rs.getString("discount")), Integer.parseInt(rs.getString("points")),
						rs.getString("operation"), Integer.parseInt(rs.getString("date_range")), rs.getString("dateD"),
						rs.getString("dateF")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return offres;
	}

	public String getCommandeState(Client client) {
		String commandeState = "aucun";
		String query = "SELECT * FROM commande WHERE id_client = ?";
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = this.Connect.prepareStatement(query);
			st.setInt(1, getClientId(client));
			rs = st.executeQuery();

			if (rs.next()) {
				commandeState = rs.getNString("state");
			}
			st.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commandeState;
	}

	public boolean authentify(Client x) {
		String query = "SELECT * FROM clients WHERE email = ? AND pass = ? ";
		PreparedStatement st = null;
		ResultSet r = null;
		boolean b = false;
		try {
			st = this.Connect.prepareStatement(query);
			st.setString(1, x.getEmail());
			st.setString(2, x.getPass());

			r = st.executeQuery();
			if (r.next())
				b = true;
			st.close();
			r.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public int nbrComEnAtt() {
		String query = "SELECT * FROM commande WHERE state LIKE 'En attente'";
		PreparedStatement st = null;
		ResultSet r = null;
		int b = 0;
		try {
			st = this.Connect.prepareStatement(query);

			r = st.executeQuery();
			while (r.next())
				b++;
			st.close();
			r.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	public int nbrComEnLivr() {
		String query = "SELECT * FROM commande WHERE state LIKE 'En cours de livraison'";
		PreparedStatement st = null;
		ResultSet r = null;
		int b = 0;
		try {
			st = this.Connect.prepareStatement(query);

			r = st.executeQuery();
			while (r.next())
				b++;
			st.close();
			r.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public boolean ajouterCinqPoints(Client x) {
		boolean b = false;
		String queryAll = "UPDATE clients SET points = ? WHERE name LIKE ? AND pass LIKE ?";
		PreparedStatement st = null;
		int r = 0;

		try {
			st = this.Connect.prepareStatement(queryAll);
			st.setInt(1, this.getClientPoints(x) + 5);
			st.setString(2, x.getName());

			st.setString(3, x.getPass());

			r = st.executeUpdate();
			if (r != 0)
				b = true;
			st.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public int getClientPoints(Client x) {
		int points = 0;
		String query = "SELECT * FROM clients WHERE name LIKE ? AND pass LIKE ?";
		PreparedStatement st = null;
		ResultSet r = null;

		try {
			st = this.Connect.prepareStatement(query);
			st.setString(1, x.getName());
			st.setString(2, x.getPass());

			r = st.executeQuery();

			if (r.next())
				points = Integer.parseInt(r.getString("points"));
			st.close();
			r.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return points;
	}

	public int getClientId(Client x) {
		String query = "SELECT id_client FROM clients WHERE email LIKE ? ";
		PreparedStatement st = null;
		ResultSet r = null;
		int id = 0;

		try {
			st = this.Connect.prepareStatement(query);
			st.setString(1, x.getEmail());
			

			r = st.executeQuery();

			if (r.next())
				id = Integer.parseInt(r.getString("id_client"));
			st.close();
			r.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id;

	}

	public Client getClientById(int id) {
		Client client = null;
		String query = "SELECT * FROM  clients WHERE id_client = ?";
		PreparedStatement st = null;
		ResultSet r = null;

		try {
			st = this.Connect.prepareStatement(query);
			st.setInt(1, id);

			r = st.executeQuery();

			if (r.next())
				client = new Client(r.getString("name"), r.getString("pass"), r.getString("email"),
						r.getString("adress"), Integer.parseInt(r.getString("points")), r.getString("tel"));
			st.close();
			r.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
	}
    public boolean ajouterCommandeGuest(Commande commande) {
    	boolean b=false;
    	// pour chercher de commande
    			String query = "SELECT * FROM commande WHERE code_menu = ? AND id_restaurateur = ? AND id_client= ?";
    			PreparedStatement st = null;
    			ResultSet r = null;
    			boolean exist = false;
    			
    			try {
    				st = this.Connect.prepareStatement(query);
    				st.setInt(1, commande.getCode_menu());
    				st.setInt(2, commande.getId_restaurateur());
    				st.setInt(3, commande.getId_client());

    				r = st.executeQuery();
    				if (r.next())
    					exist = true;
    				st.close();
    				r.close();
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			// pour ajouter de commande
    			if (!exist) {
    				query = "INSERT INTO commande(id_client, code_menu, id_restaurateur, date, prix) VALUES (?, ?, ?, ?, ?)";
    				st = null;
    				int rs = 0;

    				LocalDate commandeDate = LocalDate.now();
    				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd]");

    				java.util.Date date2 = java.sql.Date.valueOf(LocalDate.parse("2021-03-04", formatter));
                     
    				try {

    					st = this.Connect.prepareStatement(query);
    					st.setInt(1, commande.getId_client());
    					st.setInt(2, commande.getCode_menu());
    					st.setInt(3, commande.getId_restaurateur());
    					st.setDate(4, (Date) date2);
    					st.setInt(5, commande.getPrix());
    					rs = st.executeUpdate();
    					st.close();
    					
    					b = true;

    				} catch (SQLException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    	return b;
    }
	public boolean ajouterCommande(Commande commande) {
		// pour chercher de commande
		String query = "SELECT * FROM commande WHERE code_menu = ? AND id_restaurateur = ? AND id_client= ?";
		PreparedStatement st = null;
		ResultSet r = null;
		boolean exist = false;
		boolean b = false;
		try {
			st = this.Connect.prepareStatement(query);
			st.setInt(1, commande.getCode_menu());
			st.setInt(2, commande.getId_restaurateur());
			st.setInt(3, commande.getId_client());

			r = st.executeQuery();
			if (r.next())
				exist = true;
			st.close();
			r.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// pour ajouter de commande
		if (!exist) {
			query = "INSERT INTO commande(id_client, code_menu, id_restaurateur, date, prix) VALUES (?, ?, ?, ?, ?)";
			st = null;
			int rs = 0;

			LocalDate commandeDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd]");

			java.util.Date date2 = java.sql.Date.valueOf(LocalDate.parse("2021-03-04", formatter));

			try {

				st = this.Connect.prepareStatement(query);
				st.setInt(1, commande.getId_client());
				st.setInt(2, commande.getCode_menu());
				st.setInt(3, commande.getId_restaurateur());
				st.setDate(4, (Date) date2);
				st.setInt(5, commande.getPrix());
				rs = st.executeUpdate();
				st.close();
				this.ajouterCinqPoints(this.getClientById(commande.getId_client()));
				b = true;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return b;
	}

	public boolean addClient(Client x) {
		String query = "INSERT INTO clients(name, pass,points,email,adress,tel) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement st = null;
		int r = 0;
		boolean b = false;
		try {
			if (!authentify(x)) {
				st = this.Connect.prepareStatement(query);
				st.setString(1, x.getName());
				st.setString(2, x.getPass());
				st.setInt(3, 0);
				st.setString(4, x.getEmail());
				st.setString(5, x.getAdress());
				st.setString(6, x.getTel());

				r = st.executeUpdate();
				st.close();
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	public boolean addGuest(Client x) {
		String query = "INSERT INTO clients(name, pass,points,email,adress,tel) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement st = null;
		int r = 0;
		boolean b = false;
		try {
			if (!authentify(x)) {
				st = this.Connect.prepareStatement(query);
				st.setString(1, "Guest");
				st.setString(2, "Guest");
				st.setInt(3, 0);
				st.setString(4, x.getEmail());
				st.setString(5, x.getAdress());
				st.setString(6, x.getTel());

				r = st.executeUpdate();
				st.close();
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public boolean findEmail(String x) {
		String query = "SELECT * FROM clients WHERE email LIKE ? AND pass !='Guest'";
		PreparedStatement st = null;
		ResultSet r = null;
		boolean b = false;
		try {
			st = this.Connect.prepareStatement(query);
			st.setString(1, x);

			r = st.executeQuery();
			if (r.next())
				b = true;
			st.close();
			r.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public ArrayList<java.util.Date> allCommandesFromClientWithRes(Client client, Offre offre) {
		ArrayList<java.util.Date> dates = new ArrayList<java.util.Date>();
		String query = "SELECT * FROM commande WHERE id_client = ? AND id_restaurateur = ? ";
		PreparedStatement st = null;
		ResultSet rs = null;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd]");
		try {
			st = this.Connect.prepareStatement(query);

			st.setInt(1, this.getClientId(client));
			st.setInt(2, offre.getId_restaurateur());

			rs = st.executeQuery();
			while (rs.next()) {

				dates.add(java.sql.Date.valueOf(LocalDate.parse(rs.getString("date"), formatter)));

			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dates;
	}

	public java.util.Date getDateDfromOffre(Offre offre) {
		java.util.Date date = null;

		String query = "SELECT * FROM offres WHERE id_restaurateur=?";
		PreparedStatement st = null;
		ResultSet rs = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd]");
		try {
			st = this.Connect.prepareStatement(query);

			st.setInt(1, offre.getId_restaurateur());

			rs = st.executeQuery(query);
			while (rs.next()) {

				date = java.sql.Date.valueOf(LocalDate.parse(rs.getString("dateD"), formatter));

			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;
	}

	public boolean VerifierPromotion(Offre offre, Client client) {
		
		boolean b = false;
		java.util.Date[] datesT = new java.util.Date[this.allCommandesFromClientWithRes(client, offre).size()];

		for (int i = 0; i < datesT.length; i++) {
			datesT[i] = this.allCommandesFromClientWithRes(client, offre).get(i);
			

		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd]");
		if (offre.getDateD() == null) {
			
			if (this.VerifyDates(datesT, offre.getDate_range(), offre.getPoints(), this.getDateDfromOffre(offre),
					java.sql.Date.valueOf(LocalDate.parse(offre.getDateF().toString(), formatter)),
					offre.getOperation())) {
				
				b = true;
			} else
				b = false;
		} else {
			
			if (this.VerifyDates(datesT, offre.getDate_range(), offre.getPoints(),
					java.sql.Date.valueOf(LocalDate.parse(offre.getDateD().toString(), formatter)),
					java.sql.Date.valueOf(LocalDate.parse(offre.getDateF().toString(), formatter)),
					offre.getOperation())) {
				
				b = true;
			} else
				
				b = false;
		}
		return b;
	}

	public boolean forMonths2(java.util.Date[] dates, int value, int points, java.util.Date dateD,
			java.util.Date dateF) {
		boolean b = false;
		int i = 0;
		int co = 1;
		int point = 0;
		int valeurOriginal = value;
		LocalDate date;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd-MM-yyyy][yyyy-MM-dd]");
		ArrayList<java.util.Date> dates2 = new ArrayList<java.util.Date>();
		
		for (i = 0; i < dates.length; i++) {
			
			if (dates[i].before(dateF) && dates[i].after(dateD)) {
				
				if (LocalDate.parse(dates[i].toString(), formatter).getYear() == LocalDate
						.parse(dateD.toString(), formatter).getYear()) {
					point = point + 5;
					dates2.add(dates[i]);
					System.out.println(dates[i].toString());
				}
			}

		}
		if (dates2.size() == 0) {
			
			if ((co * 5) >= points)
				return true;
			else
				return false;
		} else {
			i = 0;

			if (dates2.get(i).before(dateF) && dates2.get(i).after(dateD)) {
				
				int j = i + 1;
				while (j < dates2.size()) {
					date = LocalDate.parse(dates2.get(i).toString(), formatter);
					int firstMonth = date.getMonthValue();
					if (LocalDate.parse(dates2.get(j).toString(), formatter).getMonthValue() == firstMonth) {
						
						i = j;
					} else {
						if (LocalDate.parse(dates2.get(j).toString(), formatter).getMonthValue()
								- firstMonth <= value) {
							i = j;
							value = value - (LocalDate.parse(dates2.get(j).toString(), formatter).getMonthValue()
									- firstMonth);
							if ((co * 5) >= points) {
								b = true;
								j = dates2.size();
								i = j;
							} else {
								if (value <= 0) {
									co = 0;
									i = j;
								}
							}
						} else {
							firstMonth = LocalDate.parse(dates2.get(j).toString(), formatter).getMonthValue();
							value = valeurOriginal;
							co = 0;
							i = j;
						}
					}
					co++;
					j++;

				}
			}
		}
		int pointR = co * 5;
		if (pointR >= points) {
			b = true;
		}
		System.out.println("points are : "+pointR);
		return b;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean forDaysFinal(java.util.Date[] dates, int value, int points, java.util.Date dateD,
			java.util.Date dateF) {
		boolean b = false;
		int i = 0;
		int co = 1;

		int valeurOriginal = value;
		LocalDate date;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd-MM-yyyy][yyyy-MM-dd]");
		ArrayList<java.util.Date> dates2 = new ArrayList<java.util.Date>();
		while (i < dates.length) {
			if (dates[i].before(dateF) && dates[i].after(dateD)) {
				if (LocalDate.parse(dates[i].toString(), formatter).getYear() == LocalDate
						.parse(dateD.toString(), formatter).getYear()) {
					dates2.add(dates[i]);
				}
			}
			i++;
		}
		if (dates2.size() == 0) {
			if ((co * 5) >= points)
				return true;
			else
				return false;
		}
		i = 0;
		int j = i + 1;
		while (j < dates2.size()) {
			co++;
			date = LocalDate.parse(dates2.get(i).toString(), formatter);
			int firstDay = date.getDayOfYear();
			if (LocalDate.parse(dates2.get(j).toString(), formatter).getDayOfYear() == firstDay) {
				co++;
				i = j;
			} else {
				if (LocalDate.parse(dates2.get(j).toString(), formatter).getDayOfYear() - firstDay <= value) {
					co++;
					i = j;
					value = value - (LocalDate.parse(dates2.get(j).toString(), formatter).getDayOfYear() - firstDay);
					if ((co * 5) >= points) {
						b = true;
						j = dates2.size();
						i = j;
					} else {
						if (value <= 0) {
							co = 0;
							value = valeurOriginal;
							i = j;
						}
					}
				} else {
					firstDay = LocalDate.parse(dates2.get(j).toString(), formatter).getDayOfYear();
					value = valeurOriginal;
					co = 0;
					i = j;
				}
			}
			j++;
		}
		int pointR = co * 5;
		if (pointR >= points) {
			b = true;
		}
		return b;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean forYearsFinal(java.util.Date[] dates, int value, int points, java.util.Date dateD,
			java.util.Date dateF) {
		boolean b = false;
		int i = 0;
		int co = 1;
		int point = 0;
		int valeurOriginal = value;
		LocalDate date;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd-MM-yyyy][yyyy-MM-dd]");
		ArrayList<java.util.Date> dates2 = new ArrayList<java.util.Date>();
		for (i = 0; i < dates.length; i++) {
			if (dates[i].before(dateF) && dates[i].after(dateD)) {
				point = point + 5;
				dates2.add(dates[i]);

			}

		}
		if (dates2.size() == 0) {
			if ((co * 5) >= points)
				return true;
			else
				return false;
		}
		i = 0;
		int j = i + 1;
		while (j < dates2.size()) {
			co++;
			date = LocalDate.parse(dates2.get(i).toString(), formatter);
			int firstYear = date.getYear();
			if (LocalDate.parse(dates2.get(j).toString(), formatter).getYear() == firstYear) {
				co++;
				i = j;
			} else {
				if (LocalDate.parse(dates2.get(j).toString(), formatter).getYear() - firstYear <= value) {
					co++;
					i = j;
					value = value - (LocalDate.parse(dates2.get(j).toString(), formatter).getYear() - firstYear);
					if ((co * 5) >= points) {
						b = true;
						j = dates2.size();
						i = j;
					} else {
						if (value <= 0) {
							co = 0;
							value = valeurOriginal;
							i = j;
						}
					}
				} else {
					firstYear = LocalDate.parse(dates2.get(j).toString(), formatter).getYear();
					value = valeurOriginal;
					co = 0;
					i = j;
				}
			}
			j++;
		}
		int pointR = co * 5;
		if (pointR >= points) {
			b = true;
		}
		return b;
	}

////////////////////////////////////////////////////////////////////////////
	public boolean VerifyDates(java.util.Date[] dates, int value, int points, java.util.Date dateD,
			java.util.Date dateF, String operation) {
		boolean b = false;
		int i = 0;
		int co = 0;
		int point = 0;
		int valeurOriginal = value;
		LocalDate date;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd-MM-yyyy][yyyy-MM-dd]");
		ArrayList<java.util.Date> dates2 = new ArrayList<java.util.Date>();
		
		for (i = 0; i < dates.length; i++) {
       
			if (dates[i].before(dateF) && dates[i].after(dateD)) {
				point = point + 5;
				dates2.add(dates[i]);
				
			}

		}
//we start by years
		switch (operation) {
		case "D":
			
			b = (this.forDaysFinal(dates, value, points, dateD, dateF));
			
			break;
		case "M":
			
			b = (this.forMonths2(dates, value, points, dateD, dateF));
			
			break;
		case "Y":
			
			b = this.forYearsFinal(dates, value, points, dateD, dateF);
			
			break;
		}

		return b;
	}

}
