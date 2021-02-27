package Controle;

import metier.ClientOperations;

import metier.userOperations;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import DAO.Client;
import DAO.Commande;
import DAO.Menu;
import DAO.Offre;
import DAO.User;

import metier.ClientOperations;

@Controller

public class ControleurClient {

	@GetMapping("/consulterMenus")
	public ModelAndView showMenus(Model model,HttpSession session) {
		ClientOperations cop = new ClientOperations();
		cop.consulterMenus();
		session.setAttribute("categorie", "Tous les menus");
		model.addAttribute("list", cop.consulterMenus());
		return new ModelAndView("AfficherMenus", "menu", new Menu());

	}
	@GetMapping("/pizza")
	public ModelAndView showMenusPizza(Model model,HttpSession session) {
		ClientOperations cop = new ClientOperations();
		cop.consulterMenus("Pizza");
		session.setAttribute("categorie", "Pizza");
		model.addAttribute("list", cop.consulterMenus("Pizza"));
		return new ModelAndView("AfficherMenus", "menu", new Menu());

	}
	@GetMapping("/burger")
	public ModelAndView showMenusBurger(Model model,HttpSession session) {
		ClientOperations cop = new ClientOperations();
		cop.consulterMenus("Burger");
		session.setAttribute("categorie", "Burger");
		model.addAttribute("list", cop.consulterMenus("Burger"));
		return new ModelAndView("AfficherMenus", "menu", new Menu());

	}
	@GetMapping("/traditionnel")
	public ModelAndView showMenusTraditionnel(Model model,HttpSession session) {
		ClientOperations cop = new ClientOperations();
		cop.consulterMenus("Traditionnel");
		session.setAttribute("categorie", "Traditionnel");
		model.addAttribute("list", cop.consulterMenus("Traditionnel"));
		return new ModelAndView("AfficherMenus", "menu", new Menu());

	}
	@GetMapping("/grillage")
	public ModelAndView showMenusGrillage(Model model,HttpSession session) {
		ClientOperations cop = new ClientOperations();
		cop.consulterMenus("Grillage");
		session.setAttribute("categorie", "Grillage");
		model.addAttribute("list", cop.consulterMenus("Grillage"));
		return new ModelAndView("AfficherMenus", "menu", new Menu());

	}
	@RequestMapping(value = "consulterPrix", method = RequestMethod.POST)
	public ModelAndView consulterPrix(@ModelAttribute("menu") Menu menu,Model model, HttpSession session) {

		ClientOperations cop = new ClientOperations();
		cop.consulterMenus("Grillage");
		session.setAttribute("categorie", "Prix");
		model.addAttribute("list", cop.consulterMenusPrix(menu.getPrix()));

		return new ModelAndView("AfficherMenus", "menu", new Menu());

	}
    
	@RequestMapping(value = "/passerCommande", method = RequestMethod.POST)
	public ModelAndView commander(@ModelAttribute("menu") Menu menu, HttpSession session) {

		session.setAttribute("menuCommande", menu);

		return new ModelAndView("confirmerCommande", "client", new Client());

	}
	@RequestMapping(value = "/addCommandeGuest", method = RequestMethod.GET)
	public ModelAndView guest(HttpSession session) {
		ClientOperations cop = new ClientOperations();
		return new ModelAndView("FormGuest", "client", new Client());
	}
	@RequestMapping(value = "/confirmerCommandeGuest", method = RequestMethod.POST)
	public String confirmerGuest(@ModelAttribute("client") Client client,HttpSession session) {
		ClientOperations cop = new ClientOperations();
		Menu menu = (Menu) session.getAttribute("menuCommande");
		if (cop.findEmail(client.getEmail())) {
			 
			String error = "E-mail est déjà utilisé";
			
			session.setAttribute("error", error);
			session.setAttribute("Verror", error);
			session.setAttribute("done", false);
			return "redirect:/abonner";

		} else {
			if(cop.addGuest(client)) {
				Commande commande = new Commande(cop.getClientId(client), menu.getCode_menu(), menu.getId_restaurateur(),menu.getPrix());
			   System.out.println("client email : "+client.getEmail());
				System.out.println("id client in controller : "+cop.getClientId(client));
				cop.ajouterCommandeGuest(commande);
			}
			return "redirect:/EspaceClient";
		}
		
	}
	@RequestMapping(value = "/addCommande", method = RequestMethod.POST)
	public String verifier(@ModelAttribute("client") Client client, HttpSession session) {
		//user become client
		ClientOperations cop = new ClientOperations();
		Menu menu = (Menu) session.getAttribute("menuCommande");
       
		Commande commande = new Commande(cop.getClientId(client), menu.getCode_menu(), menu.getId_restaurateur(),menu.getPrix());

		if (session.getAttribute("rep") == null)
			session.setAttribute("rep", 0);
		int repc = (int) session.getAttribute("rep");
		if (repc > 1) {
			session.invalidate();
			return "IndexTastyMeal";
		}
		if (cop.authentify(client)) {
			session.setAttribute("name", client.getName());
			cop.ajouterCommande(commande);
			session.setAttribute("state", cop.getCommandeState(client));
			session.setAttribute("client", client);
			return "redirect:/EspaceClient";
		} else {
			repc++;
			session.setAttribute("rep", repc);
			return "confirmerCommande";
		}

	}

