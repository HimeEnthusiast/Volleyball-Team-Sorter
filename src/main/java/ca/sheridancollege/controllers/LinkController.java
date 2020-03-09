package ca.sheridancollege.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ca.sheridancollege.beans.Player;

@Controller
public class LinkController {
	
	@GetMapping("/")
	public String goHome() {
		return "home.html";
	}
	
	@GetMapping("/search")
	public String goSearch() {
		return "search.html";
	}
	
	@GetMapping("/addPlayer")
	public String goAdd(Model model) {
		model.addAttribute("player", new Player());
		
		return "addPlayer.html";
	}
	
	@GetMapping("/editTeam")
	public String editTeam() {
		return "editTeam.html";
	}
}
