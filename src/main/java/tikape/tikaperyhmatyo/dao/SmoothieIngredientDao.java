package tikape.tikaperyhmatyo.dao;

import java.sql.*;
import java.util.*;
import tikape.tikaperyhmatyo.db.Database;
import tikape.tikaperyhmatyo.domain.SmoothieIngredient;

public class SmoothieIngredientDao implements Dao<SmoothieIngredient, Integer> {

    private Database db;

    public SmoothieIngredientDao(Database db) {
        this.db = db;
    }

    @Override
    public SmoothieIngredient findOne(Integer smoothieId) throws SQLException {
        SmoothieIngredient si = null;
        try (Connection connection = this.db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM SmoothieIngredient WHERE smoothie_id = (?)");
            statement.setInt(1, smoothieId);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                si = new SmoothieIngredient(smoothieId, rs.getInt("ingredient_id"), rs.getInt("order_of"), rs.getString("quantity"), rs.getString("recipe"));
            }
        }
        
        return si;
    }

    @Override
    public List<SmoothieIngredient> findAll() throws SQLException {
        List<SmoothieIngredient> smoothieIngredients = new ArrayList<>();

        try (Connection connection = this.db.getConnection()) {
            ResultSet rs = connection.prepareStatement("SELECT * FROM SmoothieIngredient").executeQuery();
            while (rs.next()) {
                smoothieIngredients.add(new SmoothieIngredient(rs.getInt("smoothie_id"), rs.getInt("ingredient_id"), rs.getInt("order_of"), rs.getString("quantity"), rs.getString("recipe")));
            }
        }

        return smoothieIngredients;
    }

    public List<SmoothieIngredient> findSmoothieIngredients(Integer smoothieId) throws SQLException {
        List<SmoothieIngredient> smoothieIngredients = new ArrayList<>();

        try (Connection connection = this.db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM SmoothieIngredient WHERE (smoothie_id IN (SELECT * FROM Smoothie WHERE id = (?)))"); //AND ingredient_id IN (SELECT * FROM Ingredient WHERE id = (?))
            statement.setInt(1, smoothieId);
            //statement.setInt(2, ingredientId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                smoothieIngredients.add(new SmoothieIngredient(rs.getInt("smoothie_id"), rs.getInt("ingredient_id"), rs.getInt("order_of"), rs.getString("quantity"), rs.getString("recipe")));
            }
        }

        return smoothieIngredients;
    }

    @Override
    public SmoothieIngredient saveOrUpdate(SmoothieIngredient object) throws SQLException {
        try (Connection connection = this.db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO SmoothieIngredient (smoothie_id, ingredient_id, order_of, quantity, recipe) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, object.getSmoothieId());
            statement.setInt(2, object.getIngredientId());
            statement.setInt(3, object.getOrderOf());
            statement.setString(4, object.getQuantity());
            statement.setString(5, object.getRecipe());
            
            statement.executeUpdate();
        }

        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