	@RequestMapping(value = "/suivreCommande", method = RequestMethod.GET)
	public ModelAndView authentifier(HttpSession session) {
		ClientOperations cop = new ClientOperations();
		
		if (session.getAttribute("client") == null) {
			
			
			return new ModelAndView("AthentiClient", "client", new Client());
		} else {
			session.setAttribute("state", cop.getCommandeState((Client) session.getAttribute("client")));
			return new ModelAndView("redirect:/EspaceClient", "", null);
		}

	}
	

	@RequestMapping(value = "/authentifier", method = RequestMethod.POST)
	public String verifier1(@ModelAttribute("client") Client client, Model model, HttpSession session) {
		ClientOperations cop = new ClientOperations();
		
		if (session.getAttribute("rep") == null)
			session.setAttribute("rep", 0);
		int rep = (int) session.getAttribute("rep");
		if (rep >= 2) {
			session.invalidate();
			return "redirect:/EspaceClient";
		}
		if (cop.authentify(client)) {
			session.setAttribute("name",client.getName());
			session.setAttribute("", cop);
			session.setAttribute("client", client);
			session.setAttribute("state", cop.getCommandeState((Client) session.getAttribute("client")));
			session.setAttribute("offreAccepte", null);

			return "redirect:/EspaceClient";
		} else {
			rep++;
			session.setAttribute("rep", rep);
			return "redirect:/suivreCommande";
		}

	}

	@RequestMapping(value = "/abonner", method = RequestMethod.GET)
	public ModelAndView showForm(HttpSession session ) {
		
		if(session.getAttribute("Verror")==null)
		{session.setAttribute("error", null);}
		
		return new ModelAndView("Abonnement", "client", new Client());
	}

	@RequestMapping(value = "/addClient", method = RequestMethod.POST)
	public String submit(@ModelAttribute("client") Client client, Model model, HttpSession session) {
		session.removeAttribute("offreDesc");
		session.removeAttribute("promoWanted");
		session.removeAttribute("promo");
		session.removeAttribute("messageAccepete");
		ClientOperations cop = new ClientOperations();
          
		if (cop.findEmail(client.getEmail())) {
			 
			String error = "E-mail est déjà utilisé";
			
			session.setAttribute("error", error);
			session.setAttribute("Verror", error);
			session.setAttribute("done", false);
			return "redirect:/abonner";

		} else {
			if(cop.addClient(client)) {
			

			session.setAttribute("client", client);
			session.setAttribute("name", client.getName());
			session.setAttribute("done", true);
			 session.setAttribute("offreDesc", null);
			session.setAttribute("state", cop.getCommandeState((Client) session.getAttribute("client")));}
			return "redirect:/EspaceClient";
		}

	}

	@RequestMapping(value = "/reclamerPromo", method = RequestMethod.POST)
	public String Promo(@ModelAttribute("offre") Offre offre, Model model, HttpSession session) {
		ClientOperations cop = new ClientOperations();
		Client client = (Client) session.getAttribute("client");

		session.setAttribute("numberO", offre.getId());
		
		if (session.getAttribute("client") != null) {
			
			if (cop.VerifierPromotion(offre, client)) {
				
				if(cop.accepteeOffre(offre, client)) {
					if(cop.reduirePrix(offre, client)){
				session.setAttribute("promo", true);
				session.setAttribute("promoWanted", true);
				session.removeAttribute("offreDesc");
			   session.setAttribute("offreDesc", offre.getDescription());
			   
					}
			   
				}
			   
			    
				

			} else {
				session.setAttribute("promo", false);
				session.setAttribute("promoWanted", true);
				
			}

			return "redirect:/EspaceClient";
		} else {
			
			return "redirect:/suivreCommande";
		}
	}
}
