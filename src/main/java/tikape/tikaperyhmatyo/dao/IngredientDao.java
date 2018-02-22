package tikape.tikaperyhmatyo.dao;

import java.sql.*;
import java.util.*;
import tikape.tikaperyhmatyo.db.Database;
import tikape.tikaperyhmatyo.domain.Ingredient;
import tikape.tikaperyhmatyo.domain.SmoothieIngredient;

public class IngredientDao implements Dao<Ingredient, Integer> {

    private Database db;

    public IngredientDao(Database db) {
        this.db = db;
    }

    @Override
    public Ingredient findOne(Integer ingredientId) throws SQLException {
        Ingredient ingredient = null;
        try (Connection connection = this.db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Ingredient WHERE id = (?)");
            statement.setInt(1, ingredientId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                ingredient = new Ingredient(rs.getInt("id"), rs.getString("name"));
            } 
        }
        
        return ingredient;
    }

    @Override
    public List<Ingredient> findAll() throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();

        try (Connection connection = this.db.getConnection()) {
            ResultSet rs = connection.prepareStatement("SELECT id, name FROM Ingredient").executeQuery();
            while (rs.next()) {
                ingredients.add(new Ingredient(rs.getInt("id"), rs.getString("name")));
            }
        }
        //muista lisätä connection.close() jne
        return ingredients;
    }

    public List<Ingredient> findSmoothieIngredients(List<SmoothieIngredient> smoothieIngredients) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();

        try (Connection connection = this.db.getConnection()) {
            for (SmoothieIngredient si : smoothieIngredients) {
                if (this.findOne(si.getIngredientId()) != null) {
                    ingredients.add(this.findOne(si.getIngredientId()));
                }
            }
        }

        return ingredients;
    }

    @Override
    public Ingredient saveOrUpdate(Ingredient object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
