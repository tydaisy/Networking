import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Each time requests are made, log them.
 * 
 * @author yt27
 *
 */
public class Logging {

    private String request;
    private String filename;
    private String response;

    /**
     * Execute log.
     * 
     * @param request
     *            means ask for GET or HEAD
     * @param filename
     *            the name of the file
     * @param response
     *            response the current result
     * @throws IOException
     *             if there is IOException
     */
    public Logging(String request, String filename, String response) throws IOException {
        this.request = request;
        this.filename = filename;
        this.response = response;

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm;ss");
        String time = dateFormat.format(now);
        FileWriter logFile = new FileWriter(" log.text", true);
        logFile.write(time + " " + request + filename + " " + response);
        logFile.close();
    }
}