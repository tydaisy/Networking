import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Handle a browser request.
 * 
 * @author yt27
 *
 */
public class ConnectionHandler {

    private String dir;
    private Socket conn;
    private static InputStream is;
    private static OutputStream os;
    private static BufferedReader br; // Use buffered reader to read client data
    private static String type; // Content-Type
    private static Logging logging;

    /**
     * Get a browser request, read the request and send back the information a
     * browser need.
     * 
     * @param dir
     *            The directory where the server will serve documents to a
     *            browser
     * @param conn
     *            The port that the server should listen
     */
    public ConnectionHandler(String dir, Socket conn) {
        this.dir = dir;
        this.conn = conn;
        try {
            is = conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            os = conn.getOutputStream();
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler: " + ioe.getMessage());
        }
    }

    /**
     * Show the web that a browser asked for.
     * 
     * @throws IOException
     *             If there is failed or interrupted I/O operations
     */
    public void showWeb() throws IOException {

        String request; // Get or Head
        String response;
        String[] line; // The first line of header
        String protocal;
        String filename;
        DataOutputStream dos;

        line = br.readLine().split(" ");
        request = line[0];
        filename = line[1];
        protocal = line[2];
        System.out.println("*************" + filename);

        contextType(filename);
        dos = new DataOutputStream(os);
        System.out.println(type);
        filename = dir + filename;
        File f = new File(filename);
        try {
            FileInputStream fis = new FileInputStream(f);
            response = protocal + " 200 OK\r\n";
            response += "Server: Simple Java Http Server\r\n";
            response += "Content-Type: " + type + "\r\n";
            response += "Content-Length: " + f.length() + "\r\n";
            response += "\r\n";
            if (!(request.equals("GET") || request.equals("HEAD"))) {
                response = protocal + " 501 Not Implemented\r\n";
                dos.writeBytes(response);
                logging = new Logging(request, filename, response);
            } else if (request.equals("HEAD")) {
                dos.writeBytes(response);
                logging = new Logging(request, filename, response);
            } else {
                dos.writeBytes(response);
                int s;
                while ((s = fis.read()) != -1) {
                    dos.write(s);
                }
                logging = new Logging(request, filename, response);
                fis.close();
            }

        } catch (FileNotFoundException e) {
            response = protocal + " 404 Not Found\r\n";
            dos.writeBytes(response);
            logging = new Logging(request, filename, response);
        }

        dos.close();
        clean();
    }

    private void contextType(String filename) {
        if (filename.contains("html")) {
            type = "text/html";
        } else if (filename.contains("jpg")) {
            type = "image/jpg";
        } else if (filename.contains("gif")) {
            type = "image/gif";
        } else if (filename.contains("png")) {
            type = "image/png";
        } else {
            type = null;
        }
    }

    private void clean() {
        System.out.println("ConnectionHandler: ... cleaning up and exiting ... ");
        try {
            br.close();
            is.close();
            os.close();
            conn.close();
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler:cleanup " + ioe.getMessage());
        }
    }
}
