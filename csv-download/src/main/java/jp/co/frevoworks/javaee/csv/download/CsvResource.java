package jp.co.frevoworks.javaee.csv.download;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import jp.co.frevoworks.javaee.csv.download.dto.ResultCsv;
import jp.co.frevoworks.javaee.csv.download.dto.User;

@Path("csv")
public class CsvResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CsvResource
     */
    public CsvResource() {
    }

    @GET
    @Produces("text/csv")
    public ResultCsv csv(@QueryParam("fields") String fields) {
        
        String[] properties = new String[]{};
        
        if(fields != null && fields.length() > 0){
            properties = fields.split(",");
        }
        
        String csvFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".csv";
        
        ResultCsv<User> resultCsv = new ResultCsv<>(properties,csvFileName,User.class);
        
        User user = new User();
        user.setAge(30);
        user.setUserName("hoge");
        resultCsv.add(user);
        
        return resultCsv;
    }
}
