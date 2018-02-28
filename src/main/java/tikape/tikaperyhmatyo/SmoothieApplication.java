package tikape.tikaperyhmatyo;

import java.sql.*;
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
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        String dbAddress = System.getenv("JDBC_DATABASE_URL");
        Database db = new Database(dbAddress);
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
            smoothieIngredients.sort((s1, s2) -> s1.getOrderOf().compareTo(s2.getOrderOf()));
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

            List<Ingredient> ingredients = iDao.findAll();
            for (Ingredient ingredient : ingredients) {
                Integer numberOfUses = iDao.numberOfUses(ingredient.getId());
                ingredient.setNumberOfUses(numberOfUses);
            }

            map.put("ingredients", ingredients);
            return new ModelAndView(map, "ingredients");
        }, new ThymeleafTemplateEngine());

        Spark.get("/smoothies/:id/delete", (req, res) -> {
            Integer smoothieId = Integer.parseInt(req.params(":id"));
            sDao.delete(smoothieId);
            siDao.delete(smoothieId);
            res.redirect("/smoothies");
            return "";
        });

        Spark.get("/ingredients/:id/delete", (req, res) -> {	//deletes ingredient when called
            Integer ingredientId = Integer.parseInt(req.params(":id"));
            iDao.delete(ingredientId);
            res.redirect("/ingredients");
            return "";
        });

        Spark.get("/serror", (req, res) -> {
            Map map = new HashMap<>();

            return new ModelAndView(map, "serror");
        }, new ThymeleafTemplateEngine());

        Spark.get("/ierror", (req, res) -> {
            Map map = new HashMap<>();

            return new ModelAndView(map, "ierror");
        }, new ThymeleafTemplateEngine());

        Spark.get("/sierror", (req, res) -> {
            Map map = new HashMap<>();

            return new ModelAndView(map, "sierror");
        }, new ThymeleafTemplateEngine());

        Spark.get("/sempty", (req, res) -> {
            Map map = new HashMap<>();

            return new ModelAndView(map, "sempty");
        }, new ThymeleafTemplateEngine());

        Spark.get("/iempty", (req, res) -> {
            Map map = new HashMap<>();

            return new ModelAndView(map, "iempty");
        }, new ThymeleafTemplateEngine());

        Spark.get("/siempty", (req, res) -> {
            Map map = new HashMap<>();

            return new ModelAndView(map, "siempty");
        }, new ThymeleafTemplateEngine());

        Spark.post("/addsmoothie", (req, res) -> {
            Map map = new HashMap<>();
            String smoothieName = req.queryParams("smoothie").trim();

            if (smoothieName.isEmpty()) {
                res.redirect("/sempty");
            } else {
                if (sDao.isItFreeToUse(smoothieName)) {
                    sDao.saveOrUpdate(new Smoothie(sDao.findAll().size() + 1, smoothieName));
                    res.redirect("/smoothies");
                } else {
                    res.redirect("/serror");
                }
            }
            return "";
        });

        Spark.post("/addingredients", (req, res) -> {
            String ingredientName = req.queryParams("ingredient").trim();

            if (ingredientName.isEmpty()) {
                res.redirect("/iempty");
            } else {
                if (iDao.isItFreeToUse(ingredientName)) {
                    iDao.saveOrUpdate(new Ingredient(iDao.findAll().size() + 1, ingredientName));
                    res.redirect("/ingredients");
                } else {
                    res.redirect("/ierror");
                }
            }
            return "";
        });

        Spark.post("/addsi", (req, res) -> {
            Integer smoothieId = Integer.parseInt(req.queryParams("smoothieId"));
            Integer ingredientId = Integer.parseInt(req.queryParams("ingredientId"));
            String orderString = req.queryParams("order").trim();
            String quantity = req.queryParams("quantity").trim();
            String recipe = req.queryParams("recipe").trim();

            if (orderString.isEmpty() | quantity.isEmpty() | recipe.isEmpty()) {
                res.redirect("/siempty");
            } else {
                Integer order = Integer.parseInt(orderString);

                siDao.saveOrUpdate(new SmoothieIngredient(smoothieId, ingredientId, order, quantity, recipe));

                res.redirect("/smoothies");
            }
            return "";
        });
    }

}
