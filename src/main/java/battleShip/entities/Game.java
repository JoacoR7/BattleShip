package battleShip.entities;

import battleShip.entities.MapElements.Decoy;
import battleShip.entities.MapElements.Island;
import battleShip.entities.MapElements.Water;
import battleShip.entities.Ships.AircraftCarrier;
import battleShip.entities.Ships.Cruiser;
import battleShip.entities.Ships.Submarine;
import battleShip.entities.Ships.Warship;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Random;

/**
 * Esta clase representa un objeto Game, el cual almacena la información de los
 * jugadores y la cantidad de turnos máxima que podrían jugar.
 *
 * @author Joaquin Ruiz.
 */
public class Game {

    private int maxTurns;
    private Player[] players;

    /**
     * Crea una nueva instancia de la clase Game.
     *
     * @param maxTurns máxima cantidad de turnos .
     * @param namePlayer1 nombre del jugador 1.
     * @param namePlayer2 nombre del jugador 2.
     */
    public Game(int maxTurns, String namePlayer1, String namePlayer2) {
        this.maxTurns = maxTurns;
        Player player1 = new Player(namePlayer1);
        Player player2 = new Player(namePlayer2);
        Player[] newPlayers = {player1, player2};
        this.players = newPlayers;
    }

    /**
     * Crea una nueva instancia de la clase Game.
     *
     * @param maxTurns máxima cantidad de turnos.
     * @param players un array de dos objetos Player ya inicializados.
     */
    public Game(int maxTurns, Player[] players) {
        this.maxTurns = maxTurns;
        this.players = players;
    }

    /**
     * Crea una nueva instancia de la clase Game La cantidad máxima de turnos es
     * 0 y no tiene jugadores, tiene la finalidad de ser editado a la hora de
     * jugar.
     */
    public Game() {
        this.maxTurns = 0;
        this.players = null;
    }

    /**
     * Obtiene la cantidad máxima de turnos.
     *
     * @return la cantidad máxima de turnos
     */
    public int getMaxTurns() {
        return maxTurns;
    }

    /**
     * Establece la cantidad máxima de turnos
     *
     * @param maxTurns
     */
    public void setMaxTurns(int maxTurns) {
        this.maxTurns = maxTurns;
    }

    /**
     * Obtiene un array de dos objetos Player
     *
     * @return devuelve un array de Player
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Establece los jugadores
     *
     * @param players
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    //Methods
    /**
     * Llama a initPlayers() y askTurnAmount() para inicializar el objeto de
     * clase Game
     */
    public void startGame() {
        cleanScreen();
        initPlayers(); //Asks players names
        waitXSeconds(1);
        cleanScreen();
        askTurnAmount();
        waitXSeconds(1);
        cleanScreen();
        int islandAmount = askIslandAmount();
        if (islandAmount != 0) {
            initIslands(islandAmount, this.getPlayers()[0]);
            initIslands(islandAmount, this.getPlayers()[1]);
        }
        waitXSeconds(1);
        cleanScreen();
    }

