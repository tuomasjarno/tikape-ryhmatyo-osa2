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
    public Ingredient saveOrUpdate(Ingredient ingredient) throws SQLException {
        if (this.findOne(ingredient.getId()) == null) {
            return this.save(ingredient);
        } else {
            return this.update(ingredient);
        }
    }
    
    public Ingredient save(Ingredient ingredient) throws SQLException {
        Connection connection = this.db.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Ingredient (name) VALUES (?)");
        statement.setString(1, ingredient.getName());
        statement.executeUpdate();
        
        return ingredient;        
    }
    
    public Ingredient update(Ingredient ingredient) throws SQLException {
        Connection connection = this.db.getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE Ingredient name = (?) WHERE id = (?)");
        statement.setString(1, ingredient.getName());
        statement.setInt(2, ingredient.getId());
        statement.executeUpdate();
        
        return ingredient;
    }

    @Override
    public void delete(Integer id) throws SQLException {
        Connection connection = this.db.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Ingredient WHERE id = (?)");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

}
