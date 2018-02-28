
package tikape.tikaperyhmatyo.domain;

public class Ingredient {
    private Integer id;
    private String name;
    private Integer numberofuses;
    
    public Ingredient(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.numberofuses = 0;
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
    
    public Integer getNumberofUses() {
        return this.numberofuses;
    }
    
    public void increaseNumberOfUses() {
        this.numberofuses++;
    }
    
    public void decreaseNumberOfUses() {
        this.numberofuses--;
    }
    
}
