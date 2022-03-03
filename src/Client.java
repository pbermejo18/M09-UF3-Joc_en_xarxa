import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    InetAddress serverIP;
    int serverPort;
    DatagramSocket socket;
    Scanner sc;
    String nom;
    int intents = 5;

    public Client() {
        sc = new Scanner(System.in);
    }

    public void init(String host, int port) throws SocketException, UnknownHostException {
        serverIP = InetAddress.getByName(host);
        serverPort = port;
        socket = new DatagramSocket();
    }

    public void runClient() throws IOException {
        byte [] receivedData = new byte[1024];
        byte [] sendingData;


        sendingData = getFirstRequest();
        while (mustContinue(sendingData) && intents >= 0) {
            DatagramPacket packet = new DatagramPacket(sendingData,sendingData.length,serverIP,serverPort);
            socket.send(packet);
            packet = new DatagramPacket(receivedData,1024);
            socket.receive(packet);
            sendingData = getDataToRequest(packet.getData(), packet.getLength());
        }
    }

    //Resta de conversa que se li envia al server
    private byte[] getDataToRequest(byte[] data, int length) {
        String rebut = new String(data,0, length);

        if (rebut.equals("\033[0;32mCorrecte, has encertat la paraula. El servidor ha pensat un altre paraula")) intents = 5;
        else intents --;

        //Imprimeix el nom del client + el que es reb del server i demana més dades
        String msg = "";
        if (intents>0) {
            System.out.print("\033[1;97m" + nom + "\033[0;37m(" + rebut + "\033[0;37m)" + "> Et queden " + intents + " intents!" + "\n");
            msg = sc.nextLine();
        }
        return msg.getBytes();
    }

    //primer missatge que se li envia al server
    private byte[] getFirstRequest() {
        System.out.println("Escriu el teu nom: ");
        nom = sc.nextLine();
        System.out.println("Digues una paraula de 5 caràcters: ");
        String p = sc.nextLine();
        return p.getBytes();

    }

    //Si se li diu adeu al server el client es desconnecta
    private boolean mustContinue(byte [] data) {
        String msg = new String(data).toLowerCase();
        return !msg.equals("adeu");
    }

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.init("localhost",5566);
            client.runClient();
        } catch (IOException e) {
            e.getStackTrace();
        }

    }

}