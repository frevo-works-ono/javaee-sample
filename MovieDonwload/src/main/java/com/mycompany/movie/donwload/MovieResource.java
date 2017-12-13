/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.movie.donwload;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.commons.io.IOUtils;

/**
 * REST Web Service
 *
 * @author h.ono
 */
@Path("movie")
public class MovieResource {

    public static final int CHUNK_SIZE = 1024 * 1024;//1MB

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MovieResource
     */
    public MovieResource() {
    }

    /**
     * 動画ファイルダウンロード（分割）
     *
     * @return 動画ファイル
     */
    @GET
    @Produces("video/mp4")
    public Response getJson(@HeaderParam("Range") String range) throws Exception {
        InputStream is = MovieResource.class.getClassLoader().getResourceAsStream("movie/sample.mp4");

        byte[] bytes = IOUtils.toByteArray(is);

        String rangeValue[] = range.substring(range.indexOf("=") + 1).split("-");
        int startRange = Integer.parseInt(rangeValue[0]);
        int endRange = CHUNK_SIZE + startRange;
        if (endRange >= bytes.length) {
            endRange = (int) (bytes.length - 1);
        }
        if (rangeValue.length == 2) {
            endRange = Integer.parseInt(rangeValue[1]);
        }

        InputStream bais = new ByteArrayInputStream(bytes);
        bais.skip(startRange);

        final int len = endRange - startRange + 1;

        final MediaStreamer streamer = new MediaStreamer(len, bais);

        final String responseRange = String.format("bytes %d-%d/%d", startRange, endRange, bytes.length);

        return Response.ok(streamer)
                .status(Response.Status.PARTIAL_CONTENT)
                .header("Accept-Ranges", "bytes")
                .header("Content-Disposition", "inline")
                .header("Content-Range", responseRange)
                .header("Content-Length", streamer.getLength())
                .build();
    }
}
