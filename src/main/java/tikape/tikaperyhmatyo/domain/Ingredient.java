
package tikape.tikaperyhmatyo.domain;

public class Ingredient {
    private Integer id;
    private String name;
    private Integer orderOf;
    private Integer pieces;
    
    public Ingredient(Integer id, String name, Integer orderOf, Integer pieces) {
        this.id = id;
        this.name = name;
        this.orderOf = orderOf;
        this.pieces = pieces;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderOf() {
        return orderOf;
    }

    public void setOrderOf(Integer orderOf) {
        this.orderOf = orderOf;
    }

    public Integer getPieces() {
        return this.pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }
    
}
