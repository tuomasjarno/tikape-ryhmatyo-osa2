
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
    public SmoothieIngredient findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public SmoothieIngredient saveOrUpdate(SmoothieIngredient object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
