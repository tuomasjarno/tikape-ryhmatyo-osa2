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
import tikape.tikaperyhmatyo.domain.SmoothieIngredient;

public class SmoothieDao implements Dao<Smoothie, Integer> {

    private Database db;
    private IngredientDao iDao;
    private SmoothieIngredientDao siDao;

    public SmoothieDao(Database db) {
        this.db = db;
        this.iDao = new IngredientDao(this.db);
        this.siDao = new SmoothieIngredientDao(this.db);
    }

    @Override
    public Smoothie findOne(Integer id) throws SQLException {
        Smoothie smoothie = null;
        try (Connection connection = this.db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Smoothie WHERE id = (?)");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                smoothie = new Smoothie(id, rs.getString("name"));
            }
        }

        return smoothie;
    }

    @Override
    public List<Smoothie> findAll() throws SQLException {
        List<Smoothie> smoothies = new ArrayList<>();

        try (Connection connection = this.db.getConnection()) {
            ResultSet rs = connection.prepareStatement("SELECT id, name FROM Smoothie").executeQuery();
            while (rs.next()) {
                List<Ingredient> ingredients = iDao.findAll();
                List<SmoothieIngredient> smoothieIngredients = siDao.findAll();
                smoothies.add(new Smoothie(rs.getInt("id"), rs.getString("name")));
            }
        }

        return smoothies;
    }

    @Override
    public Smoothie saveOrUpdate(Smoothie smoothie) throws SQLException {
        if (this.findOne(smoothie.getId()) == null) {
            return this.save(smoothie);
        } else {
            return this.update(smoothie);
        }
    }

    public Smoothie save(Smoothie smoothie) throws SQLException {
        Connection connection = this.db.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Smoothie (name) VALUES (?)");
        statement.setString(1, smoothie.getName());
        statement.executeUpdate();

        return smoothie;
    }

    public Smoothie update(Smoothie smoothie) throws SQLException {
        Connection connection = this.db.getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE Smoothie name = (?) WHERE id = (?)");
        statement.setString(1, smoothie.getName());
        statement.setInt(2, smoothie.getId());
        statement.executeUpdate();

        return smoothie;
    }

    @Override
    public void delete(Integer id) throws SQLException {
        Connection connection = this.db.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Smoothie WHERE id = (?)");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public Boolean isItFreeToUse(String smoothieName) throws SQLException {
        List<Smoothie> smoothies = this.findAll();
        for (Smoothie smoothie : smoothies) {
            if (smoothie.getName().equals(smoothieName)) {
                return false;
            }
        }
        
        return true;
    }
}
