import settings.App_config;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/*
*   Author: Roberto Sanchez A. 
*   Date:   12/21/17
*/
@Path("default")
public class MainPage {

    private App_config app_config = new App_config();
    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream getMainPage() throws FileNotFoundException {

        // Getting an html file
        String path = app_config.root() + "/Resources/default.html";

        File f = new File(path);
        return new FileInputStream(f);
    }


}
