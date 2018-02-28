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

        Spark.get("/smoothielist/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer smoothieId = Integer.parseInt(req.params(":id"));
            map.put("smoothie", sDao.findOne(smoothieId));

            List<SmoothieIngredient> smoothieIngredients = siDao.findSmoothieIngredients(smoothieId);
            for (SmoothieIngredient si : smoothieIngredients) {
                si.setIngredient(iDao.findOne(si.getIngredientId()));
            }

            map.put("smoothieingredients", smoothieIngredients);

            return new ModelAndView(map, "id");
        }, new ThymeleafTemplateEngine());

        Spark.get("/smoothies", (req, res) -> {
            Map map = new HashMap<>();
            map.put("smoothies", sDao.findAll());
            map.put("ingredients", iDao.findAll());

            return new ModelAndView(map, "smoothies");
        }, new ThymeleafTemplateEngine());

        Spark.get("/ingredients", (req, res) -> {
            Map map = new HashMap<>();
            map.put("ingredients", iDao.findAll());

            return new ModelAndView(map, "ingredients");
        }, new ThymeleafTemplateEngine());

        Spark.get("/smoothies/:id/delete", (req, res) -> {
            Integer smoothieId = Integer.parseInt(req.params(":id"));
            sDao.delete(smoothieId);
            res.redirect("/smoothies");
            return "";
        });

        Spark.get("/ingredients/:id/delete", (req, res) -> {	//deletes ingredient when called
            Integer ingredientId = Integer.parseInt(req.params(":id"));
            iDao.delete(ingredientId);
            res.redirect("/ingredients");
            return "";
        });

        Spark.post("/addsmoothie", (req, res) -> {
            String smoothieName = req.queryParams("smoothie");
            sDao.saveOrUpdate(new Smoothie(sDao.findAll().size() + 1, smoothieName));
            res.redirect("/smoothies");
            return "";
        });

        Spark.post("/addingredient", (req, res) -> {	//adds given ingredient when called
            String ingredientName = req.queryParams("ingredient");
            iDao.saveOrUpdate(new Ingredient(iDao.findAll().size() + 1, ingredientName));
            res.redirect("/ingredients");
            return "";
        });

        Spark.post("/addsi", (req, res) -> {
            int smoothieId = Integer.parseInt(req.queryParams("smoothieId"));
            int ingredientId = Integer.parseInt(req.queryParams("ingredientId"));
            int order = Integer.parseInt(req.queryParams("order"));
            String quantity = req.queryParams("quantity");
            String recipe = req.queryParams("recipe");
            siDao.saveOrUpdate(new SmoothieIngredient(smoothieId, ingredientId, order, quantity, recipe));
            res.redirect("/smoothies");
            return "";
        });
    }

}
