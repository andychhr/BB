package my.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

public class MyOutputStream {
	/**
     * use apache lib IOUtils to read content from output stream
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
}
