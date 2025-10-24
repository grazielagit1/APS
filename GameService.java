import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class GameService {
    public List<Game> getFeaturedGames() {
        return Arrays.asList(
            new Game(1, "Valorant", BigDecimal.valueOf(59.90), "Tiro Tático Competitivo. FPS 5v5 focado em precisão e habilidades únicas de agentes, exigindo estratégia em cada rodada.", "valorant.jpg"),
            new Game(4, "Minecraft", BigDecimal.valueOf(99.99), "Aventura em Blocos e Sobrevivência. Um sandbox onde você explora, constrói e sobrevive em um vasto mundo gerado proceduralmente.", "minecraft.jpg"),
            new Game(2, "League Of Legends", BigDecimal.valueOf(47.50), "Arena de Batalha Multijogador (MOBA). Equipes 5v5 competem para destruir a base inimiga, controlando campeões com poderes únicos.", "lol.jpg"),
            new Game(13, "Free Fire", BigDecimal.valueOf(19.99), "Battle Royale de Sobrevivência Rápida. Salte de paraquedas, procure armas e seja o último sobrevivente em intensas partidas mobile e PC. (GRÁTIS!)", "freefire.jpg"), 
            new Game(5, "Terraria", BigDecimal.valueOf(36.99), "Aventura 2D de Exploração e Criação. Um 'Minecraft 2D' com foco em combate contra chefes, mineração, construção e descoberta de biomas.", "terraria.jpg"),
            new Game(6, "Roblox", BigDecimal.valueOf(29.90), "Plataforma de Criação de Jogos e Experiências. Milhões de jogos criados por usuários em um mundo virtual massivo.", "roblox.jpg"),
            new Game(11, "Dead By Daylight", BigDecimal.valueOf(49.99), "Terror Assimétrico 4v1. Quatro sobreviventes tentam escapar de um assassino implacável em um cenário de horror.", "dbd.jpg"),
            new Game(14, "Stardew Valley", BigDecimal.valueOf(24.99), "Simulador de Fazenda e RPG. Herde uma velha fazenda e transforme-a em um lar próspero, cultivando, minerando e interagindo com a comunidade.", "stardew.jpg"),
            new Game(3, "Overwatch 2", BigDecimal.valueOf(69.99), "Hero Shooter 5v5. FPS de ritmo acelerado onde você escolhe entre diversos heróis com papéis e habilidades distintas para completar objetivos. (GRÁTIS!)", "overwatch2.jpg"), 
            new Game(12, "Hogwarts Legacy", BigDecimal.valueOf(249.99), "RPG de Ação em Mundo Aberto. Viva a sua própria aventura no Mundo Bruxo do século XIX, explorando Hogwarts e arredores.", "HogwartsLegacy.jpg"),
            new Game(7, "Battlefield 2042", BigDecimal.valueOf(199.90), "Guerra em Grande Escala. FPS focado em combates épicos de até 128 jogadores, com veículos e destruição de cenários.", "Battlefield2042.jpg"),
            new Game(8, "Call Of Duty: MW III", BigDecimal.valueOf(319.00), "FPS Cinematográfico e Multijogador Viciante. Campanha militar intensa, modo zumbi e um modo multijogador competitivo e rápido.", "cod.jpg"),
            new Game(9, "EA Sports FC 25", BigDecimal.valueOf(329.00), "Simulador de Futebol Realista. Jogo anual de esportes com times, ligas e atletas reais. Construa seu time no Ultimate Team!", "fc.jpg"),
            new Game(10, "Counter-Strike 2", BigDecimal.valueOf(79.90), "O Clássico FPS Tático. Jogo de tiro 5v5 focado em eliminar a equipe inimiga ou cumprir objetivos.", "cs2.jpg"),
            new Game(15, "Hollow Knight", BigDecimal.valueOf(27.99), "Metroidvania 2D de Fantasia Sombria. Explore um vasto e interconectado reino em ruínas, lutando contra criaturas e descobrindo mistérios.", "hollow-knight.jpg")
        );
    }
}