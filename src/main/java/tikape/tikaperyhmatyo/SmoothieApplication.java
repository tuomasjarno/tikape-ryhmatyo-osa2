package tikape.tikaperyhmatyo;

import java.util.*;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.tikaperyhmatyo.dao.IngredientDao;
import tikape.tikaperyhmatyo.dao.SmoothieDao;
import tikape.tikaperyhmatyo.dao.SmoothieIngredientDao;
import tikape.tikaperyhmatyo.db.Database;
import tikape.tikaperyhmatyo.domain.Ingredient;
import tikape.tikaperyhmatyo.domain.Smoothie;
import tikape.tikaperyhmatyo.domain.SmoothieIngredient;

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

        Spark.get("/:id", (req, res) -> {
            Map map = new HashMap<>();
            Integer smoothieId = Integer.parseInt(req.params(":id"));
            
            List<SmoothieIngredient> smoothieIngredients = siDao.findSmoothieIngredients(smoothieId);
            List<Ingredient> ingredients = iDao.findSmoothieIngredients(smoothieIngredients);

            map.put("ingredients", ingredients);
            map.put("smoothieIngredients", smoothieIngredients);

            return new ModelAndView(map, "html tiedoston nimi tahan");
        }, new ThymeleafTemplateEngine());
    }
}
