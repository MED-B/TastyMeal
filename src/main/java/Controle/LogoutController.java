package Controle;
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

import DAO.User;

@Controller
public class LogoutController {

	@GetMapping("/Logout")
	public String showForm(Model model,HttpSession session) {
		
        session.invalidate();
		 return"IndexTastyMeal";
	}
 

}
