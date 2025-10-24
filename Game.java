import java.math.BigDecimal;

public class Game {
    private final int id;
    private final String name;
    private final BigDecimal price;
    private final String description;
    private final String imageFileName;

    public Game(int id, String name, BigDecimal price, String description, String imageFileName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageFileName = imageFileName;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImageFileName() { return imageFileName; } 
}