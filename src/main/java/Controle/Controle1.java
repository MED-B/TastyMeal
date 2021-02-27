package Controle;

import java.net.MulticastSocket;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import DAO.Client;
import DAO.Offre;
import DAO.User;
import metier.ClientOperations;
import metier.userOperations;

@Controller
public class Controle1 {
	@GetMapping("/")
	public String show() {
		return "IndexTastyMeal";
	}

	@GetMapping("/EspaceRestaurateur")
	public String show1(HttpSession session) {
		userOperations cop = new userOperations();
		session.setAttribute("nbeComEnAtt", cop.nbrComEnAtt());
		session.setAttribute("nbeComEnLivr", cop.nbrComEnLivr());
		return "redirect:/AuthentifierRes";
	}
	@GetMapping("/EspaceLivreur")
	public String show2(HttpSession session) {
		ClientOperations cop = new ClientOperations();
		session.setAttribute("nbeComEnAtt", cop.nbrComEnAtt());
		session.setAttribute("nbeComEnLivr", cop.nbrComEnLivr());
		return "redirect:/AuthentifierLivr";
	}

	@GetMapping("/EspaceClient")
	public ModelAndView showMenus(Model model, HttpSession session) {
		ClientOperations cop = new ClientOperations();
		Offre offre = null;
		Client client = null;

		if (session.getAttribute("offreDesc") != null) {
			offre = cop.findOffre((String) session.getAttribute("offreDesc"));
		}
		session.removeAttribute("Verror");
		session.removeAttribute("error");
        if (session.getAttribute("promoWanted") == null)
			session.setAttribute("promoWanted", false);

	
		session.setAttribute("nbeComEnAtt", cop.nbrComEnAtt());
		session.setAttribute("nbeComEnLivr", cop.nbrComEnLivr());

		ArrayList<Offre> offres = new ArrayList<Offre>();

		for (int i = 0; i < (cop.consulterOffres().size()); i++) {
			offres.add(cop.consulterOffres().get(i));
			
		}
		if (session.getAttribute("client") != null) {

			client = (Client) session.getAttribute("client");
			
			for (int i = 0; i < offres.size(); i++) {
				
				if (!cop.isOffreExpired(offres.get(i))) {
					
					if (cop.isOffreAccepted(offres.get(i), client)) {
						System.out.println("offre accepted");
						offres.remove(i);
						i=0;
					} 
				}
			}
		}

		if (offre != null) {

			
			String message = ("l'offre que vous avez sélectionnée est valable pour vous, vous pouvez obtenir une réduction de "
					+ offre.getDiscount() + "%");
			session.setAttribute("messageAccepete", message);
			session.setAttribute("discount", offre.getDiscount());
		}
		model.addAttribute("list", offres);
		return new ModelAndView("EspaceClient2", "offre", new Offre());

	}
}
