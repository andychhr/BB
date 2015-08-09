package my.util.io;

/**
 * Created by server on 14-11-16.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;


/**
 * @author hanrchen
 *
 */
public class MyInputStream {

    /**
     * use apache lib IOUtils to read content from input stream
     * @param is
     * @param Charsets : StandardCharsets.UTF_8
     * @throws IOException
     */
    public static String readStringFromInputStream(InputStream _is, Charset _charSet) throws IOException{
//		final InputStreamReader isr = new InputStreamReader(_is, _charSet);
//		final BufferedReader br = new BufferedReader(isr);
        // use apache lib to read content from input stream
        StringWriter _writer = new StringWriter();
        org.apache.commons.io.IOUtils.copy(_is, _writer, _charSet);	//IOUtils
        return _writer.toString();
    }


    /**
     * read into string from input stream
     * @param _is
     * @param _charset : StandardCharsets.UTF_8
     * @throws IOException
     */
    public static String readStringFromInputStreamByLine(InputStream _is, String _charSet) throws IOException {
        final InputStreamReader _isr = new InputStreamReader(_is,_charSet);
        final BufferedReader _br = new BufferedReader(_isr);

        StringBuilder _builder = new StringBuilder();
        String _xline = "";
        while ((_xline = _br.readLine()) != null) { // Read till end
            _builder.append(_xline);
        }

        // close all opened stuff
        _br.close();
        _isr.close();
        _is.close();

        return _builder.toString();
    }
    
    
   

}