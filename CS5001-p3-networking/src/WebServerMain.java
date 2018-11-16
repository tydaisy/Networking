/**
 * Execute a network.
 *
 * @author yt
 * @version 1.0
 * @since 10/11/2017
 */
public class WebServerMain {
    private static String dir;
    private static int port;

    /**
     * Get commend line arguemts and set up the server.
     *
     * @param args
     *            Two arguments to store the directory of documents and the port
     *            that the server should listen.
     */
    public static void main(String[] args) {
        try {
            dir = args[0]; // the first commend-line arguments to store the
                           // directory of documents
            port = Integer.parseInt(args[1]); // the second commend -line
                                              // arguments to store the port
                                              // that the server should listen

            System.out.println("Server is started...");
            new Server(dir, port);

            System.out.println("Finish!!!");
        } catch (Exception e) {
            System.out.println("Usage: java WebServerMain <document_root> <port>");
        }
    }
}
