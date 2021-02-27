package Controle;

import metier.ClientOperations;
import metier.userOperations;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;

import DAO.CommandeSpec;
import DAO.Menu;
import DAO.Offre;
import DAO.User;

@Controller
public class LoginController {

	@GetMapping("/consulterMenusRes")
	public ModelAndView showMenus(Model model, HttpSession session) {
		userOperations cop = new userOperations();
		User user = (User) session.getAttribute("user");
		cop.consulterMesMenus(user);
		session.setAttribute("categorie", "Tous les menus");
		model.addAttribute("list", cop.consulterMesMenus(user));
		return new ModelAndView("AfficherMenuRes", "menu", new Menu());

	}

	@GetMapping("/pizzaRes")
	public ModelAndView showMenusPizza(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		userOperations cop = new userOperations();
		cop.consulterMesMenus(user, "Pizza");
		session.setAttribute("categorie", "Pizza");
		model.addAttribute("list", cop.consulterMesMenus(user, "Pizza"));
		return new ModelAndView("AfficherMenuRes", "menu", new Menu());

	}

	@GetMapping("/burgerRes")
	public ModelAndView showMenusBurger(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		userOperations cop = new userOperations();
		cop.consulterMesMenus(user, "Burger");
		session.setAttribute("categorie", "Burger");
		model.addAttribute("list", cop.consulterMesMenus(user, "Burger"));
		return new ModelAndView("AfficherMenuRes", "menu", new Menu());

	}

	@GetMapping("/traditionnelRes")
	public ModelAndView showMenusTraditionnel(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		userOperations cop = new userOperations();
		cop.consulterMesMenus(user, "Traditionnel");
		session.setAttribute("categorie", "Traditionnel");
		model.addAttribute("list", cop.consulterMesMenus(user, "Traditionnel"));
		return new ModelAndView("AfficherMenuRes", "menu", new Menu());

	}

