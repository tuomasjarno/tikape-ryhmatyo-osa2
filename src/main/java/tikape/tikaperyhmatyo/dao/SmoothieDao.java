
package tikape.tikaperyhmatyo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.tikaperyhmatyo.db.Database;
import tikape.tikaperyhmatyo.domain.Ingredient;
import tikape.tikaperyhmatyo.domain.Smoothie;

public class SmoothieDao implements Dao<Smoothie, Integer> {
    private Database db;
    private IngredientDao iDao;
    
    public SmoothieDao(Database db) {
        this.db = db;
        this.iDao = new IngredientDao(this.db);
    }

    @Override
    public Smoothie findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Smoothie> findAll() throws SQLException {
        List<Smoothie> smoothies = new ArrayList<>();
        
        try (Connection connection = this.db.getConnection()) {
            ResultSet rs = connection.prepareStatement("SELECT DISTINCT Smoothie.id, Smoothie.name, SmoothieIngredient.recipe FROM Smoothie, SmoothieIngredient WHERE Smoothie.id = SmoothieIngredient.smoothie_id").executeQuery();
            while (rs.next()) {
                List<Ingredient> ingredients = iDao.findAll(); // iDao = IngredientDao joka palauttaa raakaaine listan
                smoothies.add(new Smoothie(rs.getInt("Smoothie.id"), rs.getString("Smoothie.name"), ingredients, rs.getString("SmoothieIngredient.recipe")));
            }
        }
        
        return smoothies; 
    }

    @Override
    public Smoothie saveOrUpdate(Smoothie object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