    /**
     * Le pregunta el nombre a cada jugador, crea dos instancias de clase Player
     * y las guarda en el objeto Game.
     */
    public void initPlayers() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¡Bienvenidos al juego de Batalla Naval!");
        System.out.println("Jugador 1, ingrese su nombre: ");
        String nameP1 = scanner.nextLine();
        System.out.println("Jugador 2, ingrese su nombre: ");
        String nameP2 = scanner.nextLine();
        Player player1 = new Player(nameP1);
        Player player2 = new Player(nameP2);
        Player[] newPlayers = {player1, player2};
        this.players = newPlayers;
    }

    public void initIslands(int amount, Player player) {
        Random rand = new Random();
        for (int i = 0; i < amount; i++) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            Cell cell1;
            do {
                cell1 = player.getMap().getCell(x, y);
            } while (cell1 == null);
            new Island(cell1);
        }
    }

    //Ask turn amount and checks if it's correct
    /**
     * Consulta a los jugadores si desean tener un límite de turnos, en caso de
     * ser así, les deja elegir un número entre 1 y 100. Este valor se almacena
     * en el atributo maxTurns de Game.
     */
    public void askTurnAmount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Desean tener un límite de turnos? S/N");
        String option;
        do {
            option = scanner.nextLine().toUpperCase();
            if(!option.equals("S") && !option.equals("N")){
                System.out.println("Opción incorrecta, intente de nuevo");
            }
        } while (!option.equals("S") && !option.equals("N"));
        int turnAmount = 0;
        if (option.equals("S")) {
            System.out.println("¿Cuántos turnos desean jugar como máximo? Recuerda que el "
                    + "\nlímite es 100, mínimo pueden jugar 15 turnos");
            do {
                option = scanner.nextLine();
                try {
                    turnAmount = Integer.parseInt(option);
                } catch (NumberFormatException e) {
                    turnAmount = 0;
                }
                if (turnAmount > 100 || turnAmount < 15) {
                    System.out.println("Opción incorrecta, intente de nuevo");
                }
            } while (turnAmount > 100 || turnAmount < 15);
        } else {
            turnAmount = 100;
        }
        this.maxTurns = turnAmount;
    }

    /**
     * Consulta a los jugadores si quieren tener islas en el mapa o no, en caso
     * afirmativo podrán elegir tener entre 1 a 3 islas, caso contrario tendrán
     * 0 islas.
     *
     * @return Cantidad de islas
     */
    public int askIslandAmount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Desean tener islas en el mapa? S/N");
        String option;
        do {
            option = scanner.nextLine().toUpperCase();
            if (!option.equals("S") && !option.equals("N")) {
                System.out.println("Opción incorrecta, intente de nuevo");
            }
        } while (!option.equals("S") && !option.equals("N"));
        int islandAmount = 0;
        if (option.equals("S")) {
            System.out.println("¿Cuántas islas desean tener? El máximo es 3, y el mínimo es 1");
            do {
                option = scanner.nextLine();
                try {
                    islandAmount = Integer.parseInt(option);
                } catch (NumberFormatException e) {
                    islandAmount = 0;
                }
                if (islandAmount > 3 || islandAmount < 1) {
                    System.out.println("Opción incorrecta, intente de nuevo");
                }
            } while (islandAmount > 3 || islandAmount < 1);
        } else {
            islandAmount = 0;
        }
        return islandAmount;
    }

    /**
     * El jugador debe elegir entre alguna de las opciones disponibles ya sea
     * para ver la información de su mapa o el mapa enemigo, disparar, o cambiar
     * de turno. Para cambiar de turno, tiene que haber disparado antes, sino no
     * podrá hacerlo.
     *
     * @param player jugador actual
     */
    public void showMenu(Player player) {
        Scanner scanner = new Scanner(System.in);
        Player enemy = getOppossitePlayer(player);
        String option;
        boolean shot = false;
        do {
            System.out.println(player.getName() + ", por favor ingrese alguna de las opciones");
            System.out.println("1: ver mi mapa");
            System.out.println("2: ver mapa enemigo");
            System.out.println("3: disparar");
            System.out.println("4: ver mi estado");
            System.out.println("5: terminar turno");
            option = scanner.nextLine();
            System.out.println("");
            if (option.equals("1")) {
                showShipMap(player);
                showShipList(player);
                personalMapWiki();
                waitXSeconds(3);
            } else if (option.equals("2")) {
                showShotsMap(player);
                showShipList(getOppossitePlayer(player));
                enemyMapWiki();
                waitXSeconds(3);
            } else if (option.equals("3")) {
                if (shot) {
                    System.out.println("Ya has disparado ó usado alguna habilidad, espera a tu siguiente turno para realizar otro disparo ó utilizar alguna habilidad");
                } else {
                    ArrayList<Ship> availableShips = availableShipAbilities(player);
                    boolean[] specialShoot = new boolean[]{false, availableShips != null};
                    if (specialShoot[1]) {
                        specialShoot = showAbilityMenu(player, availableShips);
                        shot = specialShoot[0];
                    }
                    if (!specialShoot[1]) {
                        String position = askCoordinates();
                        shot = player.shoot(position, enemy);
                        System.out.println("");
                    }

                }
            } else if (option.equals("4")) {
                System.out.println("Información actual:");
                System.out.println("- Jugador: " + player.getName());
                System.out.println("- Cargas: " + player.getCharges());
                System.out.println("- Barcos: ");
                showShipList(player);

            } else if (option.equals("5")) {
                if (shot) {
                    System.out.println("Turno finalizado");
                } else {
                    System.out.println("No has realizado el tiro, por favor ataca al enemigo antes de terminar tu turno");
                    option = "";
                }
            } else {
                System.out.println("Opción incorrecta, intente de nuevo");
            }

        } while (!option.equals("5"));
        player.setCharges(player.getCharges() + 1);

    }

    /**
     * Le pregunta al usuario si desea usar alguna habilidad o no, en caso de
     * que sí quiera utilizar alguna habilidad, le muestra los barcos
     * disponibles con los cuales puede realizar el disparo especial.
     *
     * @param player
     * @param availableShips
     * @return {boolean1, boolean2} boolean1: verdadero si el disparo fue
     * exitoso, falso si no se pudo realizar el disparo. boolean2: verdadero si
     * quiere realizar el disparo especial, falso en caso contrario.
     */
    public boolean[] showAbilityMenu(Player player, ArrayList<Ship> availableShips) {
        System.out.println("Actualmente puede usar habilidades de los barcos");
        Scanner input = new Scanner(System.in);
        String option = "";
        do {
            System.out.println("¿Desea realizar un disparo con habilidad especial? S/N");
            option = input.nextLine().toUpperCase();
            if (!option.equals("S") && !option.equals("N")) {
                System.out.println("Opción incorrecta, intente de nuevo");
            }
        } while (!option.equals("S") && !option.equals("N"));
        if (option.equals("N")) {
            return new boolean[]{false, false};
        }

        int shipOption = 0;
        do {
            System.out.println("Barcos que puedes utilizar: ");
            Iterator<Ship> availableShipsIterator = availableShips.iterator();
            int i = 1;
            String availableShipsList = "";
            while (availableShipsIterator.hasNext()) {
                Ship availableShip = availableShipsIterator.next();
                System.out.println(i + ": " + getShipName(availableShip).toUpperCase());
                availableShipsList = availableShipsList + i + ": " + getShipName(availableShip).toUpperCase() + "\n";
                i++;
            }
            do {
                System.out.println("¿Desea ver las habilidades de los barcos disponibles? S/N");
                option = input.nextLine().toUpperCase();
                if (option.equals("S")) {
                    System.out.println("Ingrese el barco para ver su habilidad");
                    System.out.println(availableShipsList);
                    String shipOptionString = input.nextLine();
                    try {
                        shipOption = Integer.parseInt(shipOptionString);
                    } catch (NumberFormatException e) {
                        shipOption = -1;
                    }
                    if (shipOption <= 0 || shipOption > availableShips.size()) {
                        System.out.println("Opción incorrecta, intente de nuevo");
                    } else {
                        explainAbility(availableShips.get(shipOption - 1));
                    }
                }
                if (!option.equals("S") && !option.equals("N")) {
                    System.out.println("Opción incorrecta, intente de nuevo");
                }
            } while (!option.equals("N"));

            System.out.println("Ingrese el barco que desea utilizar para realizar el disparo");
            String shipOptionString = input.nextLine();
            try {
                shipOption = Integer.parseInt(shipOptionString);
            } catch (NumberFormatException e) {
                shipOption = -1;
            }
            if (shipOption <= 0 || shipOption > availableShips.size()) {
                System.out.println("Opción incorrecta, intente de nuevo");
            }
        } while (shipOption <= 0 || shipOption > availableShips.size());
        String position = askCoordinates();
        boolean wasShotSuccessful = player.shootAbility(position, getOppossitePlayer(player), availableShips.get(shipOption - 1));
        return new boolean[]{wasShotSuccessful, true};
    }

    /**
     * Le permite al usuario saber qué habilidad tiene cada barco y además
     * muestra un ejemplo gráfico de cada una.
     * @param ship
     */
    public void explainAbility(Ship ship) {
        System.out.println("            HABILIDAD DE " + getShipName(ship).toUpperCase() + "            ");
        if (ship instanceof Submarine) {
            Submarine submarine = new Submarine();
            submarine.showAbility();
            submarine.showExample();
        } else if (ship instanceof Warship) {
            Warship warship = new Warship();
            warship.showAbility();
            warship.showExample();
        } else if (ship instanceof Cruiser) {
            Cruiser cruiser = new Cruiser();
            cruiser.showAbility();
            cruiser.showExample();
        } else if (ship instanceof AircraftCarrier) {
            AircraftCarrier aircraftCarrier = new AircraftCarrier();
            aircraftCarrier.showAbility();
            aircraftCarrier.showExample();
        }
    }

    /**
     * Esta función será llamada desde el main, aquí se llamará a la función
     * startGame(), luego de que los jugadores especifiquen sus nombres y la
     * cantidad de turnos que quieren jugar, deberán colocar sus barcos.
     *
     * Una vez colocados los barcos, se harán n iteraciones de jugadores, siendo
     * n la cantidad de turnos. Si la iteración ya alcanza la cantidad de
     * turnos, ó algún jugador gana, ó empatan, el juego finaliza.
     */
    public void playGame() {
        this.startGame();
        String winner = "";

        for (Player actualPlayer : this.getPlayers()) {
            Iterator<Ship> ships = actualPlayer.getShips().iterator();
            while (ships.hasNext()) {
                Ship ship = ships.next();
                Boolean isBoatPlaced = false;
                do {
                    this.showShipMap(actualPlayer);
                    waitXSeconds(2);
                    isBoatPlaced = askShipPosition(actualPlayer, ship);
                } while (!isBoatPlaced);
                System.out.println("El barco " + getShipName(ship).toUpperCase() + " fue colocado exitosamente");
                waitXSeconds(1);
            }
            this.showShipMap(actualPlayer);
            waitXSeconds(4);
            cleanScreen();
            System.out.println(actualPlayer.getName() + ", has colocado todos tus barcos exitosamente");
            waitXSeconds(4);
            cleanScreen();
        }

        for (int i = 1; i <= maxTurns; i++) {
            System.out.println("RONDA N°" + i);
            System.out.println("");
            for (Player actualPlayer : this.getPlayers()) {
                showMenu(actualPlayer);
                waitXSeconds(2);
                cleanScreen();
                if (hasPlayerWon(actualPlayer)) {
                    if (actualPlayer == this.getPlayers()[0]) {
                        winner = "0";
                    } else {
                        winner = "1";
                    }
                    String winnersName = actualPlayer.getName();
                    if (sunkenShipCounter(actualPlayer) == actualPlayer.getShips().size() - 1) {
                        actualPlayer = getOppossitePlayer(actualPlayer);
                        System.out.println(actualPlayer.getName() + ", " + winnersName + " ha hundido todos tus barcos, tienes una última oportunidad para empatar");
                        showMenu(actualPlayer);
                        if (hasPlayerWon(actualPlayer)) {
                            winner = "01";
                        }else{
                            System.out.println(winnersName + ", has ganado la partida, ¡felicitaciones!");
                        }
                        break;
                    } else {
                        System.out.println(winnersName + ", has ganado la partida, ¡felicitaciones!");
                    }
                    break;
                }

            }
            if (!winner.equals("")) {
                break;
            }
        }
        if (winner.equals("01") || winner.equals("")) {
            System.out.println("¡Empate! La partida ha finalizado");
        }
    }

    /**
     * Pide al jugador la posición inicial y final del barco, si estas son
     * correctas llama a la función placeShip(String, String, Ship) de player
     * para colocar el barco.
     *
     * @param player jugador actual.
     * @param ship barco actual.
     * @return Verdadero si se colocó el barco, falso en el caso contrario.
     */
    public Boolean askShipPosition(Player player, Ship ship) {
        System.out.println(player.getName() + ", a continuación ingresarás la posición INICIAL del barco " + getShipName(ship).toUpperCase());
        System.out.println("El barco " + getShipName(ship) + " sólo puede ocupar " + ship.getLength() + " casillas");
        waitXSeconds(1);
        String initialPosition = askCoordinates();
        String finalPosition;
        if (ship.getLength() > 1) {
            System.out.println("Ahora ingresarás la posición FINAL del barco " + getShipName(ship).toUpperCase());
            waitXSeconds(1);
            finalPosition = askCoordinates();
        } else {
            finalPosition = initialPosition;
        }
        String[] coordinates = new String[]{initialPosition, finalPosition};
        coordinates = checkReverse(coordinates);

        return player.placeShip(coordinates[0], coordinates[1], ship);
    }

    /**
     * Ponemos a "Dormir" el programa durante los segundos que queremos
     *
     * @param seconds
     */
    public void waitXSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    /**
     * Le pide al jugador las coordenadas y verifica que sean correctas.
     *
     * @return la coordenada.
     */
    public String askCoordinates() {
        Scanner scanner = new Scanner(System.in);
        boolean isCorrectPosition;
        String position;
        do {
            System.out.println("Ingrese la coordenada en el mapa");
            System.out.println("Recuerde que las coordenadas incluyen una letra (A-J) y un número (1-10), como por ejemplo A7");
            position = scanner.nextLine().toUpperCase();
            if (position.length() < 1 || position.length() > 3) {
                isCorrectPosition = false;
                System.out.println("Incorrecto, por favor intente de nuevo");
                waitXSeconds(1);
                continue;
            }
            switch (position.substring(0, 1)) {
                case "A":
                    isCorrectPosition = true;
                    break;
                case "B":
                    isCorrectPosition = true;
                    break;
                case "C":
                    isCorrectPosition = true;
                    break;
                case "D":
                    isCorrectPosition = true;
                    break;
                case "E":
                    isCorrectPosition = true;
                    break;
                case "F":
                    isCorrectPosition = true;
                    break;
                case "G":
                    isCorrectPosition = true;
                    break;
                case "H":
                    isCorrectPosition = true;
                    break;
                case "I":
                    isCorrectPosition = true;
                    break;
                case "J":
                    isCorrectPosition = true;
                    break;
                default:
                    System.out.println("Incorrecto, por favor intente de nuevo");
                    isCorrectPosition = false;
                    waitXSeconds(1);
                    continue;
            }
            switch (position.substring(1)) {
                case "1":
                    isCorrectPosition = true;
                    break;
                case "2":
                    isCorrectPosition = true;
                    break;
                case "3":
                    isCorrectPosition = true;
                    break;
                case "4":
                    isCorrectPosition = true;
                    break;
                case "5":
                    isCorrectPosition = true;
                    break;
                case "6":
                    isCorrectPosition = true;
                    break;
                case "7":
                    isCorrectPosition = true;
                    break;
                case "8":
                    isCorrectPosition = true;
                    break;
                case "9":
                    isCorrectPosition = true;
                    break;
                case "10":
                    isCorrectPosition = true;
                    break;
                default:
                    isCorrectPosition = false;
                    System.out.println("Incorrecto, por favor intente de nuevo");
                    waitXSeconds(1);
                    break;
            }
        } while (!isCorrectPosition);
        System.out.println("");
        return position;
    }

    /**
     * Devuelve el nombre del barco según su longitud.
     *
     * @param ship Ship
     * @return nombre del baro
     */
    public String getShipName(Ship ship) {
        switch (ship.getLength()) {
            case 1:
                return "lancha";
            case 2:
                return "submarino";
            case 3:
                return "buque";
            case 4:
                return "crucero";
            case 5:
                return "portaviones";
            default:
                return null;
        }
    }

    /**
     * Devuelve el enemigo del jugador actual.
     *
     * @param currentPlayer jugador actual.
     * @return jugador enemigo.
     */
    private Player getOppossitePlayer(Player currentPlayer) {
        Player player1 = this.players[0];
        Player player2 = this.players[1];

        if (player1.equals(currentPlayer)) {
            return player2;
        } else if (player2.equals(currentPlayer)) {
            return player1;
        } else {
            throw new IllegalArgumentException("The specified player is not in the ArrayList.");
        }
    }

    /**
     * Imprime el mapa con los tiros del jugador, si disparó a un barco, se
     * dibujará una X, si se disparó al agua, se dibujará una O.
     *
     * @param player Player
     */
    public void showShotsMap(Player player) {
        Player oppPlayer = getOppossitePlayer(player);
        System.out.println("              Mapa del enemigo");
        System.out.println("");
        // Top Number Legend
        Map map = player.getMap();
        int charLegendCnt = 64;
        System.out.println();
        for (int i = 0; i < map.getBoard().size() + 1; i++) {
            if (i == 0) {
                System.out.print("   ");
            } else {
                System.out.printf("| %s ", i);
            }

        }
        System.out.println("|");
        for (int i = 0; i < map.getBoard().size(); i++) {
            // Left Character Legend
            charLegendCnt += 1;
            char charLegend = (char) charLegendCnt;
            System.out.printf(" %s ", charLegend);
            for (int j = 0; j < map.getBoard().get(0).size(); j++) {
                // Indicators
                if (map.isCellShot(player.getMap().getCell(i, j))) {
                    if (oppPlayer.getMap().getCell(i, j).getElement() instanceof Ship || oppPlayer.getMap().getCell(i, j).getElement() instanceof Decoy) {
                        System.out.printf("| X ");
                    } else {
                        System.out.printf("| O ");
                    }

                } else {
                    System.out.printf("|   ");
                }

            }
            System.out.println(" |");
        }
        System.out.println();
    }

    /**
     * Imprime el mapa del jugador actual, si algún barco fue disparado, se
     * marcará con una X, caso contrario, se marcará con B.
     *
     * @param player Player
     */
    public void showShipMap(Player player) {
        System.out.println("");
        System.out.println("              Mapa de " + player.getName());
        Map map = player.getMap();
        // Top Number Legend
        int charLegendCnt = 64;
        System.out.println();
        for (int i = 0; i < map.getBoard().size() + 1; i++) {
            if (i == 0) {
                System.out.print("   ");
            } else {
                System.out.printf("| %s ", i);
            }

        }
        System.out.println("|");
        for (int i = 0; i < map.getBoard().size(); i++) {
            // Left Character Legend
            charLegendCnt += 1;
            char charLegend = (char) charLegendCnt;
            System.out.printf(" %s ", charLegend);
            for (int j = 0; j < map.getBoard().get(0).size(); j++) {
                // Indicators
                Cell cell = map.getCell(i, j);
                if (cell.getWasShot()) {
                    if (!(cell.getElement() instanceof Water) && !(cell.getElement() instanceof Island)) {
                        System.out.print("| X ");
                    } else {
                        System.out.print("| O ");
                    }
                } else {
                    if (cell.getElement() instanceof Ship) {
                        System.out.print("| B ");
                    } else if (cell.getElement() instanceof Island) {
                        System.out.print("| I ");
                    } else if (cell.getElement() instanceof Decoy) {
                        System.out.print("| S ");
                    } else {
                        System.out.print("|   ");
                    }
                }
            }
            System.out.println(" |");
        }
        System.out.println();
    }

    /**
     * Verifica si el jugador actual ha ganado, para ello cuenta los barcos
     * hundidos del jugador contrario.
     *
     * @param player Player
     * @return cantidad de barcos hundidos del contrincante.
     */
    public boolean hasPlayerWon(Player player) {
        Player enemy = getOppossitePlayer(player);
        int sunkenShips = sunkenShipCounter(enemy);
        return sunkenShips == enemy.getShips().size();
    }

    /**
     * Cuenta los barcos hundidos del jugador.
     *
     * @param player Player
     * @return cantidad de barcos hundidos.
     */
    public int sunkenShipCounter(Player player) {
        Iterator<Ship> ships = player.getShips().iterator();
        int sunkenShips = 0;
        while (ships.hasNext()) {
            Ship ship = ships.next();
            if (ship.getSunken()) {
                sunkenShips++;
            }
        }
        return sunkenShips;
    }

    /**
     * Verifica que las coordenadas otorgadas no estén al revés, es decir, en
     * vez de (A1, A5) sea (A5, A1). En caso de estar al revés, las da vuelta
     * para asegurar la correcta colocación del barco.
     *
     * @param coordinates String[]
     * @return coordenadas en el orden esperado.
     */
    public String[] checkReverse(String[] coordinates) {
        boolean reversedString = false;
        if (coordinates[0].substring(0, 1).equals(coordinates[1].substring(0, 1))) {
            if (Integer.parseInt(coordinates[0].substring(1)) > Integer.parseInt(coordinates[1].substring(1))) {
                reversedString = true;
            }
        } else if (coordinates[0].substring(1).equals(coordinates[1].substring(1))) {
            int result = coordinates[0].substring(0, 1).compareTo(coordinates[1].substring(0, 1));
            if (result > 0) {
                reversedString = true;
            }
        }
        if (reversedString) {
            String auxPosition = coordinates[0];
            coordinates[0] = coordinates[1];
            coordinates[1] = auxPosition;
        }
        return coordinates;
    }

    /**
     * Separa los barcos hundidos de los barcos a flota del jugador y los
     * acumula en dos ArrayList<Ship>.
     *
     * @param player Player
     * @return Barcos a flota y barcos hundidos
     */
    public ArrayList<Ship>[] shipList(Player player) {
        Iterator<Ship> ships = player.getShips().iterator();
        ArrayList<Ship> safeShips = new ArrayList<>();
        ArrayList<Ship> sunkenShips = new ArrayList<>();
        while (ships.hasNext()) {
            Ship iteratorShip = ships.next();
            if (iteratorShip.getSunken()) {
                sunkenShips.add(iteratorShip);
            } else {
                safeShips.add(iteratorShip);
            }
        }
        return new ArrayList[]{safeShips, sunkenShips};
    }

    /**
     * Obtiene la información actual de barcos de player e imprime por un lado
     * los que están a flota con sus costes de habilidad, y por otro lado los
     * que están hundidos.
     *
     * @param player Player
     */
    public void showShipList(Player player) {

        ArrayList[] shipInfo = shipList(player);
        System.out.println("Lista de barcos a flote");
        Iterator<Ship> safeShipsIterator = shipInfo[0].iterator();
        while (safeShipsIterator.hasNext()) {
            Ship safeShip = safeShipsIterator.next();
            if (safeShip.getLength() != 1) {
                System.out.println("- " + getShipName(safeShip).toUpperCase() + " - Coste de cargas: " + safeShip.getAbilityCost());
            } else {
                System.out.println("- " + getShipName(safeShip).toUpperCase());
            }

        }

        System.out.println("Lista de barcos hundidos");
        Iterator<Ship> sunkenShipsIterator = shipInfo[1].iterator();
        while (sunkenShipsIterator.hasNext()) {
            Ship sunkenShip = sunkenShipsIterator.next();
            System.out.println("- " + getShipName(sunkenShip).toUpperCase());
        }
        System.out.println("");
    }

    /**
     * Obtiene la información actual de barcos de player y filtra los que están
     * a flota, de estos sólo añade a una nueva ArrayList<Ship> aquellos que
     * tengan un coste de habilidad menor o igual a la cantidad de cargas del
     * jugador.
     *
     * @param player Player
     * @return Barcos disponibles para utilizar habilidad.
     */
    public ArrayList<Ship> availableShipAbilities(Player player) {
        ArrayList[] shipInfo = shipList(player);
        ArrayList<Ship> availableShips = new ArrayList<>();
        Iterator<Ship> safeShipsIterator = shipInfo[0].iterator();
        while (safeShipsIterator.hasNext()) {
            Ship safeShip = safeShipsIterator.next();
            if (safeShip.getAbilityCost() <= player.getCharges() && safeShip.getLength() > 1) {
                availableShips.add(safeShip);
            }
        }
        if (availableShips.isEmpty()) {
            return null;
        } else {
            return availableShips;
        }
    }

    /**
     * "Limpia" la pantalla para asegurar una mejor jugabilidad al usuario
     */
    public void cleanScreen() {
        for (int i = 0; i < 40; i++) {
            System.out.println("");
        }
    }

    /**
     * Imprime en pantalla el significado de los símbolos a la hora de ver el mapa propio
     */
    public void personalMapWiki() {
        System.out.println("Significado de los símbolos");
        System.out.println("O: Disparo enemigo al agua o isla");
        System.out.println("B: Parte de barco ilesa");
        System.out.println("I: Isla ilesa");
        System.out.println("S: Señuelo ileso");
        System.out.println("X: Parte de barco ó señuelo disparado\n");
    }

    /**
     * Imprime en pantalla el significado de los símbolos a la hora de ver el mapa enemigo
     */
    public void enemyMapWiki() {
        System.out.println("Significado de los símbolos");
        System.out.println("X: Barco... ¿o señuelo?");
        System.out.println("O: Agua o isla\n");
    }
}