	@GetMapping("/grillageRes")
	public ModelAndView showMenusGrillage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		userOperations cop = new userOperations();
		cop.consulterMesMenus(user, "Grillage");
		session.setAttribute("categorie", "Grillage");
		model.addAttribute("list", cop.consulterMesMenus(user, "Grillage"));
		return new ModelAndView("AfficherMenuRes", "menu", new Menu());

	}

	@RequestMapping(value = "/consulterPrixRes", method = RequestMethod.POST)
	public ModelAndView consulterPrix(@ModelAttribute("menu") Menu menu, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		userOperations cop = new userOperations();
		cop.consulterMesMenus(user, "Grillage");
		session.setAttribute("categorie", "Prix");
		model.addAttribute("list", cop.consulterMesMenus(user, menu.getPrix()));

		return new ModelAndView("AfficherMenuRes", "menu", new Menu());

	}

	@RequestMapping(value = "/AuthentifierRes", method = RequestMethod.GET)
	public ModelAndView showForm(HttpSession session, Model model) {
		if (session.getAttribute("user") == null)
			return new ModelAndView("AthentifRestaurateur", "user", new User());
		else {
			User user = (User) session.getAttribute("user");
			userOperations uop = new userOperations();
			model.addAttribute("list", uop.consulterMesCommandesAcc(user));
			return new ModelAndView("MenuRestaurateur", "commandeSpec", new CommandeSpec());
		}
	}
	@RequestMapping(value = "/AuthentifierLivr", method = RequestMethod.GET)
	public ModelAndView showFormL(HttpSession session, Model model) {
		if (session.getAttribute("userLivr") == null)
			return new ModelAndView("AuthentiLivreur", "user", new User());
		else {
			User user = (User) session.getAttribute("userLivr");
			userOperations uop = new userOperations();
			model.addAttribute("list", uop.consulterMesCommandesAccLivrer());
			return new ModelAndView("EspaceLivreur", "commandeSpec", new CommandeSpec());
		}
	}
	@RequestMapping(value = "/addLivreur", method = RequestMethod.POST)
	public ModelAndView submit2 (@ModelAttribute("user") User user, Model model, HttpSession session) {
		userOperations uop = new userOperations();

		if (session.getAttribute("rep") == null)
			session.setAttribute("rep", 0);
		int rep = (int) session.getAttribute("rep");
		if (rep >= 2) {
			session.invalidate();
			return new ModelAndView("redirect:/", "", null);
		}
		if (uop.authentifyLivr(user)) {
			session.setAttribute("name", user.getName());
			session.setAttribute("resId", uop.getUserId(user));
			session.setAttribute("userLivr", user);

			model.addAttribute("list", uop.consulterMesCommandesAccLivrer());
			return new ModelAndView("EspaceLivreur", "commandeSpec", new CommandeSpec());
		} else {
			rep++;
			session.setAttribute("rep", rep);
			return new ModelAndView("redirect:/AuthentifierLivr", "", null);
		}

	}
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ModelAndView submit(@ModelAttribute("user") User user, Model model, HttpSession session) {
		userOperations uop = new userOperations();

		if (session.getAttribute("rep") == null)
			session.setAttribute("rep", 0);
		int rep = (int) session.getAttribute("rep");
		if (rep >= 2) {
			session.invalidate();
			return new ModelAndView("redirect:/", "", null);
		}
		if (uop.authentifyRes(user)) {
			session.setAttribute("name", user.getName());
			session.setAttribute("resId", uop.getUserId(user));
			session.setAttribute("user", user);

			model.addAttribute("list", uop.consulterMesCommandesAcc(user));
			return new ModelAndView("MenuRestaurateur", "commandeSpec", new CommandeSpec());
		} else {
			rep++;
			session.setAttribute("rep", rep);
			return new ModelAndView("redirect:/AuthentifierRes", "", null);
		}

	}

	@RequestMapping(value = "/ajouterMenu", method = RequestMethod.GET)
	public ModelAndView ajouterMenu() {
		return new ModelAndView("AjouterMenu", "menu", new Menu());
	}

	@RequestMapping(value = "/addMenu", method = RequestMethod.POST)
	public String FormMenu(@ModelAttribute("menu") Menu menu, Model model, HttpSession session) {
		userOperations uop = new userOperations();

		if (uop.ajouterMenu(menu)) {
			return "redirect:/consulterMenusRes";
		} else
			return "redirect:/ajouterMenu";

	}

	@RequestMapping(value = "/ChangerMenu", method = RequestMethod.POST)
	public ModelAndView changerMenu(@ModelAttribute("menu") Menu menu, HttpSession session, Model model) {
		session.setAttribute("menuChanger", menu);
		ArrayList<Menu> menus = new ArrayList<Menu>();
		menus.add(menu);
		model.addAttribute("menuachanger", menus);
		return new ModelAndView("ChangerMenu", "menu", new Menu());
	}

	@RequestMapping(value = "/modifierMenu", method = RequestMethod.POST)
	public String FormMenu2(@ModelAttribute("menu") Menu menu, Model model, HttpSession session) {
		userOperations uop = new userOperations();
		Menu menuP = (Menu) session.getAttribute("menuChanger");
		if (uop.modifierMenu(menuP, menu)) {
			return "redirect:/consulterMenusRes";
		} else
			return "redirect:/ChangerMenu";

	}

	@RequestMapping(value = "/Offrir", method = RequestMethod.GET)
	public ModelAndView Offrir() {
		return new ModelAndView("FormOffre", "offre", new Offre());
	}

	@RequestMapping(value = "/addOffre", method = RequestMethod.POST)
	public String FormOffre(@ModelAttribute("offre") Offre offre, Model model, HttpSession session) {
		userOperations uop = new userOperations();

		if (session.getAttribute("user") == null) {
			return "redirect:/AuthentifierRes";
		} else {
			User user = (User) session.getAttribute("user");

			if (uop.ajouterOffre(offre, user))
				System.out.println("done");
			else
				System.out.println("error!");
			return "redirect:/AuthentifierRes";
		}

	}

	@RequestMapping(value = "/consulterCommande", method = RequestMethod.GET)
	public ModelAndView mesCommandes(Model model, HttpSession session) {
		if (session.getAttribute("user") == null)
			return new ModelAndView("AthentifRestaurateur", "user", new User());
		else {
			User user = (User) session.getAttribute("user");
			userOperations uop = new userOperations();
			model.addAttribute("list", uop.consulterMesCommandes(user));
			return new ModelAndView("MesCommandes", "commandeSpec", new CommandeSpec());
		}
	}
	@RequestMapping(value = "/actualiserCommande", method = RequestMethod.POST)
	public ModelAndView ActualiserCommande(@ModelAttribute("commandeSpec")CommandeSpec commande,Model model, HttpSession session) {
		if (session.getAttribute("user") == null)
			return new ModelAndView("AthentifRestaurateur", "user", new User());
		else {
			User user = (User) session.getAttribute("user");
			userOperations uop = new userOperations();
			uop.actualiserCommande(commande);
			model.addAttribute("list", uop.consulterMesCommandes(user));
			return new ModelAndView("MesCommandes", "commandeSpec", new CommandeSpec());
		}
	}
	@RequestMapping(value = "/consulterCommandeLivr", method = RequestMethod.GET)
	public ModelAndView mesCommandesLivr(Model model, HttpSession session) {
		if (session.getAttribute("userLivr") == null)
			return new ModelAndView("AuthentiLivreur", "user", new User());
		else {
			User user = (User) session.getAttribute("userLivr");
			userOperations uop = new userOperations();
			model.addAttribute("list", uop.consulterMesCommandesLivrer(user));
			return new ModelAndView("Livraison", "commandeSpec", new CommandeSpec());
		}
	}
	@RequestMapping(value = "/actualiserCommandeLivr", method = RequestMethod.POST)
	public ModelAndView ActualiserCommandeLivrer(@ModelAttribute("commandeSpec")CommandeSpec commande,Model model, HttpSession session) {
		if (session.getAttribute("userLivr") == null)
			return new ModelAndView("AuthentiLivreur", "user", new User());
		else {
			User user = (User) session.getAttribute("userLivr");
			userOperations uop = new userOperations();
			uop.actualiserCommande(commande);
			uop.actualiserCommandeLivrer(user,commande);
			model.addAttribute("list", uop.consulterMesCommandesLivrer(user));
			return new ModelAndView("Livraison", "commandeSpec", new CommandeSpec());
		}
	}
	@RequestMapping(value = "/consulterOffres", method = RequestMethod.GET)
	public ModelAndView mesOffres(Model model, HttpSession session) {
		if (session.getAttribute("user") == null)
			return new ModelAndView("AthentifRestaurateur", "user", new User());
		else {
			User user = (User) session.getAttribute("user");
			userOperations uop = new userOperations();
			model.addAttribute("list", uop.consulterMonOffres(user));
			return new ModelAndView("MesOffres", "offre", new Offre());
		}
	}

}
