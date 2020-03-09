package ca.sheridancollege.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import ca.sheridancollege.beans.Player;
import ca.sheridancollege.beans.Team;
import ca.sheridancollege.repositories.PlayerRepository;
import ca.sheridancollege.repositories.TeamRepository;

@Controller
public class PlayerController {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@GetMapping("/newPlayer")
	public String newPlayer(@ModelAttribute Player player, Model model) {
		if(playerRepository.count() < 65) {
			playerRepository.save(player);
			
			model.addAttribute("player", new Player());
			model.addAttribute("players", playerRepository.findAll());
			
			return "redirect:/viewTeams";
		} else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/edit/{id}")
	public String editPlayer(@PathVariable int id, Model model) {
		if(playerRepository.findById(id).size() > 0) {
			Player player = playerRepository.findById(id).get(0);
			model.addAttribute("player", player);
			return "editPlayer.html";
		} else {
			return "redirect:/viewTeams";
		}
	}
	
	@GetMapping("/modify")
	public String modifyPlayer(@ModelAttribute Player player, Model model) {
		playerRepository.save(player);
		
		return "redirect:/viewTeams";
	}
	
	@GetMapping("/delete/{id}")
	public String deletePlayer(@PathVariable int id, Model model) {
		for (Player i : playerRepository.findAll()) {
			i.setTeam(null);
		}
		
		for (Team j : teamRepository.findAll()) {
			j.setPlayers(null);
		}
		playerRepository.deleteById(id);
		
		
		return "redirect:/viewTeams";
	}
}
