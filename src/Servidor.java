import java.io.IOException;
import java.net.*;

public class Servidor {
    DatagramSocket socket;
    int port;
    String fi;
    PalabraRandom ns = new PalabraRandom();
    boolean acabat;

    public Servidor(int port) {
        try {
            socket = new DatagramSocket(port);
            System.out.printf("Servidor obert pel port %d%n", port);
            System.out.println("\033[0;33mHe pensat: " + ns.getParaula());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.port = port;
        acabat = false;
        fi = "-1";
    }

    public void runServer() throws IOException, InterruptedException {
        byte[] receivingData = new byte[5];
        byte[] sendingData;
        InetAddress clientIP;
        int clientPort;

        //el servidor atén el port indefinidament
        while (!acabat) {
            //creació del paquet per rebre les dades
            DatagramPacket packet = new DatagramPacket(receivingData, 5);
            //espera de les dades
            socket.receive(packet);
            //processament de les dades rebudes i obtenció de la resposta
            sendingData = processData(packet.getData(), packet.getLength());
            //obtenció de l'adreça del client
            clientIP = packet.getAddress();
            //obtenció del port del client
            clientPort = packet.getPort();
            //creació del paquet per enviar la resposta
            packet = new DatagramPacket(sendingData, sendingData.length,
                    clientIP, clientPort);
            //enviament de la resposta
            socket.send(packet);
        }
        socket.close();
    }

    private byte[] processData(byte[] data, int lenght) throws InterruptedException {
        String msg = new String(data, 0, lenght);

        // comparació longitud de les paraules perquè no em doni errors
        if (ns.getParaula().length() == msg.length()) {
            String iguals = "";
            // string on guardo els caràcters que es repeteixen entre paraules
            String letigual = "";

            // for on llegeixo cada caràcter i el comparo
            for (int i = 0; i < ns.getParaula().length(); i++) {
                if (ns.getParaula().charAt(i) == msg.charAt(i)) {
                    letigual += ns.getParaula().charAt(i);
                }
            }

            // aquest for serveix per guardar els caràcters repetits amb espai perquè es vegi millor per al client
            for (int i = 0; i < letigual.length(); i++) {
                iguals += " " + letigual.charAt(i);
            }

            // si en acabar el for la variable letigual és igual a la paraula pensada significa que el client l'ha endevinta.
            // si no és la mateixa paraula em diu si algun caràcter coincideix y si no son igual de llarges hem dona un missatge
            if (letigual.equals(ns.getParaula())) {
                System.out.println("\033[0;32mEl client ha dit la mateixa paraula: " + ns.getParaula());
                System.out.println("\033[0;97mPenso un altre paraula...");
                Thread.sleep(3000);
                ns.pensa();
                System.out.println("\033[0;33mHe pensat: " + ns.getParaula());

                String i = "\033[0;32mCorrecte, has encertat la paraula. El servidor ha pensat un altre paraula";
                return i.getBytes();
            } else {
                System.out.println("\033[0;37mEl client ha dit: " + msg);

                if (letigual.equals("")) {
                    String n = "\033[0;31mIncorrecte, prova un altre paraula. Cap lletra coincideix";
                    return n.getBytes();
                } else {
                    String n = "\033[0;31mIncorrecte, prova un altre paraula. Algunes lletres que coincideixen son:" + iguals;
                    return n.getBytes();
                }
            }
        } else {
            String n = "\033[0;31mLa paraula te que tenir una longitud mínima de 5 caràcters";
            return n.getBytes();
        }
    }

    public static void main(String[] args) throws SocketException, IOException {
        Servidor servidor = new Servidor(5566);
        try {
            servidor.runServer();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fi Servidor");
    }
}