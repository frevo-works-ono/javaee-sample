package jp.co.frevoworks.javaee.csv.download;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import jp.co.frevoworks.javaee.csv.download.dto.ResultCsv;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

@Provider
@Produces("text/csv")
public class CsvWriterProvider implements MessageBodyWriter<ResultCsv> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(ResultCsv t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(ResultCsv t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        
        // ダウンロードファイル名設定
        httpHeaders.add("Content-Disposition", "attachment; filename=" + t.getCsvFileName());
        
        try(ICsvBeanWriter beanWriter = new CsvBeanWriter(new OutputStreamWriter(entityStream, Charset.forName("UTF-8")),
                        CsvPreference.STANDARD_PREFERENCE)) {
            
            // ヘッダー出力
            beanWriter.writeHeader(t.getHeader());
                
            // データ出力
            for( final Object o : t.getData() ) {
                beanWriter.write(o, t.getProperties());
            }
        }
        finally {
            System.out.println("Complete.");
        }
    }
}
