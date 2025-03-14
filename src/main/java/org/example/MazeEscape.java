import java.util.Scanner;

// Eccezione personalizzata per movimenti fuori dai limiti
class OutOfBoundsException extends Exception {
    public OutOfBoundsException(String message) {
        super(message);
    }
}

// Eccezione personalizzata per collisione con muri
class WallCollisionException extends Exception {
    public WallCollisionException(String message) {
        super(message);
    }
}

public class MazeEscape {
    // Dichiarazione della matrice del labirinto
    private static final char[][] LABIRINTO = {
        { 'P', '.', '#', '.', '.' },
        { '#', '.', '#', '.', '#' },
        { '.', '.', '.', '#', '.' },
        { '#', '#', '.', '.', '.' },
        { '#', '.', '#', '#', 'E' }
    };

    // Coordinate iniziali del giocatore
    private static int playerX = 0;
    private static int playerY = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean escaped = false;

        System.out.println("Benvenuto in Maze Escape! Trova l'uscita ('E').");

        while (!escaped) {
            printMaze();
            System.out.print("Muoviti (W = su, A = sinistra, S = giù, D = destra): ");
            char move = scanner.next().toUpperCase().charAt(0);

            try {
                movePlayer(move);
                if (playerX == 4 && playerY == 4) {
                    System.out.println("Congratulazioni! Hai trovato l'uscita!");
                    escaped = true;
                }
            } catch (OutOfBoundsException | WallCollisionException e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        scanner.close();
    }

    /**
     * Metodo per spostare il giocatore all'interno del labirinto
     */
    private static void movePlayer(char direction) throws OutOfBoundsException, WallCollisionException {
        int newX = playerX;
        int newY = playerY;

        switch (direction) {
            case 'W': newX--; break; // Su
            case 'A': newY--; break; // Sinistra
            case 'S': newX++; break; // Giù
            case 'D': newY++; break; // Destra
            default:
                System.out.println("Mossa non valida! Usa W, A, S, D.");
                return;
        }

        // Controllo dei limiti della matrice
        if (newX < 0 || newX >= LABIRINTO.length || newY < 0 || newY >= LABIRINTO[0].length) {
            throw new OutOfBoundsException("Sei fuori dai limiti del labirinto!");
        }

        // Controllo delle collisioni con i muri
        if (LABIRINTO[newX][newY] == '#') {
            throw new WallCollisionException("Hai colpito un muro! Scegli un'altra direzione.");
        }

        // Aggiornamento della matrice con la nuova posizione del giocatore
        LABIRINTO[playerX][playerY] = '.'; // Liberare la posizione precedente
        playerX = newX;
        playerY = newY;
        LABIRINTO[playerX][playerY] = 'P'; // Nuova posizione del giocatore
    }

    /**
     * Metodo per stampare il labirinto attuale
     */
    private static void printMaze() {
        for (char[] row : LABIRINTO) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
