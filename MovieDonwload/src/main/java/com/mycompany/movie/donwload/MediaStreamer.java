package com.mycompany.movie.donwload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

public class MediaStreamer implements StreamingOutput {

    private int length;
    final InputStream is;
    final byte[] buf = new byte[4096];

    public MediaStreamer(int length, InputStream is) {
        this.length = length;
        this.is = is;
    }

    @Override
    public void write(OutputStream outputStream) throws IOException, WebApplicationException {
        try {
            while (length != 0) {
                int read = is.read(buf, 0, buf.length > length ? length : buf.length);
                outputStream.write(buf, 0, read);
                length -= read;
            }
        } finally {
            is.close();
        }
    }

    public int getLength() {
        return length;
    }

}
