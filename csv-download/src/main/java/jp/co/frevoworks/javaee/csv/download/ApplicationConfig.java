package jp.co.frevoworks.javaee.csv.download;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * 
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(jp.co.frevoworks.javaee.csv.download.CsvResource.class);
        resources.add(jp.co.frevoworks.javaee.csv.download.CsvWriterProvider.class);
    }
    
}
