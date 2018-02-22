
package tikape.tikaperyhmatyo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.tikaperyhmatyo.dao.IngredientDao;
import tikape.tikaperyhmatyo.dao.SmoothieDao;
import tikape.tikaperyhmatyo.dao.SmoothieIngredientDao;
import tikape.tikaperyhmatyo.db.Database;
import tikape.tikaperyhmatyo.domain.Smoothie;

public class SmoothieApplication {
    public static void main(String[] args) {
        Database db = new Database("jdbc:sqlite:smoothies.db");
        SmoothieDao sDao = new SmoothieDao(db);
        IngredientDao iDao = new IngredientDao(db);
        SmoothieIngredientDao siDao = new SmoothieIngredientDao(db);
        
        Spark.get("/", (req, res) -> {
            Map map = new HashMap<>();
            map.put("smoothies", sDao.findAll());
            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/id", (req, res) -> {
            Map map = new HashMap<>();
            map.put("ingredients", iDao.findAll());
            map.put("smoothieIngredients", siDao.findAll());
            
            return new ModelAndView(map, "html tiedoston nimi tahan");
        }, new ThymeleafTemplateEngine());
    }
}
