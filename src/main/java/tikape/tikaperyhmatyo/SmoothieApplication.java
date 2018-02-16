
package tikape.tikaperyhmatyo;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.tikaperyhmatyo.dao.SmoothieDao;
import tikape.tikaperyhmatyo.db.Database;

public class SmoothieApplication {
    public static void main(String[] args) {
        Database db = new Database("jdbc:sqlite:smoothies.db");
        SmoothieDao sDao = new SmoothieDao(db);
        
        Spark.get("/", (req, res) -> {
            Map map = new HashMap<>();
            map.put("smoothies", sDao.findAll());
            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
    }
}
