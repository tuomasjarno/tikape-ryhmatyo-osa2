
package tikape.tikaperyhmatyo.domain;

import java.util.List;

public class Smoothie {
    private Integer id;
    private String name;
    private List<Ingredient> ingredients;
    private String recipe;
    
    public Smoothie(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.recipe = recipe;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

}
