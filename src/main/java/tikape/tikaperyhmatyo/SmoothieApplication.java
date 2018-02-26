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
            /*map.put("smoothie", sDao.findOne(smoothieId));
            map.put("ingredients", siDao.findOne(smoothieId));*/
            
            List<SmoothieIngredient> smoothieIngredients = siDao.findSmoothieIngredients(smoothieId);
            List<Ingredient> ingredients = new ArrayList<>();
            for (SmoothieIngredient si : smoothieIngredients) {
                ingredients.add(iDao.findOne(si.getIngredientId()));
            }
            
            map.put("ingredients", ingredients);

            return new ModelAndView(map, "id");
        }, new ThymeleafTemplateEngine());

        Spark.get("/ingredients", (req, res) -> {
            Map map = new HashMap<>();
            map.put("ingredients", iDao.findAll());

            return new ModelAndView(map, "ingredients");
        }, new ThymeleafTemplateEngine());

        Spark.get("/ingredients/:id/delete", (req, res) -> {	//deletes ingredient when called
            Integer ingredientId = Integer.parseInt(req.params(":id"));
            iDao.delete(ingredientId);
            res.redirect("/ingredients");
            return "";
        });

//    	Spark.post("/addsmoothies", (req, res) -> {   //Currently not in use by anything
//        	String smoothie = req.queryParams("smoothie");
//        	PreparedStatement stmt = db.getConnection().prepareStatement("INSERT INTO Smoothie (name) VALUES (?)");
//        	stmt.setString(1, smoothie);
//        	stmt.executeUpdate();
//        	res.redirect("/smoothies");
//        	return "";
//    	});
        Spark.post("/addingredient", (req, res) -> {	//adds given ingredient when called
            String ingredientName = req.queryParams("ingredient");
            iDao.saveOrUpdate(new Ingredient(iDao.findAll().size() + 1, ingredientName));
            res.redirect("/ingredients");
            return "";
        });
    }
}
