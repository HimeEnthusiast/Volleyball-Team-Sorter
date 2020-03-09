package ca.sheridancollege.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Player;
import ca.sheridancollege.beans.Team;
import ca.sheridancollege.repositories.PlayerRepository;
import ca.sheridancollege.repositories.TeamRepository;

@Controller
public class SearchController {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@GetMapping("/searchName")
	public String goSearchName(@RequestParam String name, Model model) {
		List<Player> players = playerRepository.findByName(name);
		model.addAttribute("myPlayers", players);
		return "search.html";
	}
	
	@GetMapping("/searchAge")
	public String goSearchAge(@RequestParam int age, Model model) {
		List<Player> players = playerRepository.findByAge(age);
		model.addAttribute("myPlayers", players);
		return "search.html";
	}
	
	@GetMapping("/searchGender")
	public String goSearchGender(@RequestParam char gender, Model model) {
		List<Player> players = playerRepository.findByGender(gender);
		model.addAttribute("myPlayers", players);
		return "search.html";
	}
	
	@GetMapping("/searchTeam")
	public String goSearchTeam(@RequestParam int team, Model model) {
		Team teams = teamRepository.findById(team);
		model.addAttribute("myTeam", teams);
		return "search.html";
	}
}
