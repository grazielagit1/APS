import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.*;

public class StoreView extends JFrame {
    private StoreController controller;
    private final JPanel gamesPanel; 
    private final JScrollPane scrollPane;
    private final JTextField searchField; 
    private final NumberFormat currencyFormatter;
    private List<Game> currentGames; 

    private static final Color DARK_BLUE_BG = new Color(15, 25, 45); 

    public StoreView() {
        setTitle("🎮 PixelHaus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        
        getContentPane().setBackground(DARK_BLUE_BG);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        topPanel.setBackground(DARK_BLUE_BG);
        
        this.searchField = new JTextField(30);
        this.searchField.setBorder(BorderFactory.createTitledBorder("Pesquisar Jogo (Digite e Pressione Enter)"));
        ((javax.swing.border.TitledBorder)this.searchField.getBorder()).setTitleColor(Color.WHITE);
        this.searchField.setBackground(DARK_BLUE_BG.darker());
        this.searchField.setForeground(Color.WHITE);
        
        this.searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (controller != null) {
                        controller.searchGames(searchField.getText());
                    }
                }
            }
        });

        JButton exitButton = new JButton("Sair do Site");
        exitButton.addActionListener(e -> System.exit(0)); 
        exitButton.setBackground(new Color(200, 50, 50));
        exitButton.setForeground(Color.WHITE);

        topPanel.add(this.searchField, BorderLayout.CENTER);
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonWrapper.setBackground(DARK_BLUE_BG);
        buttonWrapper.add(exitButton);
        topPanel.add(buttonWrapper, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // --- Área Central (Vitrine de Jogos) ---
        this.gamesPanel = new JPanel();
        this.gamesPanel.setLayout(new GridLayout(0, 5, 20, 20)); 
        this.gamesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.gamesPanel.setBackground(DARK_BLUE_BG);

        this.scrollPane = new JScrollPane(gamesPanel);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.getVerticalScrollBar().setUnitIncrement(16); 
        this.scrollPane.getViewport().setBackground(DARK_BLUE_BG);

        add(scrollPane, BorderLayout.CENTER);

        setSize(1400, 800); 
        setLocationRelativeTo(null);
    }
    
    public void setController(StoreController controller) {
        this.controller = controller;
        if (controller != null) controller.loadGameList();
    }

    public NumberFormat getCurrencyFormatter() {
        return currencyFormatter;
    }

    public void displayGames(List<Game> games) {
        this.currentGames = games;
        gamesPanel.removeAll(); 

        if (games.isEmpty()) {
             JLabel noResult = new JLabel("Nenhum jogo encontrado para a pesquisa.");
             noResult.setFont(new Font("Arial", Font.BOLD, 18));
             noResult.setForeground(Color.WHITE);
             JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
             wrapper.setBackground(DARK_BLUE_BG);
             wrapper.add(noResult);
             gamesPanel.add(wrapper);
        } else {
             for (Game game : games) {
                 gamesPanel.add(createGameCard(game));
             }
        }
        
        SwingUtilities.invokeLater(() -> {
            gamesPanel.revalidate(); 
            gamesPanel.repaint(); 
            scrollPane.getVerticalScrollBar().setValue(0); 
        });
    }

    private JPanel createGameCard(Game game) {
        JPanel card = new JPanel(new BorderLayout(5, 5)); 
        card.setPreferredSize(new Dimension(280, 420)); 
        card.setBorder(BorderFactory.createLineBorder(new Color(50, 70, 100), 1));
        card.setBackground(new Color(30, 40, 60));

        // --- Imagem (Top) ---
        JLabel imageLabel = new JLabel("Imagem Indisponível", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(280, 250)); 
        
        ImageIcon icon = loadGameImage(game.getImageFileName(), 280, 250);
        
        imageLabel.setBorder(null); 
        
        if (icon.getIconWidth() > 0) {
             imageLabel.setIcon(icon); 
             imageLabel.setText(""); 
             imageLabel.setBackground(Color.BLACK);
        } else {
             imageLabel.setBackground(Color.DARK_GRAY.darker());
             imageLabel.setForeground(Color.RED);
        }

        imageLabel.setOpaque(true);
        imageLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        card.add(imageLabel, BorderLayout.NORTH);

        // --- Detalhes (Centro/Baixo) ---
        JPanel detailsPanel = new JPanel(new BorderLayout(5, 5));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        detailsPanel.setBackground(card.getBackground());

        // Nome
        JLabel nameLabel = new JLabel("<html><b>" + game.getName() + "</b></html>");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);

        // Preço
        JLabel priceLabel = new JLabel(currencyFormatter.format(game.getPrice()), SwingConstants.RIGHT);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        // Se o preço for exatamente 0.00, usa cor verde para indicar "GRÁTIS"
        priceLabel.setForeground(game.getPrice().doubleValue() == 0.00 ? new Color(100, 255, 100) : new Color(100, 200, 255));
        
        // Painel para Nome e Preço
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(card.getBackground());
        headerPanel.add(nameLabel, BorderLayout.WEST);
        headerPanel.add(priceLabel, BorderLayout.EAST);
        detailsPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Descrição
        JTextArea descriptionText = new JTextArea(trimDescription(game.getDescription()));
        descriptionText.setLineWrap(true);
        descriptionText.setWrapStyleWord(true);
        descriptionText.setEditable(false);
        descriptionText.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionText.setBackground(card.getBackground());
        descriptionText.setForeground(new Color(180, 180, 180));
        descriptionText.setRows(3);

        JScrollPane descriptionScrollPane = new JScrollPane(descriptionText);
        descriptionScrollPane.setBorder(null);
        descriptionScrollPane.getViewport().setBackground(card.getBackground());
        
        // Adiciona a descrição no Centro
        detailsPanel.add(descriptionScrollPane, BorderLayout.CENTER);
        
        // --- Botão de Download/Compra ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(card.getBackground());
        
        String buttonText;
        Color buttonColor;
        
        // Lógica do botão baseada no preço 0.00
        if (game.getPrice().doubleValue() == 0.00) {
            buttonText = "Baixar Grátis ⬇️";
            buttonColor = new Color(50, 200, 50); // Verde
        } else {
            buttonText = "Comprar por " + currencyFormatter.format(game.getPrice());
            buttonColor = new Color(50, 100, 250); // Azul
        }

        JButton downloadButton = new JButton(buttonText);
        downloadButton.setFont(new Font("Arial", Font.BOLD, 14));
        downloadButton.setBackground(buttonColor);
        downloadButton.setForeground(Color.WHITE);
        downloadButton.setFocusPainted(false);
        
        // Ação do Botão: Chama o Controller
        downloadButton.addActionListener(e -> {
            if (controller != null) {
                controller.handleGameAction(game);
            }
        });

        buttonPanel.add(downloadButton);
        detailsPanel.add(buttonPanel, BorderLayout.SOUTH);

        card.add(detailsPanel, BorderLayout.CENTER);

        // ***************************************************************
        // CORREÇÃO DO TOOLTIP: Aplica a dica a todos os subcomponentes
        // ***************************************************************
        String fullTooltip = "<html><p width='300'><b>" + game.getName() + "</b><br>" + game.getDescription() + "</p></html>";
        
        applyTooltip(card, fullTooltip);
        applyTooltip(imageLabel, fullTooltip);
        applyTooltip(detailsPanel, fullTooltip);
        applyTooltip(nameLabel, fullTooltip);
        applyTooltip(priceLabel, fullTooltip);
        applyTooltip(headerPanel, fullTooltip);
        applyTooltip(descriptionText, fullTooltip);
        applyTooltip(descriptionScrollPane, fullTooltip);
        applyTooltip(downloadButton, fullTooltip);

        return card;
    }

    private String trimDescription(String description) {
        if (description.length() > 100) {
            return description.substring(0, 97) + "...";
        }
        return description;
    }
    
    /**
     * Aplica o ToolTipText e um MouseListener a um componente para garantir
     * que o tooltip do card principal seja exibido, mesmo sobre subcomponentes.
     */
    private void applyTooltip(JComponent component, String tooltipText) {
        component.setToolTipText(tooltipText);
        
        // O MouseListener força o tooltip do card principal a aparecer
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JComponent source = (JComponent) e.getSource();
                Container parentCard = source.getParent();
                
                // Busca o JPanel principal (o card) subindo na hierarquia
                while (parentCard != null && !(parentCard instanceof JPanel && parentCard.getPreferredSize().width == 280)) {
                    parentCard = parentCard.getParent();
                }
                
                if (parentCard != null) {
                    // Força o ToolTipManager a usar a dica do componente pai (o card)
                    ToolTipManager.sharedInstance().mouseEntered(
                        new MouseEvent(parentCard, 
                                       MouseEvent.MOUSE_ENTERED, 
                                       e.getWhen(), 
                                       e.getModifiersEx(), 
                                       e.getX(), 
                                       e.getY(), 
                                       e.getClickCount(), 
                                       e.isPopupTrigger())
                    );
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                 ToolTipManager.sharedInstance().mouseExited(e);
            }
        });
    }

    /**
     * Redimensionamento para PREENCHIMENTO (zoom/crop).
     */
    private ImageIcon loadGameImage(String imageFileName, int targetWidth, int targetHeight) {
        if (imageFileName == null) {
            return new ImageIcon(); 
        }
        
        String fullPath = "images/" + imageFileName;
        
        URL imgURL = getClass().getResource("/" + fullPath);
        ImageIcon icon = null;
        
        if (imgURL != null) {
            icon = new ImageIcon(imgURL);
        } else {
             icon = new ImageIcon(fullPath); 
        }

        if (icon == null || icon.getIconWidth() <= 0) {
            return new ImageIcon(); 
        }

        Image img = icon.getImage();
        int originalWidth = img.getWidth(null);
        int originalHeight = img.getHeight(null);
        
        double widthRatio = (double) targetWidth / originalWidth;
        double heightRatio = (double) targetHeight / originalHeight;
        
        // Usa Math.max para preencher o espaço (fazendo um zoom e recorte)
        double ratio = Math.max(widthRatio, heightRatio); 

        int newWidth = (int) (originalWidth * ratio);
        int newHeight = (int) (originalHeight * ratio);

        Image scaled = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StoreView view = new StoreView();
            StoreController controller = new StoreController(view);
            view.setController(controller);
            view.setVisible(true);
        });
    }
}