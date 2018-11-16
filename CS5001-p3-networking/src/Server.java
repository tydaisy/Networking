import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Run a server.
 * 
 * @author yt
 *
 */
public class Server {
    // private static boolean okay = true;
    /**
     * Create a server and build up connection with a browsrt.
     *
     * @param dir
     *            The directory where the server will serve documents to a
     *            browser
     * @param port
     *            The port that the server should listen
     * @throws IOException
     *             If there is failed or interrupted I/O operations
     */
    public Server(String dir, int port) throws IOException {
        ServerSocket server = new ServerSocket(port); // create a server with
                                                      // specific port
        Socket conn = null;
        ConnectionHandler ch;
        System.out.println("Server started... listening for connection on port " + port + "...");

        while (true) {
            conn = server.accept(); // the server starts listening
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

            ch = new ConnectionHandler(dir, conn); // creat new handler for the
                                                   // connection
            ch.showWeb();
            // ch.start();

        }
        // conn.close();
        // server.close();
    }

    // private static boolean execute() {
    // String choose;
    // System.out.println("Do you want to quit? (y/n)");
    // Scanner scan = new Scanner(System.in);
    // choose = scan.nextLine();
    // scan.close();
    // if (choose.equals("y")) {
    // okay = false;
    // }
    // return okay;
    // }
}
