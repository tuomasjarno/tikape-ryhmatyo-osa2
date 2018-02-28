
package tikape.tikaperyhmatyo.domain;

public class SmoothieIngredient {
    private Integer smoothieId;
    private Integer ingredientId;
    private Integer orderOf;
    private String quantity;
    private String recipe;
    private Ingredient ingredient;
    
    //Ingredient ingredient oli aiemmin Integer ingredientId
    public SmoothieIngredient(Integer smoothieId, Integer ingredientId, Integer orderOf, String quantity, String recipe) {
        this.smoothieId = smoothieId;
        this.ingredientId = ingredientId;
        this.orderOf = orderOf;
        this.quantity = quantity;
        this.recipe = recipe;
        this.ingredient = null;
    }

    public Integer getSmoothieId() {
        return smoothieId;
    }

    public void setSmoothieId(Integer smoothieId) {
        this.smoothieId = smoothieId;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Integer getOrderOf() {
        return orderOf;
    }

    public void setOrderOf(Integer orderOf) {
        this.orderOf = orderOf;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
    
    public Ingredient getIngredient() {
        return this.ingredient;
    }
    
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }   
    
}
