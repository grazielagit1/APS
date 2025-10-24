import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.ToolTipManager; 

public class StoreController {
    
    private final GameService gameService;
    private final StoreView view;
    private List<Game> allGames; 

    public StoreController(StoreView view) {
        this.gameService = new GameService();
        this.view = view;
        this.allGames = gameService.getFeaturedGames(); 
    }

    public void loadGameList() {
        view.displayGames(this.allGames);
    }

    public void searchGames(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            view.displayGames(this.allGames);
            return;
        }

        String lowerCaseSearch = searchText.toLowerCase().trim();

        List<Game> filteredGames = this.allGames.stream()
            .filter(game -> 
                game.getName().toLowerCase().contains(lowerCaseSearch) || 
                game.getDescription().toLowerCase().contains(lowerCaseSearch) 
            )
            .collect(Collectors.toList());

        view.displayGames(filteredGames);
    }
    public void handleGameAction(Game game) {
        String message;
        
        // Toda ação é tratada como "Processando compra..."
        message = "Processando compra de " + game.getName() + " por " + 
                  view.getCurrencyFormatter().format(game.getPrice()) + ".\n\nObrigado por comprar!";
        
        // 1. Exibe o feedback ao usuário (bloqueia o programa)
        JOptionPane.showMessageDialog(
            view, 
            message, 
            "Ação de Jogo Concluída", 
            JOptionPane.INFORMATION_MESSAGE
        );

        ToolTipManager.sharedInstance().setEnabled(false);
        ToolTipManager.sharedInstance().setEnabled(true);
    }
}