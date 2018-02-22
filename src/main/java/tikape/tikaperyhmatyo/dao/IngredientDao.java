package tikape.tikaperyhmatyo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.tikaperyhmatyo.db.Database;
import tikape.tikaperyhmatyo.domain.Ingredient;

public class IngredientDao implements Dao<Ingredient, Integer> {

    private Database db;

    public IngredientDao(Database db) {
        this.db = db;
    }

    @Override
    public Ingredient findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public Ingredient saveOrUpdate(Ingredient object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
