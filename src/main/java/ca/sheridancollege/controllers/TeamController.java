package ca.sheridancollege.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ca.sheridancollege.beans.Player;
import ca.sheridancollege.beans.Team;
import ca.sheridancollege.repositories.PlayerRepository;
import ca.sheridancollege.repositories.TeamRepository;

@Controller
public class TeamController {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	//Get all team members
	public ArrayList<Player> getPlayers() {
		Iterable<Player> playerIterator = playerRepository.findAll();
		ArrayList<Player> allPlayers = new ArrayList<>();
		
		playerIterator.forEach(allPlayers::add);
		
		return allPlayers;
	}
	
	//Determine number of teams
	public int numberOfTeams(int numOfPlayers) {
		int teams = 1;
		
		for(int i = 4; i < 9; i++) {
			if(numOfPlayers % (i + 1) <= 1) {
				teams = i;
				return teams;
			}
		}
		return teams;
	}
	
	
	//Determine number of players per team
	public int playersPerTeam(int numOfPlayers, int teams) {
		int modResult = numOfPlayers % teams;
		int ppt = (numOfPlayers - modResult) / teams;
		
		return ppt;
	}
	
	public void sortTeams() {
		int players = getPlayers().size();
		int teams = numberOfTeams(players);
		int teamSize = playersPerTeam(players, teams);
		ArrayList<Player> allPlayers = getPlayers();
		
		//Add Players to each team
		for(int i = 0; i < teams; i++) {
				Team team = new Team();
				
				for(int j = 0; j < teamSize - 1; j++) {
					team.getPlayers().add(allPlayers.get(j));
					allPlayers.get(j).setTeam(team);
					
					if(!allPlayers.isEmpty()) {
						allPlayers.remove(j);
					}
				}
				teamRepository.save(team);
		}
		
		//Remaining Players
		Iterable<Team> teamIterator = teamRepository.findAll();
		ArrayList<Team> allTeams = new ArrayList<>();
		teamIterator.forEach(allTeams::add);
		int j = 0;
		
		for(Player i : allPlayers) {
			allTeams.get(j).getPlayers().add(i);
			i.setTeam(allTeams.get(j));
			teamRepository.save(allTeams.get(j));
			
			if(j == allTeams.size() - 1) {
				j = 0;
			} else {
				j++;
			}
		}
	}
	
	@GetMapping("/viewTeams")
	public String goView(Model model) {
		boolean playersSet = true;
		for (Player i : playerRepository.findAll()) {
			if (i.getTeam() == null) {
				playersSet = false;
			}
			break;
		}
		
		if (playersSet == false) {
			sortTeams();
		}
		
		model.addAttribute("myTeams", teamRepository.findAll());
		model.addAttribute("myPlayers", playerRepository.findAll());
		
		return "viewTeams.html";
	}
}
