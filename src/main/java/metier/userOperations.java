package metier;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import DAO.Client;
import DAO.Commande;
import DAO.CommandeSpec;
import DAO.Menu;
import DAO.Offre;
import DAO.User;

public class userOperations {

	private static ConnexionBase con;
	private static Connection Connect;

	public userOperations() {
		con = new ConnexionBase();
		Connect = con.getConnection();
	}
	public ArrayList<Offre> consulterOffres(User user) {
		ArrayList<Offre> offres = new ArrayList<Offre>();
		String query = "SELECT * FROM offres WHERE id_restaurateur = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = this.Connect.prepareStatement(query);
			st.setInt(1,this.getUserId(user));
			rs = st.executeQuery();
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
	public String getCommandeState(CommandeSpec commande) {
		String commandeState = "Aucun";
		String query = "SELECT state FROM commande WHERE id_commande=?";
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = this.Connect.prepareStatement(query);
			st.setInt(1, commande.getId_commande());
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
	public boolean changerState(String state,CommandeSpec commande) {
		boolean b=false;
		String query = "UPDATE commande SET state=? WHERE id_commande= ?";
		PreparedStatement st = null;
		int r = 0;

		try {

			st = this.Connect.prepareStatement(query);
			st.setString(1, state);
			st.setInt(2, commande.getId_commande());


			r = st.executeUpdate();
			st.close();
			b = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	public boolean removeCommande(CommandeSpec commande) {
		boolean b=false;
		String query = "DELETE FROM commande WHERE id_commande= ?";
		PreparedStatement st = null;
		int r = 0;

		try {

			st = this.Connect.prepareStatement(query);
			
			st.setInt(1, commande.getId_commande());


			r = st.executeUpdate();
			st.close();
			b = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
    public boolean actualiserCommande(CommandeSpec commande) {
    	boolean b=false;
    	
			
				
				switch(this.getCommandeState(commande)) {
				case "En attente":if(this.changerState("En préparation", commande))b=true;break;
				case "En préparation":if(this.changerState("Prête", commande))b=true;break;
				case "Prête":if(this.changerState("En attente de livraison", commande))b=true;break;
				case "En attente de livraison":if(this.changerState("En cours de livraison", commande))b=true;break;
				case "En cours de livraison":if(this.changerState("Livrée", commande))b=true;break;
				case "Livrée":if(this.removeCommande(commande))return true;break;
				}
				
    	return b;
    }
	public int getUserId(User x) {
		String query = "SELECT id_utilisateur FROM utilisateurs WHERE email LIKE ? AND pass LIKE ?";
		PreparedStatement st = null;
		ResultSet r = null;
		int id = 0;

		try {
			st = this.Connect.prepareStatement(query);
			st.setString(1, x.getEmail());
			st.setString(2, x.getPass());

			r = st.executeQuery();

			if (r.next())
				id = Integer.parseInt(r.getString("id_utilisateur"));
			st.close();
			r.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
    public ArrayList<CommandeSpec> consulterMesCommandes(User user){
    	ArrayList<CommandeSpec> commandeSpec=new ArrayList<CommandeSpec>();
    	String query ="SELECT commande.id_commande,commande.state, clients.email,clients.adress,menus.items,commande.prix From commande,menus,clients WHERE commande.id_client=clients.id_client AND commande.code_menu=menus.code_menu AND commande.id_restaurateur=? AND commande.state!='En attente' AND commande.state!='En attente de livraison' AND commande.state!='En cours de livraison' AND commande.state!='Livrée'";
    	PreparedStatement st=null;
    	ResultSet rs=null;
    	try {
			st=this.Connect.prepareStatement(query);
			st.setInt(1,this.getUserId(user));
			rs=st.executeQuery();
			while(rs.next()) {
				commandeSpec.add(new CommandeSpec(Integer.parseInt(rs.getString("id_commande")),rs.getString("email"),rs.getString("adress"),rs.getString("items"),Integer.parseInt(rs.getString("prix")),rs.getString("state")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return commandeSpec;
    }
    public ArrayList<CommandeSpec> consulterMesCommandesAcc(User user){
    	ArrayList<CommandeSpec> commandeSpec=new ArrayList<CommandeSpec>();
    	String query ="SELECT commande.id_commande,commande.state, clients.email,clients.adress,menus.items,commande.prix From commande,menus,clients WHERE commande.id_client=clients.id_client AND commande.code_menu=menus.code_menu AND commande.id_restaurateur=? AND commande.state='En attente'";
    	PreparedStatement st=null;
    	ResultSet rs=null;
    	try {
			st=this.Connect.prepareStatement(query);
			st.setInt(1,this.getUserId(user));
			rs=st.executeQuery();
			while(rs.next()) {
				commandeSpec.add(new CommandeSpec(Integer.parseInt(rs.getString("id_commande")),rs.getString("email"),rs.getString("adress"),rs.getString("items"),Integer.parseInt(rs.getString("prix")),rs.getString("state")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return commandeSpec;
    }
     public ArrayList<CommandeSpec> consulterMesCommandesAccLivrer(){
    	ArrayList<CommandeSpec> commandeSpec=new ArrayList<CommandeSpec>();
    	String query ="SELECT commande.id_commande,commande.state, clients.email,clients.adress,menus.items,commande.prix From commande,menus,clients WHERE commande.id_client=clients.id_client AND commande.code_menu=menus.code_menu  AND commande.state='En attente de livraison'";
    	PreparedStatement st=null;
    	ResultSet rs=null;
    	try {
			st=this.Connect.prepareStatement(query);
			
			rs=st.executeQuery();
			while(rs.next()) {
				commandeSpec.add(new CommandeSpec(Integer.parseInt(rs.getString("id_commande")),rs.getString("email"),rs.getString("adress"),rs.getString("items"),Integer.parseInt(rs.getString("prix")),rs.getString("state")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return commandeSpec;
    }
     public ArrayList<CommandeSpec> consulterMesCommandesLivrer(User user){
    	 
     	ArrayList<CommandeSpec> commandeSpec=new ArrayList<CommandeSpec>();
     	String query ="SELECT commande.id_commande,commande.state, clients.email,clients.adress,menus.items,commande.prix From commande,menus,clients WHERE commande.id_client=clients.id_client AND commande.code_menu=menus.code_menu AND commande.id_livreur=? AND commande.state!='En attente' AND commande.state!='En attente de livraison'";
     	PreparedStatement st=null;
     	ResultSet rs=null;
     	try {
 			st=this.Connect.prepareStatement(query);
 			st.setInt(1,this.getUserId(user));
 			rs=st.executeQuery();
 			while(rs.next()) {
 				commandeSpec.add(new CommandeSpec(Integer.parseInt(rs.getString("id_commande")),rs.getString("email"),rs.getString("adress"),rs.getString("items"),Integer.parseInt(rs.getString("prix")),rs.getString("state")));
 			}
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
     	return commandeSpec;
     }
     public boolean actualiserCommandeLivrer(User user,CommandeSpec commande) {
    	 boolean b=false;
    	 String query="UPDATE commande SET id_livreur=? WHERE id_commande=?";
    	 PreparedStatement st = null;
 		int r = 0;

 		try {

 			st = this.Connect.prepareStatement(query);
 			st.setInt(1, this.getUserId(user));
 			st.setInt(2, commande.getId_commande());


 			r = st.executeUpdate();
 			st.close();
 			b = true;

 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    	 return b;
     }
	public boolean authentifyRes(User x) {
		String query = "SELECT * FROM utilisateurs WHERE email LIKE ? AND pass LIKE ? AND role LIKE 'Restaurateur'";
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
	public boolean authentifyLivr(User x) {
		String query = "SELECT * FROM utilisateurs WHERE email LIKE ? AND pass LIKE ? AND role LIKE 'Livreur'";
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

	

	public ArrayList<Menu> consulterMesMenus(User user) {
		ArrayList<Menu> menus = new ArrayList<Menu>();
		String query = "SELECT * FROM menus WHERE id_restaurateur = ?";
		PreparedStatement st = null;
		ResultSet r = null;
		try {
			st = this.Connect.prepareStatement(query);
			st.setInt(1, this.getUserId(user));
			r = st.executeQuery();
			while (r.next()) {
				menus.add(new Menu(r.getString("items"), Integer.parseInt(r.getString("code_menu")),
						Integer.parseInt(r.getString("prix")), r.getString("categorie")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menus;

	}
	public ArrayList<Menu> consulterMesMenus(User user,String categorie) {
		ArrayList<Menu> menus = new ArrayList<Menu>();
		String query = "SELECT * FROM menus WHERE id_restaurateur = ? AND categorie LIKE ?";
		PreparedStatement st = null;
		ResultSet r = null;
		try {
			st = this.Connect.prepareStatement(query);
			st.setInt(1, this.getUserId(user));
			st.setString(2, categorie);
			r = st.executeQuery();
			while (r.next()) {
				menus.add(new Menu(r.getString("items"), Integer.parseInt(r.getString("code_menu")),
						Integer.parseInt(r.getString("prix")), r.getString("categorie")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menus;

	}
	public ArrayList<Menu> consulterMesMenus(User user,int prix) {
		ArrayList<Menu> menus = new ArrayList<Menu>();
		String query = "SELECT * FROM menus WHERE id_restaurateur = ? AND prix <= ?";
		PreparedStatement st = null;
		ResultSet r = null;
		try {
			st = this.Connect.prepareStatement(query);
			st.setInt(1, this.getUserId(user));
			st.setInt(2, prix);
			r = st.executeQuery();
			while (r.next()) {
				menus.add(new Menu(r.getString("items"), Integer.parseInt(r.getString("code_menu")),
						Integer.parseInt(r.getString("prix")), r.getString("categorie")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menus;

	}
	

	public boolean ajouterMenu(Menu menu) {
		String query1 = "SELECT * FROM menus WHERE prix = ? AND items LIKE ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		boolean exist = false;

		try {
			st = this.Connect.prepareStatement(query1);
			st.setInt(1, menu.getPrix());
			st.setString(2, menu.getItems());

			rs = st.executeQuery();
			if (rs.next())
				exist = true;
			st.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean b = false;
		String query = "INSERT INTO menus(id_restaurateur,items,prix,categorie) VALUES (?, ?, ?, ?)";

		int r = 0;
		if (!exist) {
			try {

				st = this.Connect.prepareStatement(query);
				st.setInt(1, menu.getId_restaurateur());
				st.setString(2, menu.getItems());
				st.setInt(3, menu.getPrix());
				st.setString(4, menu.getCategorie());
				r = st.executeUpdate();
				st.close();
				b = true;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}

	public ArrayList<Offre> consulterMonOffres(User user) {
		ArrayList<Offre> offres = new ArrayList<Offre>();
		String query = "SELECT * FROM offres WHERE id_restaurateur = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = this.Connect.prepareStatement(query);
			st.setInt(1, this.getUserId(user));
			rs = st.executeQuery();
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

	public boolean ajouterOffre(Offre offre, User user) {
		boolean b = false;
		String query = "INSERT INTO offres(Id_restaurateur, description, discount, points, operation, date_range, dateD, dateF) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement st = null;
		int r = 0;
		LocalDate offreDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd]");

		java.util.Date dateD = java.sql.Date.valueOf(LocalDate.parse(offreDate.toString(), formatter));
		java.util.Date dateF = java.sql.Date.valueOf(LocalDate.parse(offre.getDateF().toString(), formatter));
		try {
			if (authentifyRes(user)) {
				st = this.Connect.prepareStatement(query);

				st.setInt(1, this.getUserId(user));
				st.setString(2, offre.getDescription());
				st.setFloat(3, offre.getDiscount());
				st.setInt(4, offre.getPoints());
				st.setString(5, offre.getOperation());
				st.setInt(6, offre.getDate_range());
				st.setDate(7, (Date) dateD);
				st.setDate(8, (Date) dateF);

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

	public boolean modifierMenu(Menu menuP, Menu menu) {
		boolean b = false;

		String query = "UPDATE menus SET items=?,prix=?,categorie=? WHERE code_menu = ?";
		PreparedStatement st = null;
		int r = 0;

		try {

			st = this.Connect.prepareStatement(query);
			st.setString(1, menu.getItems());

			st.setInt(2, menu.getPrix());
			st.setString(3, menu.getCategorie());
			st.setInt(4, menu.getCode_menu());

			r = st.executeUpdate();
			st.close();
			b = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
}
