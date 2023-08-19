package cn.linjk.testignite.rest;

import cn.linjk.testignite.bean.Player;
import cn.linjk.testignite.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class PlayerController {
    @Autowired private PlayerRepository playerRepository;

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @PostMapping("/player")
    public Player createPlayer(@RequestBody Player player) {
        return playerRepository.save(player);
    }

    @GetMapping("/players/{id}")
    public Player getPlayerById(@PathVariable(value = "id") Integer id) {
        return playerRepository.findById(id).orElse(null);
    }

    @PutMapping("/players/{id}")
    public Player updatePlayer(@PathVariable(value = "id") Integer id, @RequestBody Player details) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player " + id + " not found"));

        player.setClub(details.getClub());
        player.setName(details.getName());
        player.setWages(details.getWages());

        Player updatedPlayer = playerRepository.save(player);

        return updatedPlayer;
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable(value = "id") Integer id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player " + id + " not found"));
        playerRepository.delete(player);

        return ResponseEntity.ok().build();
    }
}
